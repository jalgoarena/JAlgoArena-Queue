#!/usr/bin/env bash
kill $(ps aux | grep -e kafka_2.12-1.1.0 | awk '{ print $2 }')
