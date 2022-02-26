FROM openjdk:8-jre-alpine
COPY ./target/customer-service-1.0.0.jar /opt/applications/
WORKDIR /opt/applications
ENTRYPOINT /usr/bin/java $JAVA_OPTS -jar customer-service-1.0.0.jar
