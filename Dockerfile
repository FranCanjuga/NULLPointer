#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY DoniranjeKrvi-be /app/
RUN mvn clean package

# Build stage for frontend
FROM node:14 AS frontend-build
WORKDIR /frontend
COPY doniranje_krvi_front /frontend/
RUN npm install
RUN npm run build

#
# Package stage
#
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
COPY --from=frontend-build /frontend/build /app/src/main/resources/static

EXPOSE 8080 3000
ENTRYPOINT ["java","-jar","app.jar"]

