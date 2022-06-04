package aws.example.lambda.api.gateway.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import aws.example.lambda.api.gateway.response.ApiGatewayResponse;
import aws.example.lambda.util.EventObjectConverter;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

public class WeatherEventHandler implements RequestHandler<Object, ApiGatewayResponse> {
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private LambdaLogger log;
	private final ObjectMapper objectMapper = new ObjectMapper();
	final ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
	final Region region = Region.US_EAST_1;
	final DynamoDbClient dynamoDbClient = DynamoDbClient.builder().credentialsProvider(credentialsProvider)
			.region(region).build();

	@Override
	public ApiGatewayResponse handleRequest(Object event, Context context) {
		ApiGatewayResponse response = new ApiGatewayResponse();

		log = context.getLogger();
		log.log("Enter " + this.getClass().getName() + " handleRequest");

		// log execution details
		log.log("ENVIRONMENT VARIABLES (Runtime env't/VM variables): " + gson.toJson(System.getenv()));
		log.log("CONTEXT: (Lambda function metadata/info.) " + gson.toJson(context));

		// process event
		log.log("EVENT TYPE: " + event.getClass().getName());
		log.log("EVENT: (Input payload sent to the function) " + EventObjectConverter.convertEventObjIntoJson(event));

		log.log("Exit " + this.getClass().getName() + " handleRequest");
		return response;
	}

	private Map<String, AttributeValue> getDynamoDBItem(String tableName, String key, String keyVal) {
		Map<String, AttributeValue> returnedItem = null;
		HashMap<String, AttributeValue> keyToGet = new HashMap<>();
		keyToGet.put(key, AttributeValue.builder().s(keyVal).build());

		GetItemRequest request = GetItemRequest.builder().key(keyToGet).tableName(tableName).build();

		try {
			returnedItem = dynamoDbClient.getItem(request).item();
		} catch (DynamoDbException e) {
			log.log("Exception: \n" + ExceptionUtils.getStackTrace(e));
		} finally {
			dynamoDbClient.close();
		}

		return returnedItem;
	}

	public void scanItems(String tableName, Integer limit) {

		try {
			ScanRequest scanRequest = ScanRequest.builder()
									.tableName(tableName)
									.limit(limit)
									.build();

			ScanResponse response = dynamoDbClient.scan(scanRequest);

			for (Map<String, AttributeValue> item : response.items()) {
				Set<String> keys = item.keySet();

				for (String key : keys) {
					System.out.println("The key name is " + key + "\n");
					System.out.println("The value is " + item.get(key).s());
				}
			}

		} catch (DynamoDbException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
