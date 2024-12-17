FROM openjdk:17-jdk-slim

WORKDIR /app

RUN ./gradlew clean build -x test

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 19090

ENTRYPOINT ["java", "-jar", "/eureka-server.jar"]