#!/usr/bin/env bash
ACCESS_TOKEN=$1

./scripts/stop.sh
./scripts/build.sh
./scripts/dockerize.sh
./scripts/run.sh $1