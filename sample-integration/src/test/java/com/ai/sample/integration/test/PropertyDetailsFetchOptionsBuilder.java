package com.ai.sample.integration.test;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class PropertyDetailsFetchOptionsBuilder {
	// all options
	private Options options = new Options();

	public PropertyDetailsFetchOptionsBuilder() {
		buildOptions();
	}

	private void buildOptions() {

		Option propertyName = new Option("property", "propertyName", true, "propertyName");
		propertyName.setRequired(false);

		Option cityName = new Option("city", "cityName", true, "cityName");
		cityName.setRequired(false);

		Option stateName = new Option("state", "stateName", true, "stateName");
		stateName.setRequired(false);

		Option countryName = new Option("country", "countryName", true, "countryName");
		countryName.setRequired(false);
		
		Option zipCode = new Option("zipCode", "zipCode", true, "zipCode");
		zipCode.setRequired(false);

		Option keyWord = new Option("keyWord", "keyWord", true, "keyWord");
		keyWord.setRequired(false);


		Option businessDate = new Option("businessDate", "businessDate", true, "businessDate");
		keyWord.setRequired(false);
		
		options.addOption(propertyName);
		options.addOption(cityName);
		options.addOption(stateName);
		options.addOption(countryName);
		options.addOption(zipCode);
		options.addOption(keyWord);
		options.addOption(businessDate);
	}

	/** returns the created Options **/
	public Options getOptions() {
		return this.options;
	}
}
