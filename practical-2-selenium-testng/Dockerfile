FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

RUN apt-get update && apt-get install -y wget gnupg ca-certificates \
    && mkdir -p /usr/share/keyrings \
    && wget -qO- https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/google-chrome.gpg \
    && echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

COPY pom.xml .
COPY testng.xml .
COPY src ./src
COPY webpage ./webpage

CMD ["mvn", "--batch-mode", "clean", "test"]
