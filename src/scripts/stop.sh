#!/usr/bin/env bash

docker stop sql1
docker rm sql1
docker stop github-repo
docker rm github-repo

docker network rm mynet