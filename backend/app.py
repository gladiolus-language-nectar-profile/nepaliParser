from flask import Flask, request, jsonify, send_file
from flask_cors import CORS
import os
import subprocess
import tempfile
import shutil
from datetime import datetime
import logging
import stat

app = Flask(__name__)
CORS(app)  # Enable CORS for React frontend

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Configuration - Updated for containerized environment
UPLOAD_FOLDER = 'uploads'
OUTPUT_FOLDER = 'outputs'
# Use relative path since everything is copied to /app in Docker
JAVA_PROJECT_PATH = os.path.abspath('../Nepali Parser')  # This should point to your Java files
SHELL_SCRIPT_PATH = os.path.join(JAVA_PROJECT_PATH, 'process_nepali.sh')

# Ensure directories exist
os.makedirs(UPLOAD_FOLDER, exist_ok=True)
os.makedirs(OUTPUT_FOLDER, exist_ok=True)

class NepaliTextProcessor:
    def __init__(self, java_project_path, shell_script_path):
        self.java_project_path = java_project_path
        self.shell_script_path = shell_script_path
        self._setup_shell_script()
    
    def _setup_shell_script(self):
        """Create and setup the shell script for processing"""
        try:
            os.makedirs(self.java_project_path, exist_ok=True)
            
            # Create the shell script content
            shell_script_content = '''#!/bin/bash
set -e  # Exit on any error

# Set Java environment
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH="$JAVA_HOME/bin:$PATH"

echo "Current directory: $(pwd)"
echo "Java version: $(java -version 2>&1 | head -1)"

# Compile Java files if they exist
echo "Compiling Java files..."
if [ -f "CreateXmlFileDemo2.java" ]; then javac -encoding utf8 CreateXmlFileDemo2.java; fi
if [ -f "ReadXMLFile.java" ]; then javac -encoding utf8 ReadXMLFile.java; fi
if [ -f "Mymatching1.java" ]; then javac -Xlint:unchecked Mymatching1.java; fi
if [ -f "Chunker.java" ]; then javac -encoding utf8 Chunker.java; fi
if [ -f "PaintNodes2.java" ]; then javac -encoding utf8 PaintNodes2.java; fi
if [ -f "TagGRNN.java" ]; then javac -encoding utf8 TagGRNN.java; fi
if [ -f "GRNN5.java" ]; then javac -encoding utf8 GRNN5.java; fi
if [ -f "Lwg7.java" ]; then javac -encoding utf8 Lwg7.java; fi
if [ -f "AutoCorrector.java" ]; then javac -encoding utf8 AutoCorrector.java; fi

echo "Processing single sentence..."
# Clean up previous outputs
rm -f input.txt annoutput.txt smallmaindata1.txt posout.txt processed_output.txt

# Process the sentence if sentence.txt exists
if [ -f "sentence.txt" ]; then
    echo "Found sentence.txt, processing..."
    if [ -f "GRNN5.class" ]; then
        java -Xmx1000m GRNN5 sentence.txt >t1.txt || echo "GRNN5 processing completed with warnings"
    fi
    
    if [ -f "AutoCorrector.class" ] && [ -f "annoutput.txt" ]; then
        java -Xmx2000m AutoCorrector annoutput.txt smallmaindata1.txt || echo "AutoCorrector processing completed with warnings"
    fi
    
    if [ -f "Lwg7.class" ] && [ -f "smallmaindata1.txt" ]; then
        java Lwg7 output.png smallmaindata1.txt || echo "Lwg7 processing completed with warnings"
    fi
    
    # Create output files
    if [ -f "annoutput.txt" ]; then
        cp annoutput.txt posout.txt
    fi
    
    if [ -f "smallmaindata1.txt" ]; then
        cp smallmaindata1.txt processed_output.txt
    fi
    
    # Create input.txt if it doesn't exist
    if [ ! -f "input.txt" ] && [ -f "sentence.txt" ]; then
        cp sentence.txt input.txt
    fi
    
    echo "Processing complete."
else
    echo "No sentence.txt found!"
    exit 1
fi
'''
            
            # Write the shell script
            with open(self.shell_script_path, 'w', encoding='utf-8') as f:
                f.write(shell_script_content)
            
            # Make the shell script executable
            st = os.stat(self.shell_script_path)
            os.chmod(self.shell_script_path, st.st_mode | stat.S_IEXEC)
            
            logger.info(f"Shell script created at: {self.shell_script_path}")
            
        except Exception as e:
            logger.error(f"Error setting up shell script: {str(e)}")
            raise e
    
    def process_text_file(self, file_content, filename):
        """Process the uploaded text file using the shell script"""
        try:
            # Create a unique working directory for this request
            timestamp = datetime.now().strftime("%Y%m%d_%H%M%S_%f")
            
            # Change to Java project directory
            original_cwd = os.getcwd()
            
            # Ensure Java project directory exists
            if not os.path.exists(self.java_project_path):
                logger.warning(f"Java project path doesn't exist, creating: {self.java_project_path}")
                os.makedirs(self.java_project_path, exist_ok=True)
            
            os.chdir(self.java_project_path)
            logger.info(f"Changed to directory: {os.getcwd()}")
            
            # Write input content to sentence.txt
            sentence_file = os.path.join(self.java_project_path, 'sentence.txt')
            with open(sentence_file, 'w', encoding='utf-8') as f:
                f.write(file_content.strip())
            
            logger.info(f"Written content to sentence.txt: {len(file_content)} characters")
            
            # Execute the shell script
            logger.info(f"Executing shell script: {self.shell_script_path}")
            
            # Make sure the script is executable
            if os.path.exists(self.shell_script_path):
                st = os.stat(self.shell_script_path)
                os.chmod(self.shell_script_path, st.st_mode | stat.S_IEXEC)
            
            result = subprocess.run(
                ['bash', self.shell_script_path],
                cwd=self.java_project_path,
                capture_output=True,
                text=True,
                timeout=300,  # 5 minute timeout
            )
            
            logger.info(f"Script stdout: {result.stdout}")
            if result.stderr:
                logger.warning(f"Script stderr: {result.stderr}")
            
            if result.returncode != 0:
                logger.error(f"Script execution failed with return code {result.returncode}")
                logger.error(f"Stderr: {result.stderr}")
                # Don't raise exception immediately, try to collect any output that was generated
            
            # Read the output files
            output_files = self._collect_output_files()
            
            # If no output files and script failed, raise exception
            if not output_files and result.returncode != 0:
                raise Exception(f"Processing failed: {result.stderr}")
            
            # Clean up
            os.chdir(original_cwd)
            
            return output_files
            
        except Exception as e:
            os.chdir(original_cwd)
            logger.error(f"Error processing file: {str(e)}")
            raise e
    
    def _collect_output_files(self):
        """Collect the generated output files"""
        output_files = {}
        
        # List of expected output files
        expected_files = [
            't1.txt',
            'posout.txt',
            'input.txt',
            'processed_output.txt',
            'sentence.txt'  # Include original input as fallback
        ]
        
        logger.info(f"Looking for output files in: {self.java_project_path}")
        logger.info(f"Files in directory: {os.listdir(self.java_project_path) if os.path.exists(self.java_project_path) else 'Directory not found'}")
    
        for filename in expected_files:
            filepath = os.path.join(self.java_project_path, filename)
            if os.path.exists(filepath):
                try:
                    with open(filepath, 'r', encoding='utf-8') as f:
                        content = f.read()
                        if content.strip():  # Only include non-empty files
                            output_files[filename] = content
                            logger.info(f"Successfully read {filename}: {len(content)} characters")
                except Exception as e:
                    logger.warning(f"Could not read {filename}: {str(e)}")
            else:
                logger.warning(f"Output file not found: {filepath}")
        
        return output_files

# Initialize processor
processor = NepaliTextProcessor(JAVA_PROJECT_PATH, SHELL_SCRIPT_PATH)

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
        
        logger.info(f"Processing file: {file.filename}, content length: {len(file_content)}")
        
        # Process the file
        output_files = processor.process_text_file(file_content, file.filename)
        
        if not output_files:
            return jsonify({'error': 'No output files generated. This might indicate the Java processing failed or the Java files are missing.'}), 500
        
        # Create a combined output file in the OUTPUT_FOLDER
        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
        output_filename = f"processed_{timestamp}_{file.filename}"
        output_path = os.path.join(OUTPUT_FOLDER, output_filename)
        
        # Ensure output directory exists
        os.makedirs(OUTPUT_FOLDER, exist_ok=True)
        
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
        
        logger.info(f"Output file created: {output_path}")
        
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
    java_version = "Unknown"
    try:
        result = subprocess.run(['java', '-version'], capture_output=True, text=True)
        java_version = result.stderr.split('\n')[0] if result.stderr else "Java not found"
    except:
        java_version = "Java not accessible"
    
    return jsonify({
        'status': 'healthy',
        'timestamp': datetime.now().isoformat(),
        'java_project_path': JAVA_PROJECT_PATH,
        'java_project_exists': os.path.exists(JAVA_PROJECT_PATH),
        'shell_script_exists': os.path.exists(SHELL_SCRIPT_PATH),
        'java_version': java_version,
        'working_directory': os.getcwd(),
        'upload_folder_exists': os.path.exists(UPLOAD_FOLDER),
        'output_folder_exists': os.path.exists(OUTPUT_FOLDER)
    })

@app.route('/api/status', methods=['GET'])
def get_status():
    """Get system status"""
    return jsonify({
        'status': 'running',
        'java_project_configured': os.path.exists(JAVA_PROJECT_PATH),
        'shell_script_exists': os.path.exists(SHELL_SCRIPT_PATH),
        'upload_folder': UPLOAD_FOLDER,
        'output_folder': OUTPUT_FOLDER,
        'java_project_path': JAVA_PROJECT_PATH
    })

if __name__ == '__main__':
    # Validate configuration before starting
    logger.info("Starting Nepali Text Parser Flask Backend...")
    logger.info(f"Working directory: {os.getcwd()}")
    logger.info(f"Upload folder: {UPLOAD_FOLDER}")
    logger.info(f"Output folder: {OUTPUT_FOLDER}")
    logger.info(f"Java project path: {JAVA_PROJECT_PATH}")
    logger.info(f"Shell script path: {SHELL_SCRIPT_PATH}")
    
    if not os.path.exists(JAVA_PROJECT_PATH):
        logger.warning(f"Java project path does not exist: {JAVA_PROJECT_PATH}")
        logger.warning("Creating directory...")
        os.makedirs(JAVA_PROJECT_PATH, exist_ok=True)
    
    # Test Java availability
    try:
        result = subprocess.run(['java', '-version'], capture_output=True, text=True)
        logger.info(f"Java is available: {result.stderr.split()[0] if result.stderr else 'Unknown version'}")
    except Exception as e:
        logger.error(f"Java is not available: {str(e)}")
    
    # Get port from environment variable (Render sets this)
    port = int(os.environ.get('PORT', 5000))
    app.run(debug=False, host='0.0.0.0', port=port)
