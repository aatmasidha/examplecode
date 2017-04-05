package com.ai.sample.integration.rateshopping.services;

import org.apache.commons.cli.CommandLine;

public class SinglePropertyRateFetchParams {

	private String propertyName;
	private String cityName;
	private String stateName;
	private String countryName;
	private String numDays;
	private String otaName;
	private String businessDate;
	private String currency;


	public SinglePropertyRateFetchParams(CommandLine line) {
		this.propertyName = line.getOptionValue("property");
		this.cityName = line.getOptionValue("city");
		this.stateName = line.getOptionValue("state");
		this.countryName = line.getOptionValue("country");
		this.numDays = line.getOptionValue("numDays");
		this.otaName = line.getOptionValue("otaName");
		this.businessDate = line.getOptionValue("businessDate");
		this.currency = line.getOptionValue("currency");
	}

	public SinglePropertyRateFetchParams(String propertyName, String cityName, String stateName, String countryName , String numDays, String otaName, String businessDate, String currency) {
		super();
		this.propertyName = propertyName;
		this.cityName = cityName;
		this.stateName = stateName;
		this.countryName = countryName;
		this.numDays = numDays;
		this.otaName = otaName;
		this.businessDate = businessDate;
		this.currency = currency;
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

	public String getNumDays() {
		return numDays;
	}

	public void setNumDays(String numDays) {
		this.numDays = numDays;
	}

	public String getOtaName() {
		return otaName;
	}

	public void setOtaName(String otaName) {
		this.otaName = otaName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
