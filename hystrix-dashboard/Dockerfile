FROM openjdk:11
VOLUME /tmp
ARG JAR_FILE
COPY target/${JAR_FILE} hystrix-dashboard.jar

COPY dockerize dockerize
CMD java -jar /hystrix-dashboard.jar