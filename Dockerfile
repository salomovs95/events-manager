FROM openjdk:17-ea-5-jdk-alpine as build
WORKDIR /app
COPY . .
RUN ./mvnw clean -DskipTests install

FROM openjdk:17-ea-5-jdk-alpine
COPY --from=build /app/target/event.generator-0.0.5.jar ./app.jar
CMD [ "java", "-jar", "./app.jar" ]
