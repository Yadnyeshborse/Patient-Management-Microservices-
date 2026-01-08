#FROM ubuntu:latest
#LABEL authors="DELL"
#
#ENTRYPOINT ["top", "-b"]


#FROM mysql:8.0
#
#ENV MYSQL_ROOT_PASSWORD=root
#ENV MYSQL_DATABASE=new_patient_service_db
#ENV MYSQL_USER=rooot
#ENV MYSQL_PASSWORD=root
#
#EXPOSE 3306

# ===== Builder stage =====
# ===== Builder stage =====
# ===== Builder stage =====
# ===== Builder stage =====
# ===== Builder stage =====
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy parent POM
COPY pom.xml ./pom.xml

# Copy module pom.xml
COPY patient-micro1/pom.xml ./patient-micro1/pom.xml

# Copy source code
COPY patient-micro1/src ./patient-micro1/src

# Build jar
RUN mvn -f patient-micro1/pom.xml clean package -DskipTests


# ===== Runner stage =====
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the correct jar from builder stage
COPY --from=builder /app/patient-micro1/target/patient-micro1-1.0-SNAPSHOT.jar ./app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]


