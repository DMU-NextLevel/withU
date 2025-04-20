# Step 1: Use a base image with JDK
FROM openjdk:17-jdk-slim as build

# Step 2: Set the working directory for Spring Boot app
WORKDIR /app

# Step 3: Copy the JAR file into the container
COPY target/my-spring-boot-app.jar /app/my-spring-boot-app.jar

# Step 4: Expose port 8080 for the Spring Boot app
EXPOSE 8080

# Step 5: Command to run the Spring Boot application
CMD ["java", "-jar", "my-spring-boot-app.jar"]

# Step 6: Use Nginx base image for static file serving
FROM nginx:latest

# Step 7: Copy the Nginx config file
COPY src/main/resources/springboot.conf /etc/nginx/sites-available/springboot.conf

# Step 8: Ensure sites-enabled exists and create symlink
RUN mkdir -p /etc/nginx/sites-enabled && \
    ln -s /etc/nginx/sites-available/springboot.conf /etc/nginx/sites-enabled/springboot.conf


# Step 10: Copy the image files to be served by Nginx
RUN mkdir src/main/resources/static/img

# Step 11: Expose ports for both Spring Boot (8080) and Nginx (80, 443)
EXPOSE 80 443 8080

# Step 12: Start both Nginx and Spring Boot app
CMD service nginx start && java -jar /app/my-spring-boot-app.jar
