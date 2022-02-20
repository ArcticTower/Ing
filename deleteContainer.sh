#!/bin/sh
ID=$(<CONT_ID)
docker container rm $ID
rm -f CONT_ID 2>/dev/null
echo "Container removed"
