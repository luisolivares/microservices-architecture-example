FROM openjdk:17-alpine
ADD target/*.jar app-rest.jar
ENTRYPOINT ["java","-jar","app-rest.jar"]