## Gantry

User-friendly web-based container service management platform

[![Docker Image Build](https://github.com/gantry-project/gantry-docker/actions/workflows/docker-publish.yml/badge.svg)](https://github.com/gantry-project/gantry-docker/actions/workflows/docker-publish.yml)

## Getting started

### 1) Running the applications using Docker
```bash
$ docker run -d -p 8080:8080 --name api-server ghcr.io/gantry-project/gantry-docker:release api-server
$ docker run -d -p 3000:3000 --name front ghcr.io/gantry-project/gantry-docker:release front
```

#### If you want to add custom environments for the front
```bash
# Create env.js file
$ cat << EOF > env.js
window.env = {
  "REACT_APP_GANTRY_API_SERVER_HOST": "http://public-ip:8080",
}
EOF
# Run the front with a volume
$ docker run -d -p 3000:3000 --name front -v env.js:/gantry-docker/build/front/env.js ghcr.io/gantry-project/gantry-docker:release front
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
