FROM maven:4.0.0-openjdk-21 AS build
WORKDIR /backend
COPY pom.xml .
COPY src ./src
# COPY .. else
RUN mvn clean package -DskipTests
# Stage 2: Run the application using OpenJDK
#change version to 21.0.4 if any prblm occurs
FROM openjdk:21-jdk-slim
COPY --from=build /target/backend-0.0.1-SNAPSHOT.jar.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]
