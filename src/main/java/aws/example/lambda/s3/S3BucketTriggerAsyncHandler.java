package aws.example.lambda.s3;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import aws.example.lambda.util.EventObjectConverter;

/**
 * Example to show how to integrate S3 with Lambda.
 * 1. S3 bucket actions (CRUD object) will trigger this function asynchronously.
 * 2. The function logs S3 bucket/object events and metadata. sent as a payload from S3.
 * 3. Send the output into a different bucket.   
 *
 */
public class S3BucketTriggerAsyncHandler implements RequestHandler<Object, String> {
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public String handleRequest(Object event, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("Enter " + this.getClass().getName() + " handleRequest");

		// log execution details
		logger.log("ENVIRONMENT VARIABLES (Runtime MicroVM/Firecracker env't variables): " + gson.toJson(System.getenv()));
		logger.log("CONTEXT: (Lambda function metadata/info.) " + gson.toJson(context));

		// process event
		logger.log("EVENT TYPE: S3 Bucket/Object change trigger.");
		logger.log("EVENT: (S3 Bucket/Object metadata sent to the function) " + EventObjectConverter.convertEventObjIntoJson(event));

		// TODO - write the output (incoming payload) into a different bucket or DynamoDB table.
		String response = "Async call response msg/obj sent to a destination (E.g: SQS)";
		
		logger.log("Exit " + this.getClass().getName() + " handleRequest");
		return response;
	}
}
