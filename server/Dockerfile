FROM eclipse-temurin:17-jdk-focal as build
WORKDIR /app
COPY . .
RUN ./gradlew :server:build

FROM eclipse-temurin:17-jdk-focal as run
WORKDIR /app
COPY --from=build /app/server/build/libs/server-0.0.1-SNAPSHOT.jar /app/server.jar
CMD [ "java", "-jar", "server.jar" ]