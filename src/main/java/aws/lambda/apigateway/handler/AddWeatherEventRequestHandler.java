package aws.lambda.apigateway.handler;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import aws.lambda.apigateway.model.WeatherEvent;
import aws.lambda.apigateway.request.ApiGateWayRequest;
import aws.lambda.apigateway.response.ApiGatewayResponse;
import aws.lambda.util.EventObjectConverterUtil;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

public class AddWeatherEventRequestHandler implements RequestHandler<ApiGateWayRequest, ApiGatewayResponse> {
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final SimpleDateFormat UTC_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS zzz");
	private LambdaLogger log;
	
    // AWS resources
	// private final ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create(); works in request from IDE but not in AWS runtime env't.
	private final Region region = Region.US_EAST_1;
	private final DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(region).build();

	// Table name configured in AWS Lambda function that will be passed to the hander as part of the env't variables.
	private final String tableName = System.getenv("WEATHER_INFO_TABLE_NAME");
	
	@Override
	public ApiGatewayResponse handleRequest(ApiGateWayRequest event, Context context) {
		log = context.getLogger();
		log.log("Enter " + this.getClass().getName() + " handleRequest");
		ApiGatewayResponse response = new ApiGatewayResponse();

		try {
			// log execution details
			log.log("Runtime MicroVM/Firecracker env't variables: " + gson.toJson(System.getenv()));
			log.log("Context: (Lambda function metadata/info.) " + gson.toJson(context));

			// read/parse incoming event
			log.log("Event type: " + event.getClass().getTypeName());
			String weatherEventJson = event.getBody();
			log.log("Event input payload sent to the function) " + weatherEventJson);

			// Convert input JSON to WeatherEvent object.
			WeatherEvent weatherEvent = EventObjectConverterUtil.toObject(weatherEventJson, WeatherEvent.class);
			addWeatherEvent(weatherEvent);
			
			//prepare response obj./message.
			response.setStatusCode(200);
			response.setBody("Weather event for city " +  weatherEvent.getCityName() + " saved successfully.");

		} catch (Exception e) {
			log.log(ExceptionUtils.getStackTrace(e));
			//prepare response obj/message.
			response.setStatusCode(500); // to show internal server error code.
			response.setBody("Weather event not saved successfully.");
		}
		
		log.log("Exit " + this.getClass().getName() + " handleRequest");
		return response;
	}
	
	
	private void addWeatherEvent(WeatherEvent event) throws Exception {
		log.log("Enter " + this.getClass().getName() + " addWeatherEvent");
		
		if(StringUtils.isBlank(tableName)) {
			throw new Exception("Table name is not passed to the function.");
		}
		

		HashMap<String, AttributeValue> itemValues = new HashMap<>();
		itemValues.put("city-name", AttributeValue.builder().s(event.getCityName()).build());
		itemValues.put("timestamp",
				AttributeValue.builder().s(UTC_DATE_FORMATTER.format(event.getTimestamp())).build());
		itemValues.put("latitude", AttributeValue.builder().n(String.valueOf(event.getLatitude())).build());
		itemValues.put("longitude", AttributeValue.builder().n(String.valueOf(event.getLongitude())).build());
		itemValues.put("temprature", AttributeValue.builder().n(String.valueOf(event.getTemprature())).build());

		try {
			log.log("Table name: " + tableName);
			log.log("Item: " + gson.toJson(itemValues));

			PutItemRequest request = PutItemRequest.builder().tableName(tableName).item(itemValues).build();
			dynamoDbClient.putItem(request);

			log.log("Item added successfully to the table " + tableName);

		} catch (ResourceNotFoundException e) {
			log.log(String.format("Error: DynamoDB table \"%s\" can't be found.\n", tableName));
			throw e;
		} catch (Exception e) {
			log.log(ExceptionUtils.getStackTrace(e));
			throw e;
		}

		log.log("Exit " + this.getClass().getName() + " addWeatherEvent");
	}
}
