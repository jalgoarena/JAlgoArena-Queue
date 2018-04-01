#!/usr/bin/env bash
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 10 --topic submissions
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 10 --topic results