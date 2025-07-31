#FROM openjdk:8-jre
#FROM openjdk:11-jre
FROM openjdk:17-slim
#FROM openjdk:21-slim

LABEL version="1.0"
LABEL description="Spring Boot Docker Jenkins Image"
COPY ./target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8081