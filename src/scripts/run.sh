#!/usr/bin/env bash

ACCESS_TOKEN=$1
docker network create \
 --subnet=172.18.0.0/16 \
 mynet

docker run --net mynet --ip="172.18.0.22" --name sql1 -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=yourStrong(!)Password' -p 1433:1433 -d mcr.microsoft.com/mssql/server:2017-CU8-ubuntu

sleep 15
docker run --net mynet --name github-repo -p 8080:8080 --env ACCESS_TOKEN=${ACCESS_TOKEN} thomasmillergb/github-repo-downloader:1.0.0
