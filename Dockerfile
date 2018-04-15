FROM openjdk:8

WORKDIR /app
ADD build/libs/jalgoarena-queue-*.jar /app/

ENV EUREKA_URL=http://eureka:5000/eureka
ENV BOOTSTRAP_SERVERS=kafka1:9092,kafka2:9093,kafka3:9094
EXPOSE 5007

CMD java -Dserver.port=5007 -jar /app/jalgoarena-queue-*.jar