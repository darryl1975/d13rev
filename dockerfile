
#
# Build stage
# 
# FROM tomcat:10.1-jdk21 AS build
# FROM tomcat:10.1-jdk17 AS build
# FROM maven:3.9.5-eclipse-temurin-17 AS build
# FROM eclipse-temurin:17-jdk AS build
FROM maven:3-eclipse-temurin-20 AS build

WORKDIR /src

COPY mvnw .
COPY mvnw.cmd .
COPY src src
COPY pom.xml .
# RUN mvn -f /home/app/pom.xml clean package
RUN mvn package -Dmaven.test.skip=true

#
# Package stage
#
# FROM tomcat:10.1-jdk21
# FROM tomcat:10.1-jdk17
# FROM maven:3.9.5-eclipse-temurin-17
# FROM eclipse-temurin:17-jdk
# FROM maven:3-eclipse-temurin-20
FROM openjdk:20-slim
WORKDIR /app

# COPY --from=build /home/app/target/d13rev-0.0.1-SNAPSHOT.jar /app/app.jar
COPY --from=build /src/target/d13rev-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


# Use Java 21
# FROM tomcat:10.1-jdk21

# LABEL maintainer="darrylng@nus.edu.sg"
# LABEL build_date="2023-11-20"

# https://www.educative.io/answers/what-is-the-workdir-command-in-docker
#define working directory of docker container
# WORKDIR /app

# specifies variables that are available to the COPY instruction
# ARG JAR_FILE=target/*.jar

# COPY JAR FILE without ARG
# COPY target/d13rev-0.0.1-SNAPSHOT.jar d13rev-0.0.1-SNAPSHOT.jar

# COPY with ARGS
# COPY ${JAR_FILE} /app/app.jar

# informs Docker container to listen on the specified network ports at runtime
# EXPOSE 8080

# configure a container that runs as an executable
# ENTRYPOINT ["java", "-jar", "app.jar"]

# https://www.docker.com/blog/9-tips-for-containerizing-your-spring-boot-code/