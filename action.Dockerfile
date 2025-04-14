# Stage 1: Runtime stage
FROM eclipse-temurin:17.0.9_9-jre-ubi9-minimal

WORKDIR /app

COPY build/libs/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar", "--spring.config.location=file:/app/resources/application.yaml"]