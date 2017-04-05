package com.ai.sample.common.dto.configuration;

public class CountryDTO {

	String countryName;
	int countryID;
	
	public CountryDTO() {
		super();	
	}

	public CountryDTO(String countryName, int countryID) {
		super();
		this.countryName = countryName;
		this.countryID = countryID;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public int getCountryID() {
		return countryID;
	}

	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}

	@Override
	public String toString() {
		return "CountryDTO [countryName=" + countryName + ", countryID="
				+ countryID + "]";
	}
}
