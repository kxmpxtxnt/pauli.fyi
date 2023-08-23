FROM gradle:jre-alpine

COPY . .
ENV SERVER_PORT=3000
ENV SERVER_HOST=locahost
ENV DEBUG=false

RUN gradle build

FROM eclipse-temurin:19-alpine as runtime

WORKDIR /app

ENTRYPOINT ["java", "-jar" , "app.jar"]