FROM openjdk:8-jdk-alpine
EXPOSE 8080
VOLUME /tmp
ARG JAR_FILE=target/jobdog-monolith-app-exec.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]