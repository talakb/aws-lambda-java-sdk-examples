package aws.lambda.apigateway.handler;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import aws.lambda.apigateway.model.WeatherEvent;
import aws.lambda.apigateway.request.ApiGateWayRequest;
import aws.lambda.apigateway.response.ApiGatewayResponse;
import aws.lambda.util.JsonUtil;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

public class GetWeatherEventRequestHandler implements RequestHandler<ApiGateWayRequest, ApiGatewayResponse> {
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
	public ApiGatewayResponse handleRequest(ApiGateWayRequest request, Context context) {
		log = context.getLogger();
		log.log("Enter " + this.getClass().getName() + " handleRequest");
		ApiGatewayResponse response = new ApiGatewayResponse();

		try {
			// log execution details
			log.log("Runtime MicroVM/Firecracker env't variables: " + gson.toJson(System.getenv()));
			log.log("Context: (Lambda function metadata/info.) " + gson.toJson(context));

			// read/parse incoming event
			log.log("Event type: " + request.getClass().getTypeName());
			String weatherEventJson = request.getBody();
			log.log("Event input payload sent to the function: " + weatherEventJson);

			// Convert input JSON to WeatherEvent object.
			
			JsonNode node = JsonUtil.getJsonMapper().readTree(weatherEventJson);
			String cityName = node.get("city-name").textValue();
			String timestamp = node.get("timestamp").toString();
			//convert to DAte and then to UTC
			Date date = Date.from(Instant.ofEpochMilli(Long.valueOf(timestamp)));
			
			
			return getRecentWeatherInfoByCityName(cityName, UTC_DATE_FORMATTER.format(date));
			

		} catch (Exception e) {
			log.log(ExceptionUtils.getStackTrace(e));
			
			//prepare error response obj/message.
			response.setStatusCode(500); // to show internal server error code.
			response.setBody("Weather event not saved successfully.");
		}
		
		log.log("Exit " + this.getClass().getName() + " handleRequest");
		return response;
	}
	
	
	private ApiGatewayResponse getRecentWeatherInfoByCityName(String cityName, String timestamp) throws Exception {
		log.log("Enter " + this.getClass().getName() + " addWeatherEvent");
		log.log("Table name: " + tableName);
		log.log("City name: " + cityName);
		
		
		ApiGatewayResponse response = new ApiGatewayResponse();

        try {
        	HashMap<String,AttributeValue> keyToGet = new HashMap<>();
            keyToGet.put("city-name", AttributeValue.builder()
                    .s(cityName).build());
            
            keyToGet.put("timestamp", AttributeValue.builder()
                    .s(timestamp).build());
            
        	GetItemRequest request = GetItemRequest.builder()
                    .key(keyToGet)
                    .tableName(tableName)
                    .build();
        	
        	
        	
                Map<String,AttributeValue> returnedItem = dynamoDbClient.getItem(request).item();
                String temprature = returnedItem.get("temprature").n();
                
                log.log("Temprature: " + temprature);
                
                WeatherEvent event = new WeatherEvent();
                event.setCityName(cityName);
                event.setTemprature(new BigDecimal(temprature));
                
                response.setBody(JsonUtil.toJsonString(event));
                response.setStatusCode(200);


        } catch (Exception e) {
            System.err.println("GetItem failed.");
            System.err.println(e.getMessage());
        }

		log.log("Exit " + this.getClass().getName() + " addWeatherEvent");
		
		return response;
	}
}