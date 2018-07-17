FROM openjdk:8-jre-alpine

MAINTAINER Jacek Spolnik <jacek.spolnik@gmail.com>

WORKDIR /app
COPY build/libs/jalgoarena-queue-*.jar /app/

EXPOSE 5007
CMD java $JAVA_OPTS -jar /app/jalgoarena-queue-*.jar