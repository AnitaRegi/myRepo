FROM openjdk:8-jdk-alpine
ADD target/*.jar user-app.jar
VOLUME /tmp
EXPOSE 8087
#Move onfig file to docker container
ADD src/main/resources/application.properties application.properties
ENTRYPOINT [ "sh", "-c", "java -jar /user-app.jar" ]