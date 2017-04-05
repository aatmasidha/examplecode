package com.ai.sample.db.service.configuration;

import org.apache.commons.cli.CommandLine;

public class PopulateDataFromRateShoppingToPropertyDetailsParams {

	private String propertyName;
	private String cityName;
	private String stateName;
	private String countryName;
	private String latitude;
	private String longitude;
	private String otaName;
	private String rateShoppingOtaID;

	public PopulateDataFromRateShoppingToPropertyDetailsParams(CommandLine line) {
		this.propertyName = line.getOptionValue("property");
		this.cityName = line.getOptionValue("city");
		this.stateName = line.getOptionValue("state");
		this.countryName = line.getOptionValue("country");
		this.latitude = line.getOptionValue("latitude");
		this.longitude = line.getOptionValue("longitude");
		this.otaName = line.getOptionValue("otaName");
		this.rateShoppingOtaID = line.getOptionValue("rateShoppingOtaID");
	}

	public PopulateDataFromRateShoppingToPropertyDetailsParams(String propertyName, String cityName, String stateName, 
			String countryName , String latitude, String longitude, String otaName, String rateShoppingOtaID) {
		super();
		this.propertyName = propertyName;
		this.cityName = cityName;
		this.stateName = stateName;
		this.countryName = countryName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.otaName = otaName;	
		this.rateShoppingOtaID = rateShoppingOtaID;
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

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getOtaName() {
		return otaName;
	}

	public String getRateShoppingOtaID() {
		return rateShoppingOtaID;
	}
}
