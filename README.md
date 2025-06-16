# MCP Client using Spring Shell and Ollama

## Custom Docker image for Ollama
To build a custom Docker image for Ollama, you can use the following Dockerfile:

### Dockerfile
```dockerfile
FROM ollama/ollama:latest

# Install curl
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy and configure the init script
COPY init.sh /init.sh
RUN chmod +x /init.sh

ENTRYPOINT ["/init.sh"]
```

### Init Script
```bash
#!/bin/sh

# Start ollama in the background
ollama serve &

# Wait for the server to be up
until curl -s http://localhost:11434/version > /dev/null; do
  echo "Waiting for Ollama server..."
  sleep 2
done

# Pull the desired models
ollama pull llama3
ollama pull nomic-embed-text

# Keep the server running in the foreground
wait
```

To run the Ollama server with the custom image, you can use the following command:
```shell
docker-compose up --build
```

## Running the application
To run the Spring Shell application, you can use the following command:
```shell
# build the project
./gradlew clean build

# run the application
./gradlew bootRun

# you can running using the jar file
java -jar build/libs/mcp-client-<version>.jar
```

## Using the application
To send a message to the Ollama server, you must use the `chat` command in the Spring Shell. Here is an example of how to use it:
```shell
chat Hello, how are you?
```
