# 1. Usa OpenJDK 21 basato su Alpine (leggero)
FROM eclipse-temurin:21-jdk-alpine

# 2. Imposta la directory di lavoro dentro il container
WORKDIR /app

# 3. Copia il jar dal tuo progetto (PC) dentro la cartella /app del container, rinominandolo app.jar
COPY target/DemoSimone-0.0.1-SNAPSHOT.jar app.jar

# 4. Esponi la porta 8080 del container (Spring Boot usa di default questa)
EXPOSE 8080

# 5. Comando per avviare il jar
ENTRYPOINT ["java", "-jar", "app.jar"]
