#!/bin/bash
docker ps -a | grep zhongjx/api-stack  | awk "{print \$1}"

#docker rm -f $(docker ps -a | grep zhongjx/api-stack  | awk "{print \$1}")
docker ps -a | grep zhongjx/api-stack  | awk "{print \$1}" | xargs --no-run-if-empty docker rm -f

docker images | grep zhongjx/api-stack | awk "{print \$3}"

#docker rmi -f $(docker images | grep zhongjx/api-stack | awk "{print \$3}" | sort -u)

docker images | grep zhongjx/api-stack | awk "{print \$3}" | sort -u | xargs --no-run-if-empty docker rmi -f

