package aws.lambda.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import aws.lambda.example.handler.sync.GreetingSyncHandler;
import aws.lambda.test.util.TestContext;

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
