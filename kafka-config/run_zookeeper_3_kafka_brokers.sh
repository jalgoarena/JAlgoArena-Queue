export PATH=/Users/jacek/kafka_2.12-1.1.0/bin:$PATH
cd /Users/jacek/github/JAlgoArena-Queue/kafka-config
nohup zookeeper-server-start.sh zookeeper.properties > zookeeper.log 2>&1 &
sleep 2
JMX_PORT=10000 nohup kafka-server-start.sh kafka_1.properties > kafka1.log 2>&1 &
JMX_PORT=10001 nohup kafka-server-start.sh kafka_2.properties > kafka2.log 2>&1 &
JMX_PORT=10002 nohup kafka-server-start.sh kafka_3.properties > kafka3.log 2>&1 &
sleep 2
echo "All started"
