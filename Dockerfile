FROM fedora:23

# Install OpenJDK, Maven
RUN dnf -y install java-1.8.0-openjdk-headless maven

# Copy sources and build application
WORKDIR /src/app
COPY pom.xml pom.xml
COPY src src
RUN mvn package

# App listens to port 8080
EXPOSE 8080

WORKDIR target
ENTRYPOINT ["java", "-jar", "tasklist-demoapp.jar"]
