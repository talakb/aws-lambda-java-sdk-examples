# AWS Lambda Java SDK examples.

1 - Lambda
   -  Handler
      -   sync (RequestResponse): Run custom logic and return response (E.g. 200-OK) to a client.
      -   async (Event): Client will receive 202-Accepted response but not the actual response detail after processing the function logic. Once processing gets completed, response payload will be sent to a destination queue (SQS and DLQ) configured in the function config. in AWS Console.
		
   -  Client
      -   sync (RequestResponse)
      -   async (Event)

2 - S3 bucket and object CRUD
