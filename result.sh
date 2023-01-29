#!/bin/bash

result(){
  array=( 999 9999 99999 999999 )
  for amount in "${array[@]}"
  do
  	curl -s "http://localhost:9191/api/faker?amount=$amount" > "$amount.json"
  	curl -s -H "Accept-Encoding: gzip" "http://localhost:9191/api/faker?amount=$amount" > "$amount.json.gz"
  done

  ls -lha *.json*
  gzip -l *.json.gz
  rm *.json*
}

docker-compose up -d
sleep 3

time result

docker-compose down