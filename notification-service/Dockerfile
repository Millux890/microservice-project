FROM openjdk:17

COPY target/*.jar app.jar

LABEL authors="millu"

ENTRYPOINT ["java","-jar","/app.jar"]