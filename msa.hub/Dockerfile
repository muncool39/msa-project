FROM openjdk:17-jdk-slim

WORKDIR /app

RUN ./gradlew clean build -x test

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 19093

ENTRYPOINT ["java", "-jar", "/hub.jar"]