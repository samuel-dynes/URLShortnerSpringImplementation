FROM openjdk:21

WORKDIR /app

# Copy the Spring Boot application JAR file
COPY target/url-shortener.jar app.jar

# Command to run the application
CMD ["java", "-jar", "app.jar"]
