#!/bin/bash
# entrypoint.sh

# Start Xvfb in the background
Xvfb :99 -screen 0 1920x1080x24 &
export DISPLAY=:99

# Start XFCE in the background
xfce4-session &

# Start the VNC server without a password on port 5900
x11vnc -forever -nopw -rfbport 5900 -display :99 &

# Start noVNC server
websockify --web=/usr/share/novnc/ 6080 localhost:5900 &

# Clone the GitHub repository
git clone https://github.com/yourusername/your-repo.git /app/repo

# Navigate to the cloned repository directory
cd /app/repo

# Resolve Maven dependencies
mvn dependency:resolve

# Run Maven tests with specified suite file
mvn test -DsuiteXmlFile=TestCaseRunner/execution_testng.xml
