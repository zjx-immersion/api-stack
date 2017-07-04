#!/bin/sh
PROFILE=${ACTIVE_PROFILE:=local}
java -Xmx512m -Djava.security.egd=file:/dev/./urandom -jar app.jar --spring.profiles.active=$PROFILE
echo "Api server is running !"

