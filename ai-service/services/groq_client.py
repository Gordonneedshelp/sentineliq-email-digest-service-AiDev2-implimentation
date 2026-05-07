import os
import time
import logging
from groq import Groq
from dotenv import load_dotenv

load_dotenv()

logging.basicConfig(level=logging.INFO) # Using logging library instead of print messages for cleaner log messages 
logger = logging.getLogger(__name__)


class GroqClient:
    def __init__(self):
        self.api_key = os.getenv("GROQ_API_KEY")
        if not self.api_key:
            logger.warning("GROQ_API_KEY is not set in environment variables!")
        self.client = Groq(api_key=self.api_key)
        self.model = "llama-3.1-8b-instant" # Change Model here 

    # Implementing 3-retry with backoff, error logging.

    def generate(self,prompt,max_retries=3):
        retries = 0
        backoff = 1 #Start with 1 sec delay 

        while retries < max_retries:
            try:
                response = self.client.chat.completions.create(
                    messages = [{"role": "user","content":prompt}],
                    model = self.model,
                    temperature = 0.2
                )
                return response.choices[0].message.content
            
            except Exception as e: 
                logger.error(f"Groq API Error on attempt {retries + 1}: {str(e)}")
                retries += 1
                if retries < max_retries:
                    time.sleep(backoff)
                    backoff *=2
                else:
                    logger.error("Max retries reached. Returning fallback.")
                    return None
        
