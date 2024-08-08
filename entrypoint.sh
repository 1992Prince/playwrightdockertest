#!/bin/bash
# entrypoint.sh

echo "Starting Xvfb..."
# Start Xvfb in the background with a smaller screen resolution
Xvfb :99 -screen 0 1280x720x24 &
export DISPLAY=:99
echo "Started Xvfb on DISPLAY=:99 with resolution 1280x720"

echo "Starting XFCE session..."
# Start XFCE in the background
xfce4-session &
echo "Started XFCE session"

echo "Starting x11vnc..."
# Start the VNC server without a password on port 5900
x11vnc -forever -nopw -rfbport 5900 -display :99 &
echo "Started x11vnc on DISPLAY=:99 port 5900"

echo "Starting noVNC..."
# Start noVNC server
websockify --web=/usr/share/novnc/ 6080 localhost:5900 &
echo "Started noVNC on port 6080"

echo "Cloning GitHub repository..."
# Clone the GitHub repository
git clone https://github.com/1992Prince/playwrightdockertest.git /app/repo
echo "Cloned GitHub repository"

echo "Navigating to the cloned repository directory..."
# Navigate to the cloned repository directory
cd /app/repo

echo "Resolving Maven dependencies..."
# Resolve Maven dependencies
mvn dependency:resolve
echo "Resolved Maven dependencies"

echo "Running Maven tests..."
# Run Maven tests with specified suite file
mvn test -DsuiteXmlFile=TestCaseRunner/execution_testng.xml
echo "Started Maven tests"
