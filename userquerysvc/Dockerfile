# Build Stage
FROM maven:3-openjdk-11 AS builder
COPY ./ /home/app
WORKDIR /home/app
RUN mvn dependency:go-offline
RUN mvn clean install -DskipTests

# Packaging Stage
FROM openjdk:11.0.10-jdk
COPY --from=builder /home/app/userquerysvc/target/userquerysvc-1.0.0.jar /home/app/userquerysvc-1.0.0.jar

ENTRYPOINT ["java", "-jar", "/home/app/userquerysvc-1.0.0.jar"]


