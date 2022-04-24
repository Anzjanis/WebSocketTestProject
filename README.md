# This project was created as example how to test websockets

## Prerequisites
* [Docker for Desktop](https://www.docker.com/get-started)
* [JDK](https://www.oracle.com/java/technologies/downloads/)
* [mvn](https://maven.apache.org/download.cgi)

## Solution information:
* Test cases are located in: src/test/resources/org/example/features
  * All of them are written using cucumber
  * As visible test cases have data tables attached to them, it makes easy to generate new test cases around
specific logic. Good example: Subscription error messages
  * Test case used:  I create unsuccessful subscription about spread and then receive notification about it
* Cucumber step impl. is located: src/main/java/org/example/steps
  * All test data within scenario is stored in: src/main/java/org/example/context/TestContext.java
    * This allows scenario to access information in runtime and share between other step classes
    * Scenario can be run in parallel 
* Environment config can be defined in src/test/resources/org/example/environments
* WebSocket functionality is located: src/main/java/org/example/sockets 
  * WebSocketKrClient have basic client which extend library: WebSocketClient (Java-WebSocket)
  * WebSocketLogic have very basic traffic controller which divides messages into types and writes them in 
TestContext for future reference
* Models used for this testing can be found src/main/java/org/example/models
  * These models are used for basic data storing
  * To Serialize/deserialize data I used src/main/java/org/example/helpers/Serializer.java
* All test are capable of running in parallel 



## To run tests locally:
    * command line
        mvn clean test -Dtest=RunCucumberParallelTest -Denv=dev 
        mvn clean test -Dtest=RunCucumberTest -Denv=dev
    * test runners
        src/test/java/runners
### Reports:
* Cucumber reports are located - target/testReports
* In future probably should add something like Allure, for better readability

## To run tests using docker:
    * To run tests on docker:
        1. Build image: docker build -t app:latest .
        2. Start docker image: docker run -it --name samplecontainer app:latest /bin/bash
        3. Start test execution: mvn -f /home/app/pom.xml clean test -Dtest=RunCucumberParallelTest -Denv=dev
    * Reports available: cd target/testReports


    
