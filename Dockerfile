FROM openjdk:21.0.4-jdk

ENV APP_HOME=/app

ARG JAR_FILE_PATH=build/libs/demo-0.0.1-SNAPSHOT.jar

WORKDIR $APP_HOME

COPY $JAR_FILE_PATH app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
