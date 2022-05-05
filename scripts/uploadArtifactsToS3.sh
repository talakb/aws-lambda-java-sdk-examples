#!/bin/bash
#TODO - parametrize  below variables to use the script for other examples too 
#./uploadArtifactsToS3.sh aws-java-sdk-examples-source-code lambda lambda-1.0-SNAPSHOT.jar

BUCKET_NAME=$1
FOLDER_NAME=$2
JAR_FILE_NAME=$3


aws s3 cp ../target/$JAR_FILE_NAME s3://$BUCKET_NAME/$FOLDER_NAME
