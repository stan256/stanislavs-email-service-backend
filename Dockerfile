FROM azul/zulu-openjdk-alpine:11
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Dserver.port=$PORT","-jar","/app.jar"]
