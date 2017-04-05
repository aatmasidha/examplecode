package com.ai.sample.integration.rateshopping.services;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class SinglePropertyRateFetchOptionsBuilder {
	// all options
	private Options options = new Options();

	public SinglePropertyRateFetchOptionsBuilder() {
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
		
		Option numDays = new Option("numDays", "numDays", true, "numDays");
		numDays.setRequired(false);

		Option otaName = new Option("otaName", "otaName", true, "otaName");
		otaName.setRequired(true);
		
		Option businessDate = new Option("businessDate", "businessDate", true, "businessDate");
		businessDate.setRequired(false);

		Option currency = new Option("currency", "currency", true, "currency");
		otaName.setRequired(false);
		
		options.addOption(propertyName);
		options.addOption(cityName);
		options.addOption(stateName);
		options.addOption(countryName);
		options.addOption(numDays);
		options.addOption(otaName);
		options.addOption(businessDate);
		options.addOption(currency);
	}

	/** returns the created Options **/
	public Options getOptions() {
		return this.options;
	}
}
