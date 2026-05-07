# Threat Model and Security Documentation

## Last Updated : 8th May 2026

## 1. Top 3 Identified Threats (By Shashank Khot)
1. **Prompt Injection**: Malicious users injecting commands to override AI instructions.
2. **API Key Exposure**: Accidentally committing the Groq API key to GitHub.
3. **Denial of Service (DoS)**: Overwhelming the Groq API with excessive requests, eating up rate limits.

## 2. Mitigations Implemented
* **Rate Limiting**: Implemented `flask-limiter` (30 req/min) on all endpoints.
* **Input Sanitization**: HTML tags are stripped from all prompts.
* **Injection Detection**: A blacklist of common injection phrases is checked before calling the AI.
* **Secrets Management**: API keys are loaded via `.env` files which are included in `.gitignore`.
