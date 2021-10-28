FROM openjdk:11
VOLUME /tmp
ARG JAR_FILE
COPY target/${JAR_FILE} cloud-config-server.jar

COPY dockerize dockerize
CMD ./dockerize -wait tcp://pg-database:5432 -timeout 15m java -jar /cloud-config-server.jar