# Step 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Compile and package the application to an executable JAR
RUN mvn clean package -DskipTests

# Step 2: Run the application
FROM openjdk:17-slim

# Set the deployment directory
WORKDIR /app

# Copy the JAR from the build stage to the run stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
CMD ["java", "-DAPI_URL=https://api.sparesti.org", "-jar", "app.jar"]

