FROM openjdk:17-jdk-slim

WORKDIR /app

RUN ./gradlew clean build -x test

ARG JAR_FILE=build/libs/gateway.jar

COPY ${JAR_FILE} app.jar

EXPOSE 19091

ENTRYPOINT ["java", "-jar", "/gateway.jar"]