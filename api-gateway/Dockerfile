FROM openjdk:17

#COPY out/artifacts/api_gateway_jar/*.jar app.jar

COPY target/*.jar app.jar

LABEL authors="millu"

ENTRYPOINT ["java","-jar","/app.jar"]