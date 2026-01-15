
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy billing service pom and source
COPY pom.xml .
COPY src ./src

# Build billing service only
RUN mvn clean package -DskipTests

# ===== Runner stage =====
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the jar
COPY --from=builder /app/target/billing_services_1-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4001 9001
ENTRYPOINT ["java", "-jar", "app.jar"]

