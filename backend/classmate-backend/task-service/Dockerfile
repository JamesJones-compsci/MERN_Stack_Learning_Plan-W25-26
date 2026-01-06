# ---------- Build stage ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY gradlew gradlew.bat ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew

COPY src ./src
RUN ./gradlew bootJar --no-daemon

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8082

# Added to debug task-service/mongoDB connection issues
COPY wait-for-it.sh ./
RUN chmod +x wait-for-it.sh

# Use exec form ENTRYPOINT so env variables are passed correctly
ENTRYPOINT ["java","-jar","app.jar"]
