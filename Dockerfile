FROM openjdk:11-slim

EXPOSE 8080

COPY ./build/libs/demo-0.0.1-SNAPSHOT.jar /demo-0.0.1.jar

ENV DOCKERIZE_VERSION v0.6.1

RUN apt-get update && apt-get install -y wget \
 && wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
 && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
 && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

#ENTRYPOINT ["java","-jar","/demo-0.0.1.jar"]