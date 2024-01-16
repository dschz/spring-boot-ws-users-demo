FROM eclipse-temurin:17

LABEL mentainer="dschz@laposte.net"

WORKDIR /app

COPY target/springboot-restful-webservices-0.0.1-SNAPSHOT.jar /app/springboot-restful-webservices.jar

ENV spring.profiles.active=docker

ENTRYPOINT ["java", "-jar", "springboot-restful-webservices.jar"]
