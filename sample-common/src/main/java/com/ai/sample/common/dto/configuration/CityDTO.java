package com.ai.sample.common.dto.configuration;

public class CityDTO {

	String cityName;
	String stateName;
	String countryName;
	
	public CityDTO() {
		super();	
	}
	
	public CityDTO(String cityName, String stateName, String countryName) {
		super();
		this.cityName = cityName;
		this.stateName = stateName;
		this.countryName = countryName;
	}

	public CityDTO(String city) {
		this.cityName = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Override
	public String toString() {
		return "CityDTO [cityName=" + cityName + ", stateName=" + stateName
				+ ", countryName=" + countryName + "]";
	}
}
