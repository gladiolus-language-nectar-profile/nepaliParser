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
        """Create and setup the shell script for processing using your exact script"""
        try:
            os.makedirs(self.java_project_path, exist_ok=True)
            
            # Your exact shell script - NO MODIFICATIONS
            shell_script_content = '''#!/bin/bash
# Add Java to PATH (if needed)
export PATH="$PATH:/usr/lib/jvm/java-14-openjdk-amd64/bin"
echo "Compiling Java files..."
rm -f annoutput.txt display.txt t11.txt
javac -encoding utf8 CreateXmlFileDemo2.java
javac -encoding utf8 ReadXMLFile.java
javac -Xlint:unchecked Mymatching1.java
javac -encoding utf8 Chunker.java
javac -encoding utf8 PaintNodes2.java
javac -encoding utf8 TagGRNN.java
javac -encoding utf8 GRNN5.java
javac -encoding utf8 Lwg7.java
javac -encoding utf8 AutoCorrector.java
echo "CONVERT UTF16 TO UTF8"
count=0
# Read each line from testpaper.txt
while IFS= read -r line; do
    echo "$line"
    echo "$line" > sentence.txt
    java -Xmx1000m GRNN5 sentence.txt > t11.txt
    # java -Xmx10000m AutoCorrector annoutput.txt smallmaindata1.txt > t111.txt
    # java Lwg7 "out${count}.png" smallmaindata1.txt > t.txt
    # mv annoutput.txt "posout${count}.txt"
    count=$((count + 1))
    echo "$count"
    cat annoutput.txt
    echo "$line"
    cat display.txt
done < testpaper.txt
read -p "Press enter to continue..."
# Notes:
# Replace the Java path (/usr/lib/jvm/...) with the correct one on your Linux system
# File path and naming conventions are case sensitive in Linux
# Use chmod +x script.sh to make it executable.
# Run with ./script.sh .
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
    
    def _clean_output_files(self):
        """Clean any existing output files before processing to ensure fresh generation"""
        files_to_clean = [
            'annoutput.txt',
            'display.txt',
            't11.txt',
            'sentence.txt',
            'testpaper.txt'
        ]
        
        # Also clean any posout files
        for i in range(50):  # Clean up to 50 possible posout files
            files_to_clean.append(f'posout{i}.txt')
        
        for filename in files_to_clean:
            filepath = os.path.join(self.java_project_path, filename)
            if os.path.exists(filepath):
                try:
                    os.remove(filepath)
                    logger.info(f"Cleaned existing file: {filename}")
                except Exception as e:
                    logger.warning(f"Could not clean {filename}: {str(e)}")
    
    def process_text_file(self, file_content, filename):
        """Process the uploaded text file using your exact shell script"""
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
            
            # Clean any existing output files to ensure fresh generation
            self._clean_output_files()
            
            # Write input content to testpaper.txt (as your script expects)
            testpaper_file = os.path.join(self.java_project_path, 'testpaper.txt')
            with open(testpaper_file, 'w', encoding='utf-8') as f:
                f.write(file_content.strip())
            
            logger.info(f"Written content to testpaper.txt: {len(file_content)} characters")
            
            # Store timestamps of files before processing
            files_before = self._get_file_timestamps()
            
            # Execute your exact shell script but handle the interactive prompt
            logger.info(f"Starting script execution...")
            
            # Create a modified version that removes the interactive prompt
            modified_script_content = '''#!/bin/bash
# Add Java to PATH (if needed)
export PATH="$PATH:/usr/lib/jvm/java-14-openjdk-amd64/bin"
echo "Compiling Java files..."
rm -f annoutput.txt display.txt t11.txt
javac -encoding utf8 CreateXmlFileDemo2.java
javac -encoding utf8 ReadXMLFile.java
javac -Xlint:unchecked Mymatching1.java
javac -encoding utf8 Chunker.java
javac -encoding utf8 PaintNodes2.java
javac -encoding utf8 TagGRNN.java
javac -encoding utf8 GRNN5.java
javac -encoding utf8 Lwg7.java
javac -encoding utf8 AutoCorrector.java
echo "CONVERT UTF16 TO UTF8"
count=0
# Read each line from testpaper.txt
while IFS= read -r line; do
    echo "$line"
    echo "$line" > sentence.txt
    java -Xmx1000m GRNN5 sentence.txt > t11.txt
    # java -Xmx10000m AutoCorrector annoutput.txt smallmaindata1.txt > t111.txt
    # java Lwg7 "out${count}.png" smallmaindata1.txt > t.txt
    # mv annoutput.txt "posout${count}.txt"
    count=$((count + 1))
    echo "$count"
    if [ -f "annoutput.txt" ]; then
        echo "=== annoutput.txt content ==="
        cat annoutput.txt
        echo "=== end annoutput.txt ==="
    else
        echo "WARNING: annoutput.txt not found"
    fi
    echo "$line"
    if [ -f "display.txt" ]; then
        echo "=== display.txt content ==="
        cat display.txt
        echo "=== end display.txt ==="
    else
        echo "WARNING: display.txt not found"
    fi
done < testpaper.txt
echo "Processing completed automatically"
'''
            
            # Write the modified script (only difference: removed interactive prompt)
            modified_script_path = os.path.join(self.java_project_path, 'process_nepali_auto.sh')
            with open(modified_script_path, 'w', encoding='utf-8') as f:
                f.write(modified_script_content)
            
            # Make it executable
            st = os.stat(modified_script_path)
            os.chmod(modified_script_path, st.st_mode | stat.S_IEXEC)
            
            result = subprocess.run(
                ['bash', modified_script_path],
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
            
            # Read the output files - only those generated after processing
            files_after = self._get_file_timestamps()
            output_files = self._collect_output_files(files_before, files_after)
            
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
    
    def _get_file_timestamps(self):
        """Get timestamps of files in the Java project directory"""
        timestamps = {}
        try:
            for filename in os.listdir(self.java_project_path):
                filepath = os.path.join(self.java_project_path, filename)
                if os.path.isfile(filepath):
                    timestamps[filename] = os.path.getmtime(filepath)
        except Exception as e:
            logger.warning(f"Could not get file timestamps: {str(e)}")
        return timestamps
    
    def _collect_output_files(self, files_before=None, files_after=None):
        """Collect only the dynamically generated output files"""
        output_files = {}
        
        logger.info(f"Looking for output files in: {self.java_project_path}")
        logger.info(f"Files in directory: {os.listdir(self.java_project_path) if os.path.exists(self.java_project_path) else 'Directory not found'}")
        
        # Priority files that should be dynamically generated
        priority_files = [
            'annoutput.txt',  # This is the main file you want
            'display.txt',
            'sentence.txt',
            't11.txt'
        ]
        
        # Check priority files first
        for filename in priority_files:
            filepath = os.path.join(self.java_project_path, filename)
            if os.path.exists(filepath):
                # Check if file was generated/modified during this processing run
                if self._is_file_generated(filename, files_before, files_after):
                    try:
                        with open(filepath, 'r', encoding='utf-8') as f:
                            content = f.read()
                            if content.strip():  # Only include non-empty files
                                output_files[filename] = content
                                logger.info(f"Successfully read dynamically generated {filename}: {len(content)} characters")
                            else:
                                logger.warning(f"File {filename} exists but is empty")
                    except Exception as e:
                        logger.warning(f"Could not read {filename}: {str(e)}")
                else:
                    logger.info(f"File {filename} exists but was not generated in this run, skipping")
        
        # Look for posout files (posout0.txt, posout1.txt, etc.) - these should be dynamically generated
        for i in range(10):  # Check first 10 possible files
            posout_file = f'posout{i}.txt'
            filepath = os.path.join(self.java_project_path, posout_file)
            if os.path.exists(filepath):
                if self._is_file_generated(posout_file, files_before, files_after):
                    try:
                        with open(filepath, 'r', encoding='utf-8') as f:
                            content = f.read()
                            if content.strip():
                                output_files[posout_file] = content
                                logger.info(f"Successfully read dynamically generated {posout_file}: {len(content)} characters")
                    except Exception as e:
                        logger.warning(f"Could not read {posout_file}: {str(e)}")
        
        # If no files were found, log an error
        if not output_files:
            logger.error("No dynamically generated output files found!")
            logger.error("This might indicate that the Java processing is not working correctly")
            
            # As a fallback, try to read any files that exist (but log a warning)
            for filename in ['annoutput.txt', 'display.txt']:
                filepath = os.path.join(self.java_project_path, filename)
                if os.path.exists(filepath):
                    try:
                        with open(filepath, 'r', encoding='utf-8') as f:
                            content = f.read()
                            if content.strip():
                                output_files[f"{filename}_fallback"] = content
                                logger.warning(f"Using fallback file {filename}: {len(content)} characters")
                    except Exception as e:
                        logger.warning(f"Could not read fallback {filename}: {str(e)}")
        
        return output_files
    
    def _is_file_generated(self, filename, files_before, files_after):
        """Check if a file was generated or modified during this processing run"""
        if files_before is None or files_after is None:
            return True  # If we can't check timestamps, assume it's generated
        
        # File is considered generated if:
        # 1. It didn't exist before but exists now
        # 2. It existed before but has a newer timestamp
        
        if filename not in files_before and filename in files_after:
            return True  # New file
        
        if filename in files_before and filename in files_after:
            return files_after[filename] > files_before[filename]  # Modified file
        
        return False

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
            
            # Prioritize annoutput.txt if it exists
            if 'annoutput.txt' in output_files:
                f.write(f"\n=== MAIN OUTPUT: annoutput.txt ===\n")
                f.write(output_files['annoutput.txt'])
                f.write(f"\n=== END OF annoutput.txt ===\n\n")
            
            # Write other files
            for filename, content in output_files.items():
                if filename != 'annoutput.txt':  # Skip since we already wrote it above
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
    port = int(os.environ.get("PORT", 5000))  # Railway sets PORT dynamically
    app.run(debug=False, host='0.0.0.0', port=port)
