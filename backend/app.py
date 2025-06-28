from flask import Flask, request, jsonify, send_file
from flask_cors import CORS
import os
import subprocess
import tempfile
import shutil
from datetime import datetime
import logging

app = Flask(__name__)
CORS(app)  # Enable CORS for React frontend

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Configuration
UPLOAD_FOLDER = 'uploads'
OUTPUT_FOLDER = 'outputs'
JAVA_PROJECT_PATH = r'../Nepali Parser'  # Update this path
BATCH_FILE_PATH = os.path.join(JAVA_PROJECT_PATH, 'fastrun2.bat')

# Ensure directories exist
os.makedirs(UPLOAD_FOLDER, exist_ok=True)
os.makedirs(OUTPUT_FOLDER, exist_ok=True)

class NepaliTextProcessor:
    def __init__(self, java_project_path, batch_file_path):
        self.java_project_path = java_project_path
        self.batch_file_path = batch_file_path
    
    def process_text_file(self, file_content, filename):
        """Process the uploaded text file using the Java batch commands"""
        try:
            # Create a unique working directory for this request
            timestamp = datetime.now().strftime("%Y%m%d_%H%M%S_%f")
            work_dir = os.path.join(tempfile.gettempdir(), f"nepali_parser_{timestamp}")
            os.makedirs(work_dir, exist_ok=True)
            
            # Change to Java project directory
            original_cwd = os.getcwd()
            os.chdir(self.java_project_path)
            
            # Write input content to sentence.txt
            sentence_file = os.path.join(self.java_project_path, 'sentence.txt')
            with open(sentence_file, 'w', encoding='utf-8') as f:
                f.write(file_content)
            
            # Create a modified batch file for single sentence processing
            modified_batch_content = self._create_single_sentence_batch()
            modified_batch_file = os.path.join(work_dir, 'process_single.bat')
            
            with open(modified_batch_file, 'w', encoding='utf-8') as f:
                f.write(modified_batch_content)
            
            # Execute the batch file
            logger.info(f"Executing batch file: {modified_batch_file}")
            result = subprocess.run(
                [modified_batch_file],
                cwd=self.java_project_path,
                capture_output=True,
                text=True,
                timeout=300,  # 5 minute timeout
                shell=True
            )
            
            if result.returncode != 0:
                logger.error(f"Batch execution failed: {result.stderr}")
                raise Exception(f"Processing failed: {result.stderr}")
            
            # Read the output files
            output_files = self._collect_output_files()
            
            # Clean up
            os.chdir(original_cwd)
            shutil.rmtree(work_dir, ignore_errors=True)
            
            return output_files
            
        except Exception as e:
            os.chdir(original_cwd)
            logger.error(f"Error processing file: {str(e)}")
            raise e
    
    def _create_single_sentence_batch(self):
        """Create a modified batch file for processing a single sentence"""
        return '''@echo off
REM Modified batch for single sentence processing
path=%path%;C:\\Program Files\\Java\\jdk-24\\bin

REM Compile Java files
javac -encoding utf8 CreateXmlFileDemo2.java
javac -encoding utf8 ReadXMLFile.java
javac -Xlint:unchecked Mymatching1.java
javac -encoding utf8 Chunker.java
javac -encoding utf8 PaintNodes2.java
javac -encoding utf8 TagGRNN.java
javac -encoding utf8 GRNN5.java
javac -encoding utf8 Lwg7.java
javac -encoding utf8 AutoCorrector.java

echo Processing single sentence...
DEL input.txt
REM Process the sentence
java -Xmx1000m GRNN5 sentence.txt 
java -Xmx10000m AutoCorrector annoutput.txt smallmaindata1.txt 
java Lwg7 output.png smallmaindata1.txt 

REM Rename output files
copy annoutput.txt posout.txt
copy smallmaindata1.txt processed_output.txt
type input.txt 
echo Processing complete.
'''

    def _collect_output_files(self):
        """Collect the generated output files"""
        output_files = {}
        
        # List of expected output files
        expected_files = [
            'posout.txt',
            'input.txt'
        ]
    
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
processor = NepaliTextProcessor(JAVA_PROJECT_PATH, BATCH_FILE_PATH)

@app.route('/api/process-nepali-text', methods=['POST'])
def process_nepali_text():
    """Main endpoint to process Nepali text files"""
    try:
        # Check if file is present in request
        if 'file' not in request.files:
            return jsonify({'error': 'No file provided'}), 400
        
        file = request.files['file']
        
        if file.filename == '':
            return jsonify({'error': 'No file selected'}), 400
        
        if not file.filename.endswith('.txt'):
            return jsonify({'error': 'Only .txt files are supported'}), 400
        
        # Read file content
        file_content = file.read().decode('utf-8')
        
        if not file_content.strip():
            return jsonify({'error': 'File is empty'}), 400
        
        logger.info(f"Processing file: {file.filename}")
        
        # Process the file
        output_files = processor.process_text_file(file_content, file.filename)
        
        if not output_files:
            return jsonify({'error': 'No output generated'}), 500
        
        # Create a combined output file
        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
        output_filename = f"processed_{timestamp}_{file.filename}"
        output_path = os.path.join(OUTPUT_FOLDER, output_filename)
        
        # Combine all outputs into a single file
        with open(output_path, 'w', encoding='utf-8') as f:
            f.write(f"Nepali Text Processing Results\n")
            f.write(f"Original file: {file.filename}\n")
            f.write(f"Processed at: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n")
            f.write("="*50 + "\n\n")
            
            for filename, content in output_files.items():
                f.write(f"\n--- {filename} ---\n")
                f.write(content)
                f.write(f"\n--- End of {filename} ---\n\n")
        
        # Return the processed file
        return send_file(
            output_path,
            as_attachment=True,
            download_name=output_filename,
            mimetype='text/plain'
        )
        
    except Exception as e:
        logger.error(f"Error in process_nepali_text: {str(e)}")
        return jsonify({'error': f'Processing failed: {str(e)}'}), 500

@app.route('/api/health', methods=['GET'])
def health_check():
    """Health check endpoint"""
    return jsonify({
        'status': 'healthy',
        'timestamp': datetime.now().isoformat(),
        'java_project_path': JAVA_PROJECT_PATH,
        'batch_file_exists': os.path.exists(BATCH_FILE_PATH)
    })

@app.route('/api/status', methods=['GET'])
def get_status():
    """Get system status"""
    return jsonify({
        'status': 'running',
        'java_project_configured': os.path.exists(JAVA_PROJECT_PATH),
        'batch_file_exists': os.path.exists(BATCH_FILE_PATH),
        'upload_folder': UPLOAD_FOLDER,
        'output_folder': OUTPUT_FOLDER
    })

if __name__ == '__main__':
    # Validate configuration before starting
    if not os.path.exists(JAVA_PROJECT_PATH):
        logger.warning(f"Java project path does not exist: {JAVA_PROJECT_PATH}")
        logger.warning("Please update JAVA_PROJECT_PATH in the configuration")
    
    if not os.path.exists(BATCH_FILE_PATH):
        logger.warning(f"Batch file does not exist: {BATCH_FILE_PATH}")
    
    logger.info("Starting Nepali Text Parser Flask Backend...")
    logger.info(f"Upload folder: {UPLOAD_FOLDER}")
    logger.info(f"Output folder: {OUTPUT_FOLDER}")
    logger.info(f"Java project path: {JAVA_PROJECT_PATH}")
    
    app.run(debug=True, host='0.0.0.0', port=5000)
