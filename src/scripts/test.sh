#!/bin/bash -e

export CONFIG_ENV=local

#Would normally using local stack
docker run --name sqltest -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=yourStrong(!)Password' -p 1433:1433 -d mcr.microsoft.com/mssql/server:2017-CU8-ubuntu

./gradlew test

docker stop sqltest
docker rm sqltest