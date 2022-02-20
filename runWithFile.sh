#!/bin/sh
FILE=$1
ID=$(<CONT_ID)
docker cp "$FILE" $ID:/Ing/lng.csv
docker start $ID
docker cp $ID:/Ing/result.txt .
echo "Result saved in file result.txt"
