# JAlgoArena Queue [![Build Status](https://travis-ci.org/spolnik/JAlgoArena-Queue.svg?branch=master)](https://travis-ci.org/spolnik/JAlgoArena-Queue) [![codecov](https://codecov.io/gh/spolnik/JAlgoArena-Queue/branch/master/graph/badge.svg)](https://codecov.io/gh/spolnik/JAlgoArena-Queue) [![GitHub release](https://img.shields.io/github/release/spolnik/jalgoarena-queue.svg)]()

JAlgoArena Queue is microservice dedicated for publishing incoming submissions to Apache Kafka cluster.

- [Introduction](#introduction)
- [REST API](#rest-api)
- [Components](#components)
- [Continuous Delivery](#continuous-delivery)
- [Infrastructure](#infrastructure)
- [Running Locally](#running-locally)
- [Notes](#notes)

## Introduction

- JAlgoArena Queue allows user to publish solution for chosen problem so it may be queued within Apache Kafka and processed later on via JAlgoArena Judge agent.

![Component Diagram](https://github.com/spolnik/JAlgoArena-Queue/raw/master/design/component_diagram.png)

## REST API

|Endpoint|Description|
|----|---------------|
|POST /problems/:problemId/publish|Publish submission|

## Components

- [JAlgoArena](https://github.com/spolnik/JAlgoArena)
- [JAlgoArena UI](https://github.com/spolnik/JAlgoArena-UI)
- [JAlgoArena Judge](https://github.com/spolnik/JAlgoArena-Judge)
- [JAlgoArena Auth Server](https://github.com/spolnik/JAlgoArena-Auth)
- [JAlgoArena API Gateway](https://github.com/spolnik/JAlgoArena-API)

## Continuous Delivery

- initially, developer push his changes to GitHub
- in next stage, GitHub notifies Travis CI about changes
- Travis CI runs whole continuous integration flow, running compilation, tests and generating reports
- coverage report is sent to Codecov

## Infrastructure

- Xodus (embedded highly scalable database) - http://jetbrains.github.io/xodus/
- Spring Boot, Spring Cloud
- Apache Kafka
- TravisCI - https://travis-ci.org/spolnik/JAlgoArena-Queue

## Running locally

There are two ways to run it - from sources or from binaries.

### Running from binaries
- go to [releases page](https://github.com/spolnik/JAlgoArena-Queue/releases) and download last app package (JAlgoArena-Queue-[version_number].zip)
- after unpacking it, go to folder and run `./run.sh` (to make it runnable, invoke command `chmod +x run.sh`)
- you can modify port in run.sh script, depending on your infrastructure settings. The script itself can be found in here: [run.sh](run.sh)

### Running from sources
- run `git clone https://github.com/spolnik/JAlgoArena-Queue` to clone locally the sources
- now, you can build project with command `./gradlew clean stage` which will create runnable jar package with app sources. Next, run `java -Dserver.port=8989 -jar build\libs\jalgoarena-queue-*.jar` which will start application
- there is second way to run app with gradle. Instead of running above, you can just run `./gradlew clean bootRun`

## Notes
- [Running locally](https://github.com/spolnik/jalgoarena/wiki)
- [Travis Builds](https://travis-ci.org/spolnik)

![Component Diagram](https://github.com/spolnik/JAlgoArena/raw/master/design/JAlgoArena_Logo.png)
