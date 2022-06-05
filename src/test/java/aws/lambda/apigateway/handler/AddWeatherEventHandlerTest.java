package aws.lambda.apigateway.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import aws.lambda.apigateway.model.WeatherEvent;
import aws.lambda.apigateway.response.ApiGatewayResponse;
import aws.lambda.test.util.TestContext;

@TestInstance(Lifecycle.PER_CLASS)
public class AddWeatherEventHandlerTest {

	// CUT (Class Under Test)
	private AddWeatherEventHandler addWeatherEventHandler;
	private TestContext testContext;

	@BeforeAll
	public void init() {
		addWeatherEventHandler = new AddWeatherEventHandler();
		testContext = new TestContext();
	}
	
	@Test
	public void handleRequestShoulReturnAvalidResponse() {
		//AAA
		//Arrange (Given)
		WeatherEvent weatherEvent = new WeatherEvent();
		weatherEvent.setCityName("Austin");
		weatherEvent.setTimestamp(Calendar.getInstance().getTime());
		weatherEvent.setTemprature(BigDecimal.valueOf(82));
		weatherEvent.setLatitude(BigDecimal.valueOf(30.266666));
		weatherEvent.setLongitude(BigDecimal.valueOf(-97.733330));
		
		//Act (When)
		ApiGatewayResponse response = addWeatherEventHandler.handleRequest(weatherEvent, testContext);
		int expectedResponseCode = response.getStatusCode();
		
		//Assert (Then)
		assertNotNull(response);
		assertEquals(expectedResponseCode, 200);
	}

}
