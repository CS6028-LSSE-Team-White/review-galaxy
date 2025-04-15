# ---------- Stage 1: Build the application ----------
FROM openjdk:21-jdk-slim-bullseye AS builder

# Install dos2unix to convert Windows line endings
RUN apt-get update && apt-get install -y dos2unix

WORKDIR /app
COPY . /app

# Convert the Maven wrapper script and make it executable
RUN dos2unix /app/mvnw && chmod +x /app/mvnw

# Build the application, skipping tests
RUN /app/mvnw clean package -DskipTests

# Copy the built JAR to a known location
RUN cp /app/target/middleware-0.0.1-SNAPSHOT.jar /app/app.jar

# ---------- Stage 2: Create the runtime image ----------
FROM openjdk:21-jdk-slim-bullseye

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=builder /app/app.jar /app/app.jar
COPY --from=builder /app/zoom-features.csv /app/zoom-features.csv
COPY --from=builder /app/zoom-reviews.csv /app/zoom-reviews.csv
COPY --from=builder /app/zoom-features.csv /app/webex-features.csv
COPY --from=builder /app/zoom-reviews.csv /app/webex-reviews.csv
COPY --from=builder /app/zoom-features.csv /app/firefox-features.csv
COPY --from=builder /app/zoom-reviews.csv /app/firefox-reviews.csv

# Expose the port your application listens on (adjust if needed)
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "/app/app.jar"]
