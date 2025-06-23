
# Use a lightweight Java 21 image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the jar into the image
COPY ClassroomLibrary_jar.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the jar
CMD ["java", "-Xmx480m", "-Xms128m", "-jar", "app.jar"]

