FROM openjdk:21-jdk-slim-bullseye
RUN apt-get update && apt-get install -y dos2unix
WORKDIR /app
COPY . /app
RUN dos2unix /app/mvnw
RUN chmod +x /app/mvnw
RUN /app/mvnw clean package -DskipTests
RUN cp /app/target/middleware-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar", "-Dserver.port=3000", "/app/app.jar"]