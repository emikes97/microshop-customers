FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q test package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar /app/
RUN set -eux; \
    JAR="$(ls /app/*.jar | grep -v plain | head -n 1)"; \
    echo "Using jar: $JAR"; \
    mv "$JAR" /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]