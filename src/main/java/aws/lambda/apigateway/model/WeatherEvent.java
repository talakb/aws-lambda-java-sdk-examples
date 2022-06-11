package aws.lambda.apigateway.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherEvent {

	@JsonProperty(value = "city-name")
	private String cityName;
	private Date timestamp;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private BigDecimal temprature;

}
