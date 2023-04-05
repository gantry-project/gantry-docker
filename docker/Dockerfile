FROM rockylinux:9 AS builder
WORKDIR /

RUN yum update -y && \
    yum install -y git java-17-openjdk-devel.x86_64 nodejs

RUN git clone https://github.com/gantry-project/gantry-docker.git

WORKDIR /gantry-docker

RUN bin/build-api-server.sh
RUN bin/test-api-server.sh
RUN bin/deploy-api-server.sh

RUN bin/build-front.sh
RUN bin/test-front.sh
RUN bin/deploy-front.sh


FROM openjdk:17
WORKDIR /gantry-docker

RUN curl -SL https://nodejs.org/dist/v18.15.0/node-v18.15.0-linux-x64.tar.gz | tar xzv && \
    export PATH=${PATH}:${pwd}/node-v18.15.0-linux-x64/bin && \
    npm install -g serve

COPY --from=builder /gantry-docker/build /gantry-docker/build
COPY --from=builder /gantry-docker/bin /gantry-docker/bin

EXPOSE 8080

ENTRYPOINT ["bin/start.sh"]
CMD ["api-server"]