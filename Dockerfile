FROM openjdk:8-jre-alpine

MAINTAINER Jacek Spolnik <jacek.spolnik@gmail.com>

WORKDIR /app
ADD build/libs/jalgoarena-queue-*.jar /app/

EXPOSE 5007
CMD java -jar /app/jalgoarena-queue-*.jar