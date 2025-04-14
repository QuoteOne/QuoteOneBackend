# Stage 1: Build stage
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY . .

# Run tests before building the JAR
RUN cd /app && ./gradlew test

RUN cd /app && ./gradlew bootJar

# Stage 2: Runtime stage
FROM eclipse-temurin:17.0.9_9-jre-ubi9-minimal

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar .

CMD ["java", "-jar", "/app/app-0.0.1.jar", "--spring.config.location=file:/app/resources/application.yaml"]
