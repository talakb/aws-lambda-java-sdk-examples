package aws.lambda.example.event;

public class Greeting {

	private String greetingText;
	private String language;
	
	
	public String getGreetingText() {
		return greetingText;
	}
	public void setGreetingText(String greetingText) {
		this.greetingText = greetingText;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

}
