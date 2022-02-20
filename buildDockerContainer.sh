#!/bin/sh
docker pull tomokito/ing-csv-txt
c_id=$(docker create tomokito/ing-csv-txt)
id_file="CONT_ID"
rm -f "$id_file" 2> /dev/null
echo "$c_id" > "$id_file"
echo "Container is ready for work!"
echo "run ./runWithFile your_file_name"
