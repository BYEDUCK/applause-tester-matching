#!/bin/sh

mvn clean package
docker build -t applause .
docker run -it --rm applause