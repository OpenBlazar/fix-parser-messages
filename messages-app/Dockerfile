FROM openjdk:13-alpine

COPY target/messages-app-*.jar /opt/messages-app.jar

CMD ["java", "-jar", "-Dspring.profiles.active=default", "/opt/messages-app.jar"]