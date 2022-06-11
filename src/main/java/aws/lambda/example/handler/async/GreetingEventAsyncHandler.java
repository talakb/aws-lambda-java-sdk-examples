package aws.lambda.example.handler.async;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import aws.lambda.util.JsonUtil;

public class GreetingEventAsyncHandler {
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public void handleRequest(Object event, Context context) throws JsonProcessingException { // No response type is expected from async handler. 
		LambdaLogger logger = context.getLogger();
		logger.log("Enter " + this.getClass().getName() + " handleRequest");

		// log execution details
		logger.log("ENVIRONMENT VARIABLES (Runtime env't/VM variables): " + gson.toJson(System.getenv()));
		logger.log("CONTEXT: (Lambda function metadata/info.) " + gson.toJson(context));

		// process event
		logger.log("EVENT TYPE: " + event.getClass().getName());
		logger.log("EVENT: (Input payload sent to) " + this.getClass().getSimpleName() + ": " + JsonUtil.toJsonString(event));

		// response returned after processing a specific business logic.
		//String response = "Async call response msg/obj sent to a destination (E.g: SQS)";
		//TODO - add logic to pass incoming event into S3 bucket or SQS queue.

		logger.log("Exit " + this.getClass().getName() + " handleRequest");
	}
}