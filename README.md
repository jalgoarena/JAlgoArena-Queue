# JAlgoArena Queue [![Build Status](https://travis-ci.org/spolnik/JAlgoArena-Queue.svg?branch=master)](https://travis-ci.org/spolnik/JAlgoArena-Queue) [![codecov](https://codecov.io/gh/spolnik/JAlgoArena-Queue/branch/master/graph/badge.svg)](https://codecov.io/gh/spolnik/JAlgoArena-Queue) [![GitHub release](https://img.shields.io/github/release/spolnik/jalgoarena-queue.svg)]()

JAlgoArena Queue is microservice dedicated for publishing incoming submissions to Apache Kafka cluster.

- [Introduction](#introduction)
- [API](#api)
- [Running Locally](#running-locally)
- [Notes](#notes)

## Introduction

- JAlgoArena Queue allows user to publish solution for chosen problem so it may be queued within Apache Kafka and processed later on via JAlgoArena Judge agent.

![Component Diagram](https://github.com/spolnik/JAlgoArena-Queue/raw/master/design/component_diagram.png)

## API

#### Sign up

  _Create a new user_

|URL|Method|
|---|------|
|_/problems/:problemId/publish_|`POST`|

* **Data Params**

  _Submission json for given problem id e.g. `fib`_
  
  `POST /problems/fib/publish`
  
  ```json
  {
    "sourceCode": "<source code>",
    "userId": "1"
  }
  ```

  > Note: you have to submit token via header - `'X-Authorization': Bearer <token>`

* **Success Response:**

  _You will get confirmation that your solution was submitted_

  * **Code:** 200 <br />
    **Content:** `TBC`


* **Sample Call:**

  ```bash
  curl --header "Content-Type: application/json" \
       --data '{"sourceCode":"<source code>","userId":"1"}' \
       http://localhost:5003/signup
  ```

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
