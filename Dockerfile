FROM gradle:6.8.3-jdk11 AS BUILDER
WORKDIR /app
COPY . /app/
RUN gradle build

FROM openjdk:11-jre-slim AS RUNNER
WORKDIR /app
COPY --from=BUILDER /app/build/libs/demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1.jar
ENV  LOG_LEVEL=${LOG_LEVEL}
ENV API_VERSION=${API_VERSION}
ENV AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}
ENV AWS_S3_BUCKET_NAME=${AWS_S3_BUCKET_NAME}
ENV AWS_REGION=${AWS_REGION}
ENV AWS_S3_PROFILE_IMAGE_DIRECTORY=${AWS_S3_PROFILE_IMAGE_DIRECTORY}
ENV AWS_S3_DEFAULT_PROFILE_IMAGE_URL=${AWS_S3_DEFAULT_PROFILE_IMAGE_URL}
ENV DB_HOST=${DB_HOST}
ENV DB_PORT=${DB_PORT}
ENTRYPOINT ["java","-jar","demo-0.0.1.jar"]

