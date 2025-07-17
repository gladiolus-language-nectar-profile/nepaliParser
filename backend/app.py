from flask import Flask, request, send_file, jsonify
import subprocess
import os
from datetime import datetime

app = Flask(__name__)

UPLOAD_DIR = '/app/uploads'
OUTPUT_FILE = os.path.join(UPLOAD_DIR, 'annoutput.txt')

@app.route('/process', methods=['POST'])
def process():
    # Get the text input from frontend
    input_text = request.json.get('text', '')
    if not input_text.strip():
        return jsonify({'error': 'Empty input'}), 400

    # Write the input to testpaper.txt
    testpaper_path = os.path.join(UPLOAD_DIR, 'testpaper.txt')
    with open(testpaper_path, 'w', encoding='utf-8') as f:
        f.write(input_text.strip())

    # Run the Java program (adjust classpath and name if needed)
    try:
        result = subprocess.run(
            ['java', '-Xmx1000m', 'GRNN5', testpaper_path],
            cwd='/app/Nepali Parser',
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            timeout=20  # prevent long hang
        )
    except subprocess.TimeoutExpired:
        return jsonify({'error': 'Java processing timed out'}), 500

    if result.returncode != 0:
        return jsonify({'error': 'Java failed', 'stderr': result.stderr.decode()}), 500

    # Check if annoutput.txt was created
    if not os.path.exists(OUTPUT_FILE):
        return jsonify({'error': 'annoutput.txt not found'}), 500

    # Return annoutput.txt only
    return send_file(OUTPUT_FILE, as_attachment=False)

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=int(os.environ.get("PORT", 5000)))
