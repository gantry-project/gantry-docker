### Gantry

User-friendly web-based container service management platform

[![Docker Image Build](https://github.com/gantry-project/gantry-docker/actions/workflows/docker-publish.yml/badge.svg)](https://github.com/gantry-project/gantry-docker/actions/workflows/docker-publish.yml)

### Getting started

#### 1. build applications
```bash
$ bin/build-api-server.sh
$ bin/deploy-api-server.sh
$ bin/build-front.sh
$ bin/deploy-front.sh
```

#### 2. run applications
```bash
$ bin/start.sh api-server
$ bin/start.sh front
```

### Swagger URL and H2 Console

After starting the api-server, you can access the Swagger UI and H2 Console.

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 Console : http://localhost:8080/h2-console
