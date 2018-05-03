#!/usr/bin/env bash
pm2 stop queue
pm2 delete queue
./gradlew clean
./gradlew stage
pm2 start pm2.config.js