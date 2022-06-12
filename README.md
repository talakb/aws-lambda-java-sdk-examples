# AWS Lambda Java SDK examples.
 
## 1 -   Synchronous (RequestResponse) Handler


 ### Serverless weather API to add/read weather info.

* A simple example with CRUD operation to accept request via API Gateway and interact with DynamoDB backend table. AWS services used:
  -  API Gateway
  -  Lambda
  -  DynamoDB
  
![WeatherAPI-Serverless-Example drawio](https://user-images.githubusercontent.com/5312958/173243136-610cbb94-c816-4ac7-9306-fddd30c16fb1.png)

 ## 2 -   Asynchronous (Event) Handler
 
* Client will receive 202-Accepted response but not the actual response detail after processing the function logic. Once processing gets completed, response payload will be sent to a destination:
  - SNS topic
  - SQS queue
  - Lambda function
  - EventBridge event bus
  - Kinesis or DynamoDB stream
 
 * Destinations  needs to be configured in the Lambda function config. in AWS Console or using Lambda API.
  
 ### Send response to a queue (SQS and DLQ)
  

![aws-java-sdk-examples-ambda-GreetingAsync](https://user-images.githubusercontent.com/5312958/166472905-f97aaf7d-a08f-43b5-a26a-18eb6bcdf9ba.svg)

 ## 3 -   Stream Processing Handler (TODO)
 
 * The event trigger will be DynamoDB stream (events from CRUD operations on an item).
 * Lambda function logic receives the stream even from DynamoDB and executes custom logic.
