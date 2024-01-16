# Stage 1: Build with Maven
FROM maven:4.0.0-openjdk-17 AS builder
WORKDIR /app
COPY ./src ./src
COPY ./pom.xml .
COPY src/main/resources/static/images /app/src/main/resources/static/images


# Ajout des ressources statiques (CSS, JS, images) dans le répertoire du conteneur
#COPY src/main/resources/static /app1/static
#COPY src/main/resources/templates /app1/templates
#COPY src/main/resources/templates/machine/style /app1/templates/machine/style
RUN mvn clean package


# Stage 2: Create the final image
FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8089
# Commande d'entrée pour exécuter l'application
ENTRYPOINT ["java","-jar","/app.jar"]