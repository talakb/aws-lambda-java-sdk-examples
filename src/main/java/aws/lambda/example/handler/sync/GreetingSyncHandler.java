package aws.lambda.example.handler.sync;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import aws.lambda.util.EventObjectConverterUtil;

public class GreetingSyncHandler  implements RequestHandler<Object, String> {
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
		try {
			logger.log("EVENT: (Input payload sent to the function) " + EventObjectConverterUtil.toJsonString(event));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// response returned after processing a specific business logic.
		String response = "Sync call response msg/obj...";

		logger.log("Exit " + this.getClass().getName() + " handleRequest");
		return response;
	}
}