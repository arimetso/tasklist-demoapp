Demo application for training session
=====================================

This application is provided as a sample for demonstrating the process of "Dockerization".

The application works, but takes quite a many shortcuts to keep things simple and does not follow all good programming practices.

## Phase 1: Building Docker Image with Maven

Use Spotify's [docker-maven-plugin](https://github.com/spotify/docker-maven-plugin) to build the Docker image.
Plugin configuration is added to pom.xml and basic Dockerfile is added under the docker directory.

**First build the image:**

```
mvn clean package docker:build
```

**Then run the container:**

```
docker run -t -i -p 8080:8080 hddemo/tasklist-demoapp:0.1-SNAPSHOT
```

The command uses options -i and -t to allow logging to stdout and to keep the container running on foreground.
Option -p is used for port mapping so we can access the application through [localhost:8080](http://localhost:8080/).


