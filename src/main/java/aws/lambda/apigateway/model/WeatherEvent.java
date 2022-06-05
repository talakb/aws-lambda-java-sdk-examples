package aws.lambda.apigateway.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherEvent {

	private String cityName;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private BigDecimal temprature;
	private Date timestamp;

}
