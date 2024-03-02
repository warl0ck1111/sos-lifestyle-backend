#FROM openjdk:11-jdk-slim AS build
#WORKDIR /app
#COPY . .
#RUN ./mvnw clean package -DskipTests
#
#FROM openjdk:11-jre-slim
#WORKDIR /app
#COPY --from=build /workspace/build/libs/*.jar app.jar
#CMD ["java", "-jar", "sosInventory-0.0.1.jar"]

FROM openjdk:11-jdk-slim AS build

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

FROM openjdk:11-jre-slim

WORKDIR /app

COPY --from=build /app/target/sosInventory-0.0.1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
