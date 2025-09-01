# Use an official OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven build files
COPY pom.xml .
COPY src ./src

# Build the WAR
RUN apt-get update && apt-get install -y maven && mvn clean package

# Expose port
EXPOSE 8080

# Run the WAR
CMD ["java", "-jar", "target/core.war"]
