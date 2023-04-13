## Gantry

User-friendly web-based container service management platform

[![Docker Image Build](https://github.com/gantry-project/gantry-docker/actions/workflows/docker-publish.yml/badge.svg)](https://github.com/gantry-project/gantry-docker/actions/workflows/docker-publish.yml)

## Getting started

### 2) Running the applications using Docker
```bash
$ docker run -d -p 8080:8080 --name api-server ghcr.io/gantry-project/gantry-docker:release api-server
$ docker run -d -p 3000:3000 --name front ghcr.io/gantry-project/gantry-docker:release front
```

### 2) Build and run applications in local
```bash
$ bin/build-api-server.sh
$ bin/build-front.sh

$ bin/deploy-api-server.sh
$ bin/deploy-front.sh

$ bin/start.sh api-server
$ bin/start.sh front
```

### Swagger URL and H2 Console

After starting the api-server, you can access the Swagger UI and H2 Console.

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 Console : http://localhost:8080/h2-console
