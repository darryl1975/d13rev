
#
# Build stage
# 
# FROM tomcat:10.1-jdk21 AS build
FROM tomcat:10-1-jdk17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package


#
# Package stage
#
# FROM tomcat:10.1-jdk21
FROM tomcat:10.1-jdk17
WORKDIR /app

COPY --from=build /home/app/target/d13rev-0.0.1-SNAPSHOT.jar /app/app.jar
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