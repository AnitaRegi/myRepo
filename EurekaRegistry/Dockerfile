FROM openjdk:8-jdk-alpine
ADD target/*.jar eureka.jar
VOLUME /tmp
EXPOSE 8761
#Move config file to docker container
ADD src/main/resources/application.properties application.properties
ENTRYPOINT [ "sh", "-c", "java -jar /eureka.jar" ]