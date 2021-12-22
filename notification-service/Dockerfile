FROM openjdk:11
VOLUME /tmp
ARG JAR_FILE
COPY target/${JAR_FILE} user-service.jar

COPY dockerize dockerize
CMD ./dockerize -wait tcp://pg-database:5432 -timeout 15m java -jar /post-service.jar