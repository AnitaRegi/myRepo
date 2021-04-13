FROM openjdk:8-jdk-alpine
ADD target/*.jar app.jar
VOLUME /tmp
EXPOSE 5000
#Place the config file as a part of application
ADD src/main/resources/application.properties application.properties
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]