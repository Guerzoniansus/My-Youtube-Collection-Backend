# Stage 1: Build the Spring application
FROM maven:3.8.4-openjdk-17 as build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
ARG BRANCH_NAME
RUN if [ "$BRANCH_NAME" = "main" ]; then mvn -B package --file pom.xml -Pprod; else mvn -B package --file pom.xml -Pdev; fi

# Stage 2: Run the application
FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
