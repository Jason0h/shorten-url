FROM eclipse-temurin:17-jdk-focal
LABEL authors="jasonoh"

WORKDIR app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

ARG DB_PASSWORD
ENV DB_PATH=jdbc:mysql://shorten-url-database-1.c942cwsouzk1.us-east-2.rds.amazonaws.com:3306/shorturldb
ENV DB_USERNAME=admin
ENV DB_PASSWORD=this.is.a.fake.password

EXPOSE 8080

CMD SPRING_PROFILES_ACTIVE=prod DB_PATH=$DB_PATH DB_USERNAME=$DB_USERNAME DB_PASSWORD=$DB_PASSWORD ./mvnw spring-boot:run