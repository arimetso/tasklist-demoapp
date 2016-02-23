FROM fedora:23

# Install OpenJDK
RUN dnf -y install java-1.8.0-openjdk-headless

# Add application and shorten name
ADD tasklist-demoapp.jar app.jar

# App listens to port 8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
