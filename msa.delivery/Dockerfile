FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY build/libs/delivery-0.0.1-SNAPSHOT.jar delivery-service.jar
EXPOSE 19095
CMD ["java", "-jar", "delivery-service.jar"]