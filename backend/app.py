from flask import Flask, request, jsonify, send_file
from flask_cors import CORS
import os
import subprocess
import tempfile
import shutil
from datetime import datetime
import logging

app = Flask(__name__)
CORS(app)

# Logging setup
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Config paths
UPLOAD_FOLDER = 'uploads'
OUTPUT_FOLDER = 'outputs'
JAVA_PROJECT_PATH = os.path.join(os.path.dirname(__file__), '../Nepali Parser')

# Create folders
os.makedirs(UPLOAD_FOLDER, exist_ok=True)
os.makedirs(OUTPUT_FOLDER, exist_ok=True)

class NepaliTextProcessor:
    def __init__(self, java_project_path):
        self.java_project_path = java_project_path

    def process_text_file(self, file_content, filename):
        try:
            timestamp = datetime.now().strftime("%Y%m%d_%H%M%S_%f")
            work_dir = os.path.join(tempfile.gettempdir(), f"nepali_parser_{timestamp}")
            os.makedirs(work_dir, exist_ok=True)

            original_cwd = os.getcwd()
            os.chdir(self.java_project_path)

            # Write input content to sentence.txt
            sentence_file = os.path.join(self.java_project_path, 'sentence.txt')
            with open(sentence_file, 'w', encoding='utf-8') as f:
                f.write(file_content)

            logger.info("Compiling Java files...")

            java_files = [
                "CreateXmlFileDemo2.java",
                "ReadXMLFile.java",
                "Mymatching1.java",
                "Chunker.java",
                "PaintNodes2.java",
                "TagGRNN.java",
                "GRNN5.java",
                "Lwg7.java",
                "AutoCorrector.java"
            ]

            for java_file in java_files:
                subprocess.run(["javac", "-encoding", "utf8", java_file], check=True)

            logger.info("Running GRNN5...")
            subprocess.run(["java", "-Xmx1000m", "GRNN5", "sentence.txt"], check=True)

            logger.info("Running AutoCorrector...")
            subprocess.run(["java", "-Xmx10000m", "AutoCorrector", "annoutput.txt", "smallmaindata1.txt"], check=True)

            logger.info("Running Lwg7...")
            subprocess.run(["java", "Lwg7", "output.png", "smallmaindata1.txt"], check=True)

            output_files = self._collect_output_files()

            os.chdir(original_cwd)
            shutil.rmtree(work_dir, ignore_errors=True)

            return output_files

        except subprocess.CalledProcessError as e:
            os.chdir(original_cwd)
            logger.error(f"Command failed: {e}")
            raise Exception(f"Java command failed: {e}")
        except Exception as e:
            os.chdir(original_cwd)
            logger.error(f"Processing error: {str(e)}")
            raise

    def _collect_output_files(self):
        output_files = {}
        expected_files = ['posout.txt', 'input.txt']

        for filename in expected_files:
            filepath = os.path.join(self.java_project_path, filename)
            if os.path.exists(filepath):
                try:
                    with open(filepath, 'r', encoding='utf-8') as f:
                        output_files[filename] = f.read()
                except Exception as e:
                    logger.warning(f"Could not read {filename}: {str(e)}")

        return output_files

# Initialize processor
processor = NepaliTextProcessor(JAVA_PROJECT_PATH)

@app.route('/api/process-nepali-text', methods=['POST'])
def process_nepali_text():
    try:
        if 'file' not in request.files:
            return jsonify({'error': 'No file provided'}), 400

        file = request.files['file']

        if file.filename == '':
            return jsonify({'error': 'No file selected'}), 400

        if not file.filename.endswith('.txt'):
            return jsonify({'error': 'Only .txt files are supported'}), 400

        file_content = file.read().decode('utf-8')
        if not file_content.strip():
            return jsonify({'error': 'File is empty'}), 400

        logger.info(f"Processing file: {file.filename}")
        output_files = processor.process_text_file(file_content, file.filename)

        if not output_files:
            return jsonify({'error': 'No output generated'}), 500

        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
        output_filename = f"processed_{timestamp}_{file.filename}"
        output_path = os.path.join(OUTPUT_FOLDER, output_filename)

        with open(output_path, 'w', encoding='utf-8') as f:
            f.write(f"Nepali Text Processing Results\n")
            f.write(f"Original file: {file.filename}\n")
            f.write(f"Processed at: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n")
            f.write("="*50 + "\n\n")

            for fname, content in output_files.items():
                f.write(f"\n--- {fname} ---\n{content}\n--- End of {fname} ---\n\n")

        return send_file(output_path, as_attachment=True, download_name=output_filename, mimetype='text/plain')

    except Exception as e:
        logger.error(f"Error: {str(e)}")
        return jsonify({'error': f'Processing failed: {str(e)}'}), 500

@app.route('/api/health', methods=['GET'])
def health_check():
    return jsonify({
        'status': 'healthy',
        'timestamp': datetime.now().isoformat(),
        'java_project_path': JAVA_PROJECT_PATH
    })

@app.route('/api/status', methods=['GET'])
def get_status():
    return jsonify({
        'status': 'running',
        'java_project_configured': os.path.exists(JAVA_PROJECT_PATH),
        'upload_folder': UPLOAD_FOLDER,
        'output_folder': OUTPUT_FOLDER
    })

if __name__ == '__main__':
    logger.info("Starting Nepali Text Parser Backend...")
    logger.info(f"Java project path: {JAVA_PROJECT_PATH}")
    app.run(debug=True, host='0.0.0.0', port=5000)
