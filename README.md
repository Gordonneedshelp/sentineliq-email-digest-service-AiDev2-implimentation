# SentinelIQ Email Digest Service

SentinelIQ is an intelligent email digestion and analysis system. The AI Service is a core component responsible for processing email content and generating structured summaries and action items.

---

## 🧠 AI Service (Python/Flask)

The AI Service is a specialized Python microservice that leverages the **Groq Cloud API** and **Llama 3.1 8B** to perform high-speed email analysis.

### 🚀 Key Features
- **High-Performance AI**: Uses the `llama-3.1-8b-instant` model for near-instantaneous processing.
- **Structured Output**: Automatically parses AI responses into clean JSON objects (Summary, Action Items, etc.).
- **Resilient Logic**: Implemented with 3-retry backoff logic and automated fallback mechanisms.
- **Security First**: 
  - Input sanitization and prompt injection detection.
  - Rate limiting (30 requests/min).
  - Runs as a **non-root user** inside Docker for enhanced security.

### 🛠️ Technology Stack
- **Framework**: Flask
- **WSGI Server**: Gunicorn (Production-grade)
- **AI Engine**: Groq SDK
- **Containerization**: Docker / Docker Compose

### 📦 Installation & Setup

#### **1. Environment Variables**
Create a `.env` file in the root directory:
```env
GROQ_API_KEY=your_api_key_here
```

#### **2. Running with Docker Compose (Recommended)**
```bash
docker-compose up --build
```
The service will be available at `http://localhost:5000`.

#### **3. Local Development**
```bash
cd ai-service
python -m venv .venv
.\.venv\Scripts\Activate.ps1  # Windows
pip install -r requirements.txt
python app.py
```

### 🛣️ API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/health` | Check if the service and Groq API connection are healthy. |
| `POST` | `/generate-report` | Process email content. Expects `{"prompt": "..."}`. |

### 🧪 Testing
Run automated tests using pytest:
```bash
cd ai-service
pytest
```