import os 
from groq import Groq
from dotenv import load_dotenv

#Load env vars
load_dotenv()

api_key=os.getenv("GROQ_API_KEY")

if not api_key:
    print("ERROR: GROQ_API_KEY not found in environment variables")
else: 
    client = Groq(api_key=api_key)
    response = client.chat.completions.create(
        messages=[{"role": "user", "content": "Say hello!"}],
        model="llama-3.1-8b-instant"
    )
    print("Sucessfully Connected to Groq! AI says: ",response.choices[0].message.content)
    