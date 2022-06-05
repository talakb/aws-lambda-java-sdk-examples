package aws.lambda.apigateway.request;

import java.util.Map;

/**
 * Example to show how to use POJO class to map/parse API Gateway request and response. 
 *
 */
public class ApiGateWayRequest {
	
	 private String body;
	 private Map<String, String> queryStringParameters;
	 
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Map<String, String> getQueryStringParameters() {
		return queryStringParameters;
	}
	public void setQueryStringParameters(Map<String, String> queryStringParameters) {
		this.queryStringParameters = queryStringParameters;
	}

}
