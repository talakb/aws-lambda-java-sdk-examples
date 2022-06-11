package aws.lambda.apigateway.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.fasterxml.jackson.core.JsonProcessingException;

import aws.lambda.apigateway.model.WeatherEvent;
import aws.lambda.apigateway.request.ApiGateWayRequest;
import aws.lambda.apigateway.response.ApiGatewayResponse;
import aws.lambda.test.util.TestContext;
import aws.lambda.util.JsonUtil;

@TestInstance(Lifecycle.PER_CLASS)
public class GetWeatherEventRequestHandlerTest {
	private static final SimpleDateFormat UTC_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS zzz");
	
	// CUT (Class Under Test)
	private GetWeatherEventRequestHandler getWeatherEventRequestHandler;
	private TestContext testContext;

	@BeforeAll
	public void init() {
		getWeatherEventRequestHandler = new GetWeatherEventRequestHandler();
		testContext = new TestContext();
	}
	
	@Test
	public void handleRequestShoulReturnValidResponse() throws JsonProcessingException, ParseException {
		//AAA
		//Arrange (Given)
		WeatherEvent weatherEvent = new WeatherEvent();
		weatherEvent.setCityName("Austin");
		weatherEvent.setTimestamp(UTC_DATE_FORMATTER.parse("2022-06-10T16:09:59.185 CDT"));
		
		
		ApiGateWayRequest request = new ApiGateWayRequest();
		String weatherEventJson = JsonUtil.toJsonString(weatherEvent);
		request.setBody(weatherEventJson);
		
		//Act (When)
		ApiGatewayResponse response = getWeatherEventRequestHandler.handleRequest(request, testContext);
		int expectedResponseCode = response.getStatusCode();
		WeatherEvent responseWeatherEvent = JsonUtil.toObject(response.getBody(), WeatherEvent.class);
		
		//Assert (Then)
		assertNotNull(responseWeatherEvent);
		assertEquals(expectedResponseCode, 200);
		assertEquals(responseWeatherEvent.getCityName(), "Austin");
	}

}
