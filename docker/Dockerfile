FROM rockylinux:9 AS builder
WORKDIR /

RUN yum update -y && \
    yum install -y git java-17-openjdk-devel.x86_64 nodejs

RUN git clone -b release https://github.com/gantry-project/gantry-docker.git

WORKDIR /gantry-docker

RUN bin/build-api-server.sh
RUN bin/test-api-server.sh
RUN bin/deploy-api-server.sh

RUN echo "REACT_APP_GANTRY_API_SERVER_HOST=http://api-server:8080" >> /gantry-docker/front/.env
RUN bin/build-front.sh
RUN bin/test-front.sh
RUN bin/deploy-front.sh


FROM openjdk:17
WORKDIR /gantry-docker

RUN curl -SL https://nodejs.org/dist/v18.15.0/node-v18.15.0-linux-x64.tar.gz | tar xzv && \
     mv node-v18.15.0-linux-x64 node && \
     export PATH=${PATH}:$(pwd)/node/bin && \
     cd node/bin && \
     node npm install -g serve

COPY --from=builder /gantry-docker/front /gantry-docker/front
COPY --from=builder /gantry-docker/build /gantry-docker/build
COPY --from=builder /gantry-docker/bin /gantry-docker/bin

EXPOSE 3000/tcp
EXPOSE 8080/tcp

ENV PATH=${PATH}:/gantry-docker/node/bin

ENTRYPOINT ["bin/start.sh"]
CMD ["api-server"]
