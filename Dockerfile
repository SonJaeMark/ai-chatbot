# --- Stage 1: Build the JAR ---
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

# Copy everything (to build the JAR)
COPY . .

# Build the JAR using Maven wrapper
RUN ./mvnw clean package -DskipTests

# --- Stage 2: Run the app ---
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Install Redis CLI and PostgreSQL client (optional, for debugging)
RUN apt-get update && \
    apt-get install -y --no-install-recommends redis-tools postgresql-client && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Copy JAR from builder stage
COPY --from=builder /app/target/ai-chatbot-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
