FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy everything (ALL modules)
COPY pom.xml .
COPY patient-micro1 ./patient-micro1
COPY billing_services_1 ./billing_services_1

# Build only patient-micro1, but allow reactor
RUN mvn clean package -pl patient-micro1 -am -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

RUN apt-get update \
    && apt-get install -y netcat \
    && rm -rf /var/lib/apt/lists/*

COPY --from=builder /app/patient-micro1/target/*.jar app.jar

EXPOSE 8081 5005
ENTRYPOINT ["java","-jar","app.jar"]


