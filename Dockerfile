# Use the official OpenJDK image for a base
FROM openjdk:17-jdk-slim

# Set a working directory
WORKDIR /app

# Copy the built artifact from the target folder to the app folder
COPY ./target/booktify-*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run your app
CMD ["java", "-jar", "app.jar"]
