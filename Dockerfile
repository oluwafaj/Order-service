# -------------------------------------------------------------------
# Stage 1: Build the Jar using Maven & JDK 21
# -------------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# -------------------------------------------------------------------
# Stage 2: Lightweight Runtime Image
# -------------------------------------------------------------------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create non-root system group and user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy compiled JAR from builder stage
COPY --from=builder /app/target/order-service-1.0.0.jar app.jar

# Adjust permissions
RUN chown -R appuser:appgroup /app

USER appuser

EXPOSE 8080

ENV WEBSITES_PORT=8080
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
