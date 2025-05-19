FROM eclipse-temurin:21-jre
WORKDIR /app

# Create non-root user
RUN useradd -m secure_player

# Copy your already-built JAR file
COPY app.jar app.jar

RUN chown -R secure_player:secure_player /app
EXPOSE 8443
USER secure_player
ENTRYPOINT ["java", "-jar", "app.jar"]
