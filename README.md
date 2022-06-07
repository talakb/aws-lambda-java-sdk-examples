# AWS Lambda Java SDK examples.

 
 ## 1 -   Asynchronous (Event) Handler
 
* Client will receive 202-Accepted response but not the actual response detail after processing the function logic. Once processing gets completed, response payload will be sent to a destination:
  - SNS topic
  - SQS queue
  - Lambda function
  - EventBridge event bus
  - Kinesis or DynamoDB stream
 
 * Destinations  needs to be configured in the Lambda function config. in AWS Console or using Lambda API.
  
 ### Send response to a queue (SQS and DLQ)
  

![aws-java-sdk-examples-ambda-GreetingAsync](https://user-images.githubusercontent.com/5312958/166472905-f97aaf7d-a08f-43b5-a26a-18eb6bcdf9ba.svg)


## 2 -   Synchronous (RequestResponse) Handler


 ### Serverless weather API to add/read weather info.

* A simple example with CRUD operation to accept request via API Gateway and interact with DynamoDB backend table. AWS services used:
  -  API Gateway
  -  Lambda
  -  DynamoDB

![WeatherAPI-Serverless-Example drawio](https://user-images.githubusercontent.com/5312958/172287492-ef6f378c-213d-4213-bad5-161815226943.png)
