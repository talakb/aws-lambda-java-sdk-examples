package aws.example.lambda.event;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Greeting implements Serializable{
	private static final long serialVersionUID = -1654346868636037103L;

	private String greetingText;
	private String language;

}
