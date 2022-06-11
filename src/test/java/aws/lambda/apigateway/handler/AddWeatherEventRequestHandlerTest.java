package aws.lambda.apigateway.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
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
public class AddWeatherEventRequestHandlerTest {

	// CUT (Class Under Test)
	private AddWeatherEventRequestHandler addWeatherEventHandler;
	private TestContext testContext;

	@BeforeAll
	public void init() {
		addWeatherEventHandler = new AddWeatherEventRequestHandler();
		testContext = new TestContext();
	}
	
	@Test
	public void handleRequestShoulReturnValidResponse() throws JsonProcessingException {
		//AAA
		//Arrange (Given)
		WeatherEvent weatherEvent = new WeatherEvent();
		weatherEvent.setCityName("Austin");
		weatherEvent.setTimestamp(Calendar.getInstance().getTime());
		weatherEvent.setTemprature(BigDecimal.valueOf(82));
		weatherEvent.setLatitude(BigDecimal.valueOf(30.266666));
		weatherEvent.setLongitude(BigDecimal.valueOf(-97.733330));
		
		ApiGateWayRequest request = new ApiGateWayRequest();
		String weatherEventJson = JsonUtil.toJsonString(weatherEvent);
		request.setBody(weatherEventJson);
		
		//Act (When)
		ApiGatewayResponse response = addWeatherEventHandler.handleRequest(request, testContext);
		int expectedResponseCode = response.getStatusCode();
		
		//Assert (Then)
		assertNotNull(response);
		assertEquals(expectedResponseCode, 200);
	}

}
