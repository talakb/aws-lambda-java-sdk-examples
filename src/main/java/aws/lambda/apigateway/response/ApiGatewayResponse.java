package aws.lambda.apigateway.response;

/**
 * Example to show how to use POJO class to map/parse API Gateway request and response. 
 *
 */
public class ApiGatewayResponse {
	
	private int statusCode;
	private String body;
	
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	

}
