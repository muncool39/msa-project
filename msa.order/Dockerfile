FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY build/libs/order-0.0.1-SNAPSHOT.jar order-service.jar
EXPOSE 19096
CMD ["java", "-jar", "order-service.jar"]