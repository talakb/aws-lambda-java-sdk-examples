#!/bin/bash
#TODO - parametrize  below variables to use the script for other examples too 
#./uploadArtifactsToS3.sh aws-java-sdk-examples-source-code lambda lambda-1.0-SNAPSHOT.jar

BUCKET_NAME=aws-java-sdk-examples-source-code
FOLDER_NAME=lambda
aws s3 cp ../target/lambda-1.0-SNAPSHOT.jar s3://$BUCKET_NAME/$FOLDER_NAME