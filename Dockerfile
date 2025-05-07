### Build del JAR)
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY target/*.jar app.jar

### Alpine Image
FROM eclipse-temurin:21-jdk-alpine AS alpine
WORKDIR /app
COPY --from=builder /app/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

### Distroless Image
FROM gcr.io/distroless/java21-debian12 AS distroless
WORKDIR /app
COPY --from=builder /app/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# 9010 9011