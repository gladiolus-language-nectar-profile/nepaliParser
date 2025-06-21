FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

# Install Python, pip, Java
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    openjdk-21-jdk \
    && apt-get clean

# Set Java environment
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

# Set working directory
WORKDIR /app

# Copy everything
COPY . .

# Install Python dependencies
RUN pip3 install -r backend/requirements.txt

# Expose port for Flask
EXPOSE 5000

# Run the app
RUN which javac && javac -version
CMD ["python3", "backend/app.py"]
