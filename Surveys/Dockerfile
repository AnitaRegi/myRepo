FROM openjdk:8-jdk-alpine
ADD target/*.jar survey-app.jar
VOLUME /tmp
EXPOSE 5000
#Move onfig file to docker container
ADD src/main/resources/application.properties application.properties
ENTRYPOINT [ "sh", "-c", "java -jar /survey-app.jar" ]