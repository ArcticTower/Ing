#base image
FROM openjdk:11
RUN mkdir /Ing
WORKDIR /Ing
COPY ./out/artifacts/Ing_jar/Ing.jar /Ing/
COPY ./dockerFiles/lng.csv /Ing/
RUN ["java","-jar","Ing.jar","lng.csv"]
