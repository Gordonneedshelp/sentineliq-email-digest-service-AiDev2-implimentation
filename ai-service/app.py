# Initial Flask Boilerplate - Prepared by AI Dev 2 [Shashank Khot] to unblock development from AI developer 1

import os 
from flask import Flask,request,jsonify
from flask_limiter import Limiter
from flask_limiter.util import get_remote_address
from dotenv import load_dotenv
import groq 
import json

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
    
    email_content = data['prompt']

    #We take raw prompt and santizier it using the functions we just made in services folder 

    email_content = sanitize_input(email_content)

    if detect_prompt_injection(email_content):
        return jsonify({"error": " Security Risk - Potential prompt injection detected."}), 400

    try:
        # Generate raw response from Groq
        ai_response_raw = groq_client.generate(email_content)
        
        if ai_response_raw is None:
            return jsonify({"is_fallback": True, "error": "Groq API failed after retries"}), 200

        # Parse the stringified JSON from AI into a Python dictionary
        try:
            parsed_result = json.loads(ai_response_raw)
            return jsonify({"result": parsed_result})
        except (json.JSONDecodeError, TypeError):
            # Fallback if AI returns invalid JSON
            return jsonify({
                "result": ai_response_raw,
                "warning": "AI returned non-JSON content",
                "is_hallucination": True
            })

    except Exception as e:
        return jsonify({"is_fallback": True, "error": str(e)}), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
       
    



