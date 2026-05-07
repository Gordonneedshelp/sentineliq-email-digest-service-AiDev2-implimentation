# Initial Flask Boilerplate - Prepared by AI Dev 2 [Shashank Khot] to unblock development from AI developer 1

import os 
from flask import Flask,request,jsonify
from flask_limiter import Limiter
from flask_limiter.util import get_remote_address
from dotenv import load_dotenv
import groq 

#Load environment variables 
load_dotenv()

#Initialize Flask App 
app = Flask(__name__)


