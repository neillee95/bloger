FROM openjdk:8-jdk-alpine
COPY src/main/resources/application-dev.yml /application.yml
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar", "--spring.config.name=/application.yml"]