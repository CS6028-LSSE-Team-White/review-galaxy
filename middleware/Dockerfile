FROM openjdk:21
COPY . .
RUN ./mvnw clean package -DskipTests
# Copy the jar file to the root directory
RUN cp target/middleware-0.0.1-SNAPSHOT.jar app.jar