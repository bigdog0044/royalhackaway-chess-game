# Stage 1: Build the application with Maven
# This stage uses a Maven image to compile the Java code and package it.
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project definition file
COPY pom.xml .

# Copy the source code
COPY src ./src

# Run Maven to package the application into a JAR file.
# -DskipTests skips running the tests to speed up the build.
RUN mvn package -DskipTests


# Stage 2: Create the final lightweight production image
# This stage uses a minimal JRE image to reduce the final image size.
FROM eclipse-temurin:17-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file from the 'builder' stage
COPY --from=builder /app/target/*.jar /app/app.jar

# Expose the port that the Spring Boot application runs on
EXPOSE 8080

# Run the Spring Boot application using the JAR file
ENTRYPOINT ["java","-jar","/app/app.jar"]
