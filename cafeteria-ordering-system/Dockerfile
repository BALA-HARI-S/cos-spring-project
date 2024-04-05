FROM eclipse-temurin:17-jdk-alpine

RUN apk add --no-cache curl

VOLUME /tmp

WORKDIR /app

COPY /cafeteria-ordering-system-0.0.1-SNAPSHOT.jar /app/cos-app.jar

EXPOSE 8080

CMD ["java", "-jar", "cos-app.jar"]
