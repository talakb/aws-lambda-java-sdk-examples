package aws.example.lambda.api.gateway.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherEvent implements Serializable {
	private static final long serialVersionUID = 1698705592621193652L;

	private String locationName;
	private BigDecimal temprature;
	private Date timestamp;
	private BigDecimal longitude;
	private BigDecimal latitude;
	
	

}
