FROM openjdk:11
VOLUME /tmp
ARG JAR_FILE
COPY target/${JAR_FILE} cloud-gateway.jar

COPY dockerize dockerize
CMD java -jar /cloud-gateway.jar