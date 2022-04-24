FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /home/app
COPY src src
COPY pom.xml ./