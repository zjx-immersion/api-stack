#!/bin/bash
docker ps -a | grep zhongjx/api-stack  | awk "{print \$1}"

docker rm -f $(docker ps -a | grep zhongjx/api-stack  | awk "{print \$1}") 1>/dev/null 2>/dev/null

docker images | grep zhongjx/api-stack | awk "{print \$3}"

docker rmi -f $(docker images | grep zhongjx/api-stack | awk "{print \$3}" | sort -u) 1>/dev/null 2>/dev/null


