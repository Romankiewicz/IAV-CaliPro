FROM openjdk:17
EXPOSE 8080
ADD "backend/target/CaliPro.jar" app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]