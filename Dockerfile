#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY DoniranjeKrvi-be /app/
RUN mvn clean package -e


#
# Package stage
#
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080 
ENTRYPOINT ["java","-jar","app.jar"]

