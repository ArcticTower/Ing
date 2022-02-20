# Ing
Test task for work

###To run in your PC:

require:

  java 11
  
usage:

java -jar Ing.jar file.csv


###To run in docker:

(privileges same as to run docker)

run "./buildDockerContainer.sh" to get container

run "runWithFile.sh your_file" to send your_file to container and get result

result will be saved in your host as "result.txt"

run "./deleteContainer.sh" if you want to delete cantainer

