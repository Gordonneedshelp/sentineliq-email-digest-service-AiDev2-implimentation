# Initial Flask Boilerplate - Prepared by AI Dev 2 [Shashank Khot] to unblock development from AI developer 1

import os 
from flask import Flask,request,jsonify
from flask_limiter import Limiter
from flask_limiter.util import get_remote_address
from dotenv import load_dotenv
import groq 

# Importing services we just made 
from services.groq_client import GroqClient
from services.text_sanitizer import sanitize_input, detect_prompt_injection

#Load environment variables 
load_dotenv()

#Initialize Flask App 
app = Flask(__name__)

#Adding rate limiting 

limiter = Limiter(
    get_remote_address,
    app=app,
    default_limits=["30 per minute"],
    storage_uri="memory://"
)

groq_client = GroqClient()

##============================== ENDPOINTS =================================================

@app.route('/health',methods = ['GET'])
def health_check():
    return jsonify({"status": "healthy", "service": "ai-service"})

@app.route('/generate-report',methods = ['POST'])
def generate_report():
    data = request.get_json()

    if not data or 'prompt' not in data :
        return jsonify({"error": "Missing prompt in request"}), 400
    
    raw_prompt = data['prompt']

    #We take raw prompt and santizier it using the functions we just made in services folder 

    sanitized_prompt = sanitize_input(raw_prompt)

    if detect_prompt_injection(sanitized_prompt):
        return jsonify({"error": " Security Risk - Potential prompt injection detected."}), 400

    try:
        result = groq_client.generate(sanitized_prompt)
        return jsonify({"result": result})
    except Exception as e:
        return jsonify({"is_fallback": True, "error": str(e)}), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
       
    



