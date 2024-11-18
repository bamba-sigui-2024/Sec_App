
from flask import Flask, request, jsonify, send_file
from werkzeug.utils import secure_filename
from PIL import Image
import os
import numpy as np

app = Flask(__name__)
UPLOAD_FOLDER = './uploads'
RESULT_FOLDER = './results'
os.makedirs(UPLOAD_FOLDER, exist_ok=True)
os.makedirs(RESULT_FOLDER, exist_ok=True)

app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
app.config['RESULT_FOLDER'] = RESULT_FOLDER

def koch_zhao_hide(image_path, message):
    # Dummy implementation for demonstration
    img = Image.open(image_path).convert('RGB')
    arr = np.array(img)
    encoded_message = message.encode('utf-8')
    arr.flat[:len(encoded_message)] = np.frombuffer(encoded_message, dtype=np.uint8)
    output_path = os.path.join(app.config['RESULT_FOLDER'], "hidden.png")
    Image.fromarray(arr).save(output_path)
    return output_path

def koch_zhao_extract(image_path):
    # Dummy extraction for demonstration
    img = Image.open(image_path).convert('RGB')
    arr = np.array(img)
    extracted_bytes = arr.flat[:100]  # Assume first 100 bytes hold the message
    return extracted_bytes.tobytes().decode('utf-8', errors='ignore')

@app.route('/hide', methods=['POST'])
def hide_message():
    if 'image' not in request.files or 'message' not in request.form:
        return jsonify({"error": "Image and message are required"}), 400
    image = request.files['image']
    message = request.form['message']
    filename = secure_filename(image.filename)
    image_path = os.path.join(app.config['UPLOAD_FOLDER'], filename)
    image.save(image_path)
    result_path = koch_zhao_hide(image_path, message)
    return send_file(result_path, as_attachment=True)

@app.route('/extract', methods=['POST'])
def extract_message():
    if 'image' not in request.files:
        return jsonify({"error": "Image is required"}), 400
    image = request.files['image']
    filename = secure_filename(image.filename)
    image_path = os.path.join(app.config['UPLOAD_FOLDER'], filename)
    image.save(image_path)
    extracted_message = koch_zhao_extract(image_path)
    return jsonify({"message": extracted_message})

if __name__ == "__main__":
    app.run(host='20.123.41.154', port=8000, debug=True)
