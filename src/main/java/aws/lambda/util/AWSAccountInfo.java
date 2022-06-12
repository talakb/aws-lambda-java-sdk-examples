package aws.lambda.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AWSAccountInfo {
	private String accountId;
	private String region;

}
