FROM python:3.13-slim

# Prevent Python from writing .pyc files and enable unbuffered logging
ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1

WORKDIR /app

# Create a non-root user for security
RUN adduser --disabled-password --gecos "" appuser
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
COPY . .
# Change ownership to the non-root user
RUN chown -R appuser:appuser /app
USER appuser
EXPOSE 5000
# Use gunicorn for production instead of 'python app.py'
CMD ["gunicorn", "--bind", "0.0.0.0:5000", "app:app"]