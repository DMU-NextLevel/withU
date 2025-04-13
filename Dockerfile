FROM openjdk:21-slim

ENV APP_HOME=/app

ARG JAR_FILE_PATH=build/libs/demo-0.0.1-SNAPSHOT.jar

WORKDIR $APP_HOME

RUN mkdir -p /app/img/

COPY src/main/resources/keystore.p12 $APP_HOME/keystore.p12

COPY $JAR_FILE_PATH app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
