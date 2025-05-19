#!/bin/bash

CONTAINER_NAME="fair-dice-game"
DOCKERFILE_PATH="../secure-game-backend/docker/Dockerfile"
# Used for volumes to persist the logs on disc instead of on the container.
LOCAL_LOG_DIR="/opt/fair-dice-game/logs"
DOCKER_LOG_DIR="/app/logs"

if [ ! -d "$LOCAL_LOG_DIR" ]; then
  echo "Creating logging directory for fair dice game."
  mkdir -p "$LOCAL_LOG_DIR"
fi

if [ "$(docker ps -a -q -f name=$CONTAINER_NAME)" ]; then
    echo "Container $CONTAINER_NAME is running. Stopping and removing it..."
    docker stop $CONTAINER_NAME
    docker rm $CONTAINER_NAME
else
    echo "No running container named $CONTAINER_NAME found, creating it from scratch."
fi


docker build -f $DOCKERFILE_PATH -t $CONTAINER_NAME .
docker run -d --restart always --name $CONTAINER_NAME $CONTAINER_NAME -u -v $LOCAL_LOG_DIR:$DOCKER_LOG_DIR


