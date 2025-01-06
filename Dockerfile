FROM openjdk:17
LABEL authors="list_in_user"
WORKDIR /app
COPY target/list-in.jar list-in.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "list-in.jar"]