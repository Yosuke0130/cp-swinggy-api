FROM openjdk:11-slim

EXPOSE 8080

COPY ./build/libs/demo-0.0.1-SNAPSHOT.jar /demo-0.0.1.jar

ENTRYPOINT ["java","-jar","/demo-0.0.1.jar"]