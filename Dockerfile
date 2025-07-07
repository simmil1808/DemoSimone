# 1. Stage di build: usa Maven con JDK 21 per compilare il progetto
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia solo pom.xml prima per sfruttare la cache di Maven
COPY pom.xml .

# Copia il codice sorgente
COPY src ./src

# Build del jar senza test (per velocizzare)
RUN mvn clean package -DskipTests

# 2. Stage di runtime: immagine leggera con solo JDK 21
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copia il jar generato dal builder
COPY --from=builder /app/target/DemoSimone-0.0.1-SNAPSHOT.jar app.jar

# Esponi la porta 8080
EXPOSE 8080

# Avvia l'app Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]