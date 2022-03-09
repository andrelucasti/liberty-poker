FROM maven:3.8.2-jdk-11 as builder

COPY ./ ./

RUN mvn package


FROM maven:3.8.2-openjdk-11-slim

WORKDIR /usr/src/app

COPY --from=builder core/target/*.jar planning-poker.jar
EXPOSE 8080
EXPOSE 8081
EXPOSE 9958

ENV SPRING_PROFILES_ACTIVE "h2"

ENTRYPOINT [
    "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}",
    "-jar /usr/src/app/planning-poker.jar"]