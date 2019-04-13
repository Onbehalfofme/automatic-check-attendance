FROM gradle:jdk8

COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew build --no-daemon


EXPOSE 8080

COPY build/libs/*.jar ./app.jar
VOLUME /images

CMD [ "java", "-jar", "./app.jar" ]