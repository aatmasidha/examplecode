package com.ai.sample.common.dto.ui;

import java.util.HashMap;
import java.util.Map;

import com.ai.sample.common.dto.configuration.CityDTO;

public class CountryStateCityDTO {
	
	Map<String, Map<String, CityDTO>>  countryStateCityMap = new HashMap<String, Map<String, CityDTO>>();

	
	public Map<String, Map<String, CityDTO>> getCountryStateCityMap() {
		return countryStateCityMap;
	}

	public void setCountryStateCityMap(
			Map<String, Map<String, CityDTO>> countryStateCityMap) {
		this.countryStateCityMap = countryStateCityMap;
	}

	@Override
	public String toString() {
		return "CountryStateCityDTO [countryStateCityMap="
				+ countryStateCityMap + "]";
	}

	
}
