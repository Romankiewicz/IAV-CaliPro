FROM openjdk:20
EXPOSE 80
EXPOSE 8080

ADD "target/backend.jar" app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]