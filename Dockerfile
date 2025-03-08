FROM openjdk:17-ea-5-jdk-alpine as build
WORKDIR /app
COPY pom.xml ./pom.xml
COPY src/ ./src/
RUN mvn clean -DskipTests install

FROM openjdk:17-ea-5-jdk-alpine
COPY --from=build /app/target/event.generator-0.0.5.jar ./app.jar
CMD [ "java", "-jar", "./app.jar" ]
