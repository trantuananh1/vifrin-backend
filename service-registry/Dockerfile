FROM openjdk:11
VOLUME /tmp
ARG JAR_FILE
COPY target/${JAR_FILE} service-registry.jar
CMD java -jar /service-registry.jar