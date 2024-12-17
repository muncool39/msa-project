FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY build/libs/delivery-0.0.1-SNAPSHOT.jar company-service.jar
EXPOSE 19094
CMD ["java", "-jar", "company-service.jar"]
