package com.ai.sample.db.service.extract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.CommandLine;

public class CreateExtractParams {

	private String propertyName;
	private String cityName;
	private String stateName;
	private String countryName;
	private Date businessDate;
	private boolean history = false;

	public CreateExtractParams(CommandLine line) {
		this.propertyName = line.getOptionValue("property");
		this.cityName = line.getOptionValue("city");
		this.stateName = line.getOptionValue("state");
		this.countryName = line.getOptionValue("country");
		String date = line.getOptionValue("date");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if( date == null || date.length() <= 0)
		{
			date = formatter.format(new Date());
		}
		
		try {
			businessDate = formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 String historyParam = line.getOptionValue("history");
		 this.history = Boolean.parseBoolean(historyParam);
	}

	public CreateExtractParams(String propertyName, String cityName, String stateName, String countryName , Date date, boolean history) {
		super();
		this.propertyName = propertyName;
		this.cityName = cityName;
		this.stateName = stateName;
		this.countryName = countryName;
		this.businessDate = date;
		this.history = history;
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

	public Date getBusinessDate() {
		return businessDate;
	}

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
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

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

}
