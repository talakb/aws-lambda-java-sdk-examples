package aws.lambda.apigateway.handler;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import aws.lambda.apigateway.model.WeatherEvent;
import aws.lambda.test.util.TestContext;
import aws.lambda.util.JsonUtil;

@TestInstance(Lifecycle.PER_CLASS)
public class AddWeatherEventRequestStreamHandlerTest {
	// CUT (Class Under Test)
	private AddWeatherEventRequestStreamHandler addWeatherEventRequestStreamHandler;
	private TestContext testContext;

	@BeforeAll
	public void init() {
		addWeatherEventRequestStreamHandler = new AddWeatherEventRequestStreamHandler();
		testContext = new TestContext();
	}
	
	@Test
	public void handleRequestShoulReturnValidResponse() throws IOException {
		//AAA
		//Arrange (Given)
		WeatherEvent weatherEvent = new WeatherEvent();
		weatherEvent.setCityName("Austin");
		weatherEvent.setTimestamp(Calendar.getInstance().getTime());
		weatherEvent.setTemprature(BigDecimal.valueOf(82));
		weatherEvent.setLatitude(BigDecimal.valueOf(30.266666));
		weatherEvent.setLongitude(BigDecimal.valueOf(-97.733330));
		
		String weatherEventJson = JsonUtil.toJsonString(weatherEvent);
		
		
		InputStream inputStream = new ByteArrayInputStream(weatherEventJson.getBytes(StandardCharsets.UTF_8));
		OutputStream ouputStream = null;
		
		//Act (When)
		addWeatherEventRequestStreamHandler.handleRequest(inputStream, ouputStream, testContext);
		
		//Assert (Then)
		assertNotNull(ouputStream);
	}



}
