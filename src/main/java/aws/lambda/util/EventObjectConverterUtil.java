package aws.lambda.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class EventObjectConverterUtil {
	// Jackson JSON string to object and vice-versa coverter.
	private static final ObjectMapper objectMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	private EventObjectConverterUtil() {

	}

	public static String toJsonString(Object event) throws JsonProcessingException {
		if (null == event) {
			return "";
		}

		return objectMapper.writeValueAsString(event);
	}

	public static <T> T toObject(String jsonInput, Class<T> toType)
			throws JsonMappingException, JsonProcessingException {
		if (null == jsonInput) {
			return null;
		}

		return objectMapper.readValue(jsonInput, toType);
	}
}
