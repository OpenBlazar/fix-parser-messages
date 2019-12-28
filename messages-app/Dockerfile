FROM openjdk:13-alpine

ARG VERSION=*

COPY target/messages-app-${VERSION}.jar /opt/messages-app.jar

CMD ["java", "-jar", "-Dspring.profiles.active=default", "/opt/messages-app.jar"]