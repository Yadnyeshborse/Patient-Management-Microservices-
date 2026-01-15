FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy parent + module POMs
COPY pom.xml .
COPY patient-micro1/pom.xml patient-micro1/pom.xml

# Resolve dependencies
RUN mvn -pl patient-micro1 -am dependency:go-offline

# Copy source
COPY patient-micro1/src ./patient-micro1/src

# Build module
RUN mvn -pl patient-micro1 -am clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/patient-micro1/target/patient-micro1-1.0-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
