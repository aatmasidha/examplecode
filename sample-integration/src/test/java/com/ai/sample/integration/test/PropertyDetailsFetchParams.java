package com.ai.sample.integration.test;

import org.apache.commons.cli.CommandLine;

public class PropertyDetailsFetchParams {

	private String propertyName;
	private String cityName;
	private String stateName;
	private String countryName;
	private String zipCode;
	private String keyWord;

	public PropertyDetailsFetchParams(CommandLine line) {
		this.propertyName = line.getOptionValue("property");
		this.cityName = line.getOptionValue("city");
		this.stateName = line.getOptionValue("state");
		this.countryName = line.getOptionValue("country");
		this.zipCode = line.getOptionValue("zipCode");
		this.keyWord = line.getOptionValue("keyword");
	}

	public PropertyDetailsFetchParams(String propertyName, String cityName, String stateName, String countryName , String zipCode, String keyWord) {
		super();
		this.propertyName = propertyName;
		this.cityName = cityName;
		this.stateName = stateName;
		this.countryName = countryName;
		this.zipCode = zipCode;
		this.keyWord = keyWord;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getCityName() {
		return cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getKeyWord() {
		return keyWord;
	}
}
