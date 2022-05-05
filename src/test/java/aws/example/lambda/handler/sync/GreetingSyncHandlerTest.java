package aws.example.lambda.handler.sync;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import aws.example.lambda.util.TestContext;

@TestInstance(Lifecycle.PER_CLASS)
public class GreetingSyncHandlerTest {
	
	//CUT (Class Under Test)
	private GreetingSyncHandler greetingSyncHandler;
	private TestContext testContext;
	
	@BeforeAll
	public void init() {
		greetingSyncHandler = new GreetingSyncHandler();
		testContext = new TestContext();
	}
	
	
	@Test
	public void handleRequestShoulReturnAvalidResponse() {
		//AAA
		//Arrange (Given)
		String event = "Json event/request";
		
		//Act (When)
		String response = greetingSyncHandler.handleRequest(event, testContext);
		String expectedResponse = "Sync call response msg/obj...";
		
		//Assert (Then)
		assertNotNull(response);
		assertEquals(expectedResponse, response);
	}
}
