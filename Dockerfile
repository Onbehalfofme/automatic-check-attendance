FROM openjdk:8-jre-alpine
ADD backend/build/libs/backend-0.0.1-SNAPSHOT.jar /opt

EXPOSE 8080
WORKDIR /opt
VOLUME [ "/opt/images" ]

CMD [ "java", "-jar", "backend-0.0.1-SNAPSHOT.jar" ]
