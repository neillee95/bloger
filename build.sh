#!/bin/zsh
gradle clean build -xtest
mkdir -p build/dependency && (cd build/dependency || exit; jar -xf ../libs/*.jar)
sudo docker rmi dearlee/bloger-backend:0.1
sudo docker build -t dearlee/bloger-backend:0.1 .