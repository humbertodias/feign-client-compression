#!/bin/bash

result(){
  array=( 999 9999 99999 )
  for amount in "${array[@]}"
  do
    echo "Generating result of $amount"
  	curl -s "http://localhost:9191/api/faker?amount=$amount" > "$amount.json"
  	curl -s -H "Accept-Encoding: gzip" "http://localhost:9191/api/faker?amount=$amount" > "$amount.json.gz"
  done

  ls -lha *.json*
  gzip -l *.json.gz
  rm *.json*
}

wait_health(){
  printf "\nWaiting heath"
  until $(curl -s localhost:9191/actuator/health | grep -q UP); do
      printf '.'
      sleep 2
  done
  printf "OK\n"
}


docker-compose up -d
wait_health

time result

docker-compose down