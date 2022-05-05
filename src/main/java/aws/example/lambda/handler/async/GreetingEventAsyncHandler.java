package aws.example.lambda.handler.async;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import aws.example.lambda.util.EventObjectConverter;

public class GreetingEventAsyncHandler implements RequestHandler<Object, String> {
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public String handleRequest(Object event, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("Enter " + this.getClass().getName() + " handleRequest");

		// log execution details
		logger.log("ENVIRONMENT VARIABLES (Runtime env't/VM variables): " + gson.toJson(System.getenv()));
		logger.log("CONTEXT: (Lambda function metadata/info.) " + gson.toJson(context));

		// process event
		logger.log("EVENT TYPE: " + event.getClass().getName());
		logger.log("EVENT: (Input payload sent to) " + this.getClass().getSimpleName() + ": " + EventObjectConverter.convertEventObjIntoJson(event));

		// response returned after processing a specific business logic.
		String response = "Async call response msg/obj sent to a destination (E.g: SQS)";

		logger.log("Exit " + this.getClass().getName() + " handleRequest");
		return response;
	}
}