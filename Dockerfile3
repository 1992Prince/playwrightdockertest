# Use the official Playwright image as the base image
FROM mcr.microsoft.com/playwright/java:v1.45.1-jammy

# Install additional packages not included in the base image
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y \
    xvfb \
    xfce4 \
    xfce4-panel \
    xfce4-session \
    xfce4-settings \
    x11vnc \
    novnc \
    websockify \
    git && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven POM file and resolve dependencies (this layer is cached if pom.xml doesn't change)
COPY pom.xml /app
RUN mvn dependency:resolve

# Copy the rest of the project files
COPY src /app/src
COPY src/test/resources/config.properties /app/src/test/resources/config.properties

# Copy the entrypoint script
COPY entrypoint.sh /app/entrypoint.sh

# Make the entrypoint script executable
RUN chmod +x /app/entrypoint.sh

# Set the entrypoint to the script
ENTRYPOINT ["/app/entrypoint.sh"]

# Expose ports for VNC and noVNC
EXPOSE 5900
EXPOSE 6080
