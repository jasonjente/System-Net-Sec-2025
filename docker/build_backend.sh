#!/bin/bash
# navigates to backend
cd ../backend-module
# packages the jar
mvn clean package

# copies the jar to the docker workspace
cp target/*.jar ../docker/app.jar

# move to docker workspace
cd ../docker