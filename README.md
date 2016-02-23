Demo application for training session
=====================================

This application is provided as a sample for demonstrating the process of "Dockerization".

The application works, but takes quite a many shortcuts to keep things simple and does not follow all good programming practices.

Previously:

 1. Building Docker Image with Maven

## Phase 2: Let Docker Build Everything

Building Docker image with Maven is convenient as it's possible to use for example
version numbers and names already specified in the pom.xml. Docker images are however
often large (a few hundred megabytes) so uploading them to cloud is slow.

[Docker Hub](https://hub.docker.com/) provides us with a way to build the docker image
**in the cloud**, but requires that the image is built fully using Docker. Dockerfile
is moved to project root and the full build process is described there.

**First build the image:**

```
docker build -t hddemo/tasklist-demoapp:latest .
```

**Then run the container:**

```
docker run -t -i -p 8080:8080 hddemo/tasklist-demoapp:latest
```

The command uses options -i and -t to allow logging to stdout and to keep the container running on foreground.
Option -p is used for port mapping so we can access the application through [localhost:8080](http://localhost:8080/).

**Now we can start using Docker Hub** for automating the image build.

