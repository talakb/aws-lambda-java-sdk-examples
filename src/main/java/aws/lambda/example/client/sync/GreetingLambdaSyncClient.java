package aws.lambda.example.client.sync;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.ServiceException;

import aws.lambda.example.event.GreetingEventBuilder;

public class GreetingLambdaSyncClient {
	private static final Logger logger = LoggerFactory.getLogger(GreetingLambdaSyncClient.class);
	
	private static String awsPropFilePath; 
	private static String lambdaFunctionName; 
	
	/**
	 * Expected args: 
	 * - AWS connection property file location/path
	 * - Lambda function name to be executed
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		readInputArgs(args);

		String lambdaArnPrefix = "arn:aws:lambda";
		String functionName = "function:" + lambdaFunctionName;

		Pair<String, String> accountIdAndRegion = getRegionAndAccountIdFromPropFile(awsPropFilePath);
		String accountId = accountIdAndRegion.getLeft();
		String region = accountIdAndRegion.getRight();
		
		String functionArn = String.join(":", lambdaArnPrefix, region, accountId, functionName);
		
		logger.info("Function ARN: {}", functionArn);
		
		String greetingEvent = GreetingEventBuilder.getGreetingEvent();

		InvokeRequest request = new InvokeRequest()
									.withFunctionName(functionArn)
									.withPayload(greetingEvent)
									.withInvocationType(InvocationType.RequestResponse); // Make synchronous request (i.e client will wait for response)
		
		InvokeResult response = null;

		try {
			// Create Lambda client by passing default credential and region where function is deployed.
			AWSLambda awsLambdaClient = AWSLambdaClientBuilder.standard()
														.withCredentials(new ProfileCredentialsProvider())
													    .withRegion(region)
													    .build();

			response = awsLambdaClient.invoke(request);

		} catch (ServiceException e) {
			logger.error("ServiceException: \n" + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Response code: {}", response.getStatusCode().toString());
		logger.info("Response payload: {}", new String(response.getPayload().array(), StandardCharsets.UTF_8));
	}


	private static void readInputArgs(String[] args) throws Exception {
		awsPropFilePath = args[0];
		lambdaFunctionName = args[1];
		
		if(StringUtils.isEmpty(awsPropFilePath)) {
			throw new Exception("Location of AWS connection property file is required.");
		}
		
		if(StringUtils.isEmpty(lambdaFunctionName)) {
			throw new Exception("Lambda function name is required.");
		}
	}

	
	/**
	 * Read AWS account ID and region from a prop. file. 
	 * @return
	 * @throws IOException 
	 */
	private static Pair<String, String> getRegionAndAccountIdFromPropFile(final String awsPropFileName) throws IOException {
		String acctId = "";
		String region = "";
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(awsPropFileName)) {
			
			prop.load(input);
			acctId = prop.getProperty("ACCT_ID");
			region = prop.getProperty("REGION");

		} catch (IOException ex) {
			logger.error("Could not load prop file. \n" + ExceptionUtils.getStackTrace(ex));
			throw ex;
		}

		return Pair.of(acctId, region);
	}
}
