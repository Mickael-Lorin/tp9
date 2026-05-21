FROM maven:3.9-eclipse-temurin-17 AS build
## installe Maven, compile le projet, génère le .jar
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

## ne contient pas Maven, contient uniquement java
## récupère le .jar, lance l'application
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]


