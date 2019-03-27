FROM openjdk:8-jdk-alpine

EXPOSE 8080

WORKDIR /opt
COPY backend/build/libs/*.jar ./app.jar
VOLUME /opt/images

CMD [ "java", "-jar", "./app.jar" ]