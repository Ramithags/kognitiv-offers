# Base image
FROM openjdk:8-jdk-alpine

# Add a volume pointing to /tmp
VOLUME /tmp

# Add jar file to container. JAR_FILE also provided as argument
ARG JAR_FILE='build/libs/offers-0.0.1-SNAPSHOT.jar'
ADD ${JAR_FILE} app.jar

# Run the jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]


FROM openjdk:8-jdk-alpine

VOLUME /tmp