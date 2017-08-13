#!/bin/bash

# Need to Install jq to extract json


host="localhost:8082"
#host="10.202.128.167:8080"
echo -e "\n\n----------------\n"
echo "Try to get api version"
curl -s http://$host/api/version

echo -e "\n\n----------------\n"
echo "Try to login"

curl -s -i -H "Content-Type: application/json" -XPOST http://$host/api/login -d '{"username":"admin","password":"admin"}'

#curl -s -H "Authorization: {AboveAuthorizationID}" -XGET http://$host/api/secure/product/123/123

echo -e "\n\n----------------\n"
echo "Try to refresh"

bear="Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJiMDBmZGYzYi1mZWVjLTQyNWMtODY1ZS02ZTE0ZGRjMDI0NWUiLCJzdWIiOiJhZG1pbiIsImV4cCI6MTUwMzUwMjAxNH0.EpB-RPKz_XEeTGICCag9aUMwlLkGgFAh8bRk4s4WeStKMEkbAJJUH7jxNFjOU3aSrak4IzAODs08JosiT5FKNA"
curl -s -i -H "$bear" -XPOST http://$host/api/secure/token/refresh


echo -e "\n\n----------------\n"
echo "Try to update password by wrong oldPassword"
curl -s -i -H "Content-type: application/json" -H "$bear" -XPUT http://$host/api/secure/user/password -d '{"oldPassword": "hello", "newPassword": "hello"}'

echo -e "\n\n----------------\n"
echo "Try to update password by correct password"
curl -s -i -H "Content-type: application/json" -H "$bear" -XPUT http://$host/api/secure/user/password -d '{"oldPassword": "admin", "newPassword": "password"}'


echo -e "\n\n----------------\n"
echo "Try to login with new password"
curl -s -i -H "Content-Type: application/json" -XPOST http://$host/api/login -d '{"username":"admin","password":"password"}'


curl -s -i -H "Authorization: Bearer ff" -XGET http://$host/api/secure/qrcode


