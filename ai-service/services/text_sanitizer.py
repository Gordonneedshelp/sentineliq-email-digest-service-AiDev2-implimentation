import re 

def sanitize_input(text):
    # Day 3: Strip HTML
    clean_text = re.sub(r'<.*?>', '', text)
    return clean_text


def detect_prompt_injection(text):
    # Day 3: Detect prompt injection
    suspicious_phrases = [
        "ignore previous instructions",
        "system prompt",
        "disregard all prior",
        "you are now",
        "bypass"
    ]
    text_lower = text.lower()
    for phrase in suspicious_phrases:
        if phrase in text_lower:
            return True
    return False