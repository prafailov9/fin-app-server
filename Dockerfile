# Multi-Stage Build: This Dockerfile uses a multi-stage build. The first stage (build) compiles the project using the official Maven image.
# The second stage creates the final image containing only the JRE and the built JAR file.

# Caching: The RUN mvn dependency:go-offline command is optional.
# It's there to download dependencies so that Docker can cache them. This speeds up subsequent builds.

# Final Image: The final image is based on openjdk:11-jre-slim, which is a minimal image with the Java Runtime Environment (JRE) installed.
# You can adjust the Java version as needed.

# JAR File: The JAR file is copied from the build stage to the final image. Replace my-app.jar with the name of your JAR file.

# Port: The EXPOSE 8080 instruction exposes port 8080. Adjust this to match the port your server runs on.

#To build and run the Docker image, you can use the following commands:
# docker build -t my-java-server .
# docker run -p 8080:8080 my-java-server

# Use the official Maven image to build the project
FROM maven:3.8.2-jdk-11 as build

# Set the working directory
WORKDIR /app

# Copy the Maven pom.xml file to the container
COPY pom.xml .

# Download project dependencies (this step is optional but improves caching)
RUN mvn dependency:go-offline

# Copy the source code to the container
COPY src ./src

# Build the project
RUN mvn clean package

# Use the official OpenJDK image as the base image for the final image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/my-app.jar /app/my-app.jar

# Expose the server port
EXPOSE 8080

# Run the server
CMD ["java", "-jar", "/app/fin-app-server.jar"]
