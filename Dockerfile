FROM openjdk:8-jdk-alpine

EXPOSE 8080

COPY backend/build/libs/*.jar ./app.jar
VOLUME /images

CMD [ "java", "-jar", "./app.jar" ]