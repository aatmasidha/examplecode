package com.ai.sample.db.service.configuration;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class PopulateDataFromRateShoppingToPropertyDetailsOptionsBuilder {
	// all options
	private Options options = new Options();

	public PopulateDataFromRateShoppingToPropertyDetailsOptionsBuilder() {
		buildOptions();
	}

	private void buildOptions() {

		Option propertyName = new Option("property", "propertyName", true, "propertyName");
		propertyName.setRequired(true);

		Option cityName = new Option("city", "cityName", true, "cityName");
		cityName.setRequired(true);

		Option stateName = new Option("state", "stateName", true, "stateName");
		stateName.setRequired(true);

		Option countryName = new Option("country", "countryName", true, "countryName");
		countryName.setRequired(true);
		
		Option latitude = new Option("latitude", "latitude", true, "latitude");
		latitude.setRequired(false);

		Option longitude = new Option("longitude", "longitude", true, "longitude");
		longitude.setRequired(false);

		Option otaName = new Option("ota", "otaName", true, "otaName");
		cityName.setRequired(true);
		
		Option rateShoppingOtaID = new Option("rateShoppingOtaID", "rateShoppingOtaID", true, "rateShoppingOtaID");
		cityName.setRequired(true);
	
		options.addOption(propertyName);
		options.addOption(cityName);
		options.addOption(stateName);
		options.addOption(countryName);
		options.addOption(latitude);
		options.addOption(longitude);
		options.addOption(otaName);
		options.addOption(rateShoppingOtaID);
	}

	/** returns the created Options **/
	public Options getOptions() {
		return this.options;
	}
}
