FROM openjdk:20
EXPOSE 8080
WORKDIR /cars-01-web
ADD data data
ADD api/target/api.jar api.jar
ADD persistence/target/persistence.jar persistence.jar
ADD service/target/service.jar service.jar
ENTRYPOINT ["java", "-jar", "api.jar"]