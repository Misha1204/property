FROM node:latest as build
WORKDIR /usr/local/app
COPY ./frontend /usr/local/app/
RUN npm install
RUN npm run build

FROM maven:3.8.5-eclipse-temurin-17
WORKDIR /app
COPY backend /app
COPY --from=build /usr/local/backend/src/main/resources/static /app/src/main/resources/static
RUN mvn clean install
CMD ["java", "-jar", "target/catalogue-0.0.1-SNAPSHOT.jar", "--spring.config.location=file:///app/application.properties"]