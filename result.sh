#!/bin/bash

result(){
  delay=$1
  array=( 999 9999 99999 999999 9999999 )
  for amount in "${array[@]}"
  do
    echo "Generating result of $amount with $delay delay"
    printf "# JSON"
  	time curl -s "http://localhost:9191/api/faker?amount=$amount&delay=$delay" > "$amount.json"
  	printf "# GZIP"
  	time curl -s -H "Accept-Encoding: gzip" "http://localhost:9191/api/faker?amount=$amount&delay=$delay" > "$amount.json.gz"
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

time result 0

docker-compose down -v