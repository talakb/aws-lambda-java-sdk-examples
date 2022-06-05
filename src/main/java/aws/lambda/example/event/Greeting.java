package aws.lambda.example.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Greeting {

	private String greetingText;
	private String language;

}
