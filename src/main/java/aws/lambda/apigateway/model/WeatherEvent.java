package aws.lambda.apigateway.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class WeatherEvent {

	@JsonProperty(value = "city-name")
	private String cityName;
	private Date timestamp;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private BigDecimal temprature;
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public BigDecimal getTemprature() {
		return temprature;
	}
	public void setTemprature(BigDecimal temprature) {
		this.temprature = temprature;
	}

}
