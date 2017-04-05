package com.ai.sample.db.service.extract;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CreateExtractOptionsBuilder {
	// all options
	private Options options = new Options();

	public CreateExtractOptionsBuilder() {
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
		
		Option date = new Option("date", "businessDate", true, "businessDate");
		date.setRequired(false);
		
		Option history = new Option("history", "history", true, "history");
		history.setRequired(false);

		options.addOption(propertyName);
		options.addOption(cityName);
		options.addOption(stateName);
		options.addOption(countryName);
		options.addOption(date);
		options.addOption(history);
	}

	/** returns the created Options **/
	public Options getOptions() {
		return this.options;
	}
}
