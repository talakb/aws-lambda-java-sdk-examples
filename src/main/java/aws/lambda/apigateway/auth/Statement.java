package aws.lambda.apigateway.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Logic taken from https://github.com/awslabs/aws-apigateway-lambda-authorizer-blueprints
 *
 */
public class Statement {

	String Effect;
	String Action;
	Map<String, Map<String, Object>> Condition;

	private List<String> resourceList;

	public Statement() {

	}

	public Statement(String effect, String action, List<String> resourceList,
			Map<String, Map<String, Object>> condition) {
		this.Effect = effect;
		this.Action = action;
		this.resourceList = resourceList;
		this.Condition = condition;
	}

	public static Statement getEmptyInvokeStatement(String effect) {
		return new Statement(effect, "execute-api:Invoke", new ArrayList<>(), new HashMap<>());
	}

	public String getEffect() {
		return Effect;
	}

	public void setEffect(String effect) {
		this.Effect = effect;
	}

	public String getAction() {
		return Action;
	}

	public void setAction(String action) {
		this.Action = action;
	}

	public String[] getResource() {
		return resourceList.toArray(new String[resourceList.size()]);
	}

	public void addResource(String resource) {
		resourceList.add(resource);
	}

	public Map<String, Map<String, Object>> getCondition() {
		return Condition;
	}

	public void addCondition(String operator, String key, Object value) {
		Condition.put(operator, Collections.singletonMap(key, value));
	}

}
