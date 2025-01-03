FROM openjdk:17
LABEL authors="abdulaxad"
WORKDIR /app
COPY target/list-in.jar list-in.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "list-in.jar"]