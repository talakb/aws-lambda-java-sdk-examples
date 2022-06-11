package aws.lambda.example.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GreetingEventBuilder {
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public static String getGreetingEvent() throws JsonProcessingException {
		Greeting hello = new Greeting();
		hello.setGreetingText("Hello!");
		hello.setLanguage("EN");

		return gson.toJson(hello);
	}

}
