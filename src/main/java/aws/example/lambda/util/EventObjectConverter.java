package aws.example.lambda.util;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class EventObjectConverter {
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private EventObjectConverter() {
		
	}
	
	
	public static String convertEventObjIntoJson(Object event) {
		if(null == event) {
			return "";
		}
		
		if(event instanceof Map) {
			return gson.toJson(event);
		} else if (event instanceof String) {
			return (String) event;
		}
		
		throw new RuntimeException("Unknown event object type.");
	}

}
