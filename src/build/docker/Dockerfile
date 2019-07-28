FROM openjdk:8-jdk-alpine

ARG JAR_FILE
ARG VERSION

ENV VERSION=$VERSION

EXPOSE 8080

ENV JAVA_OPTS="-Xms512m -Xmx1024m"

COPY "${JAR_FILE}" /app/app.jar


ENTRYPOINT java ${JAVA_OPTS} $* -jar /app/app.jar