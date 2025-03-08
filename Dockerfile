FROM ubuntu as build
WORKDIR /app
COPY pom.xml ./pom.xml
COPY src/ ./src/
RUN apt-get install openjdk-17-jdk maven && \
    mvn clean -DskipTests install

FROM openjdk:17-ea-5-jdk-alpine
COPY --from=build /app/target/event.generator-0.0.5.jar ./app.jar
CMD [ "java", "-jar", "./app.jar" ]
