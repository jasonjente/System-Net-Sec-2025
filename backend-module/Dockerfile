FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
# create a non-root user <secure_player> to run everything
RUN useradd -m secure_player
COPY --from=build /app/target/*.jar app.jar
RUN chown -R secure_player:secure_player /app
EXPOSE 8443
USER secure_player
ENTRYPOINT ["java", "-Xms512m", "-Xmx2048m", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseContainerSupport", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
