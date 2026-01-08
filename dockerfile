FROM maven:4.0.0-rc-5-amazoncorretto-25-debian-trixie AS build

WORKDIR /app
COPY /backend/pom.xml /app/pom.xml
COPY /backend/src /app/src
RUN mvn clean package -DskipTests

FROM amazoncorretto:21

WORKDIR /app
COPY --from=0 /app/target/backend-0.0.1-SNAPSHOT.jar /app/backend.jar
CMD [ "java","-jar","backend.jar"]



