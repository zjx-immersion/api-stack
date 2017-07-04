#!/bin/bash
docker ps -a | grep zhongjx/api-stack  | awk "{print \$1}"

docker rm -f $(docker ps -a | grep zhongjx/api-stack  | awk "{print \$1}")

docker images | grep zhongjx/api-stack | awk "{print \$3}"

docker rmi -f $(docker images | grep zhongjx/api-stack | awk "{print \$3}" | sort -u)


