#
# Package stage
#
FROM openjdk:11-jre-slim
COPY target/user-service.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]