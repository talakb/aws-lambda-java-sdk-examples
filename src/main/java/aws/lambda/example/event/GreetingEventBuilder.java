package aws.lambda.example.event;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GreetingEventBuilder {
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public static String getGreetingEvent() {
		Greeting hello = Greeting.builder()
		.greetingText("Hello!")
		.language("EN")
		.build();

		return gson.toJson(hello);
	}

}
