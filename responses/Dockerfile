FROM gradle:jdk19-alpine as build

COPY . .
ENV SERVER_PORT=3000
ENV SERVER_HOST=locahost
ENV DEBUG=false
RUN gradle build

FROM eclipse-temurin:19-alpine as runtime

WORKDIR /app

COPY --from=build /home/gradle/build/libs/responses.pauli.fyi-*-all.jar responses.jar

ENTRYPOINT ["java", "-jar" , "responses.jar"]