# Use an official OpenJDK image as a base
FROM openjdk:17-jdk-slim

# Set a working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/doc_sharing-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on (change if needed)
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
