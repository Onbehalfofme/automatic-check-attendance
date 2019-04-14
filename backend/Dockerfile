FROM openjdk:8-jdk-alpine as builder

COPY build.gradle gradlew /app/
COPY gradle /app/gradle
COPY src /app/src
WORKDIR /app/
RUN ./gradlew build

FROM openjdk:8-jdk-alpine

EXPOSE 8080
VOLUME /tmp
COPY --from=builder /app/build/libs/*.jar /app/app.jar
CMD [ "java", "-jar", "/app/app.jar" ]