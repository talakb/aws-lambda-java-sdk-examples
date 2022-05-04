# AWS Lambda Java SDK examples.

 
 1 -   async (Event): Client will receive 202-Accepted response but not the actual response detail after processing the function logic. Once processing gets completed, response payload will be sent to a destination queue (SQS and DLQ) configured in the lambda function config. in AWS Console.
 

![aws-java-sdk-examples-ambda-GreetingAsync](https://user-images.githubusercontent.com/5312958/166472905-f97aaf7d-a08f-43b5-a26a-18eb6bcdf9ba.svg)



