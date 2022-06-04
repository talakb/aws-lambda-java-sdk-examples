package aws.example.lambda.api.gateway.response;

import java.io.Serializable;

public class ApiGatewayResponse implements Serializable {
	private static final long serialVersionUID = 559184516063446932L;
	
	private int responseCode;
	private String responseBody;
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

}
