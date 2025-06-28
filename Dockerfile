FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

# Install Python, pip, Java 14, and other necessary tools
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    openjdk-17-jdk \
    bash \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Set Java environment (using Java 17 as Java 14 is not available in Ubuntu 22.04)
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

# Create a symlink to match your script's expected path
RUN ln -sf /usr/lib/jvm/java-17-openjdk-amd64 /usr/lib/jvm/java-14-openjdk-amd64

# Set working directory
WORKDIR /app

# Copy the entire project
COPY . .

# Install Python dependencies
RUN pip3 install -r backend/requirements.txt

# Create necessary directories
RUN mkdir -p /app/uploads /app/outputs

# Ensure the Nepali Parser directory exists and copy Java files if they exist
RUN mkdir -p "/app/Nepali Parser"

# If you have Java files in a specific directory, copy them
# Adjust this path based on where your Java files are located
# COPY "path/to/your/java/files/" "/app/Nepali Parser/"

# Make sure shell scripts are executable (if any exist)
RUN find /app -name "*.sh" -type f -exec chmod +x {} \;

# Verify Java installation
RUN java -version && javac -version

# Expose port for Flask
EXPOSE 5000

# Change to backend directory and run the app
WORKDIR /app/backend
CMD ["python3", "app.py"]
