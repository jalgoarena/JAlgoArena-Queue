#!/usr/bin/env bash
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 3 --partitions 6 --topic submissions
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 3 --partitions 6 --topic results