package aws.lambda.apigateway.auth;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Logic taken from https://github.com/awslabs/aws-apigateway-lambda-authorizer-blueprints
 *
 * AuthPolicy receives a set of allowed and denied methods and generates a valid
 * AWS policy for the API Gateway authorizer. The constructor receives the calling
 * user principal, the AWS account ID of the API owner, and an apiOptions object.
 * The apiOptions can contain an API Gateway RestApi Id, a region for the RestApi, and a
 * stage that calls should be allowed/denied for. For example
 *
 * new AuthPolicy(principalId, PolicyDocument.getDenyAllPolicy(region, awsAccountId, restApiId, stage));
 *
 * @author Jack Kohn
 */
public class AuthPolicy {

    // IAM Policy Constants
    public static final String VERSION = "Version";
    public static final String STATEMENT = "Statement";
    public static final String EFFECT = "Effect";
    public static final String ACTION = "Action";
    public static final String NOT_ACTION = "NotAction";
    public static final String RESOURCE = "Resource";
    public static final String NOT_RESOURCE = "NotResource";
    public static final String CONDITION = "Condition";

    private String principalId;
    private  PolicyDocument policyDocumentObject;
    

    public AuthPolicy(String principalId, PolicyDocument policyDocumentObject) {
        this.principalId = principalId;
        this.policyDocumentObject = policyDocumentObject;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    /**
     * IAM Policies use capitalized field names, but Lambda by default will serialize object members using camel case
     *
     * This method implements a custom serializer to return the IAM Policy as a well-formed JSON document, with the correct field names
     *
     * @return IAM Policy as a well-formed JSON document
     */
	public Map<String, Object> getPolicyDocument() {
		Map<String, Object> serializablePolicy = new HashMap<>();
		serializablePolicy.put(VERSION, policyDocumentObject.Version);
		Statement[] statements = policyDocumentObject.getStatement();
		Map<String, Object>[] serializableStatementArray = new Map[statements.length];

		for (int i = 0; i < statements.length; i++) {
			Map<String, Object> serializableStatement = new HashMap<>();
			Statement statement = statements[i];
			serializableStatement.put(EFFECT, statement.Effect);
			serializableStatement.put(ACTION, statement.Action);
			serializableStatement.put(RESOURCE, statement.getResource());
			serializableStatement.put(CONDITION, statement.getCondition());
			serializableStatementArray[i] = serializableStatement;
		}

		serializablePolicy.put(STATEMENT, serializableStatementArray);
		return serializablePolicy;
	}

    public void setPolicyDocument(PolicyDocument policyDocumentObject) {
        this.policyDocumentObject = policyDocumentObject;
    }
}