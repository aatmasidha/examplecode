package com.ai.sample.db.ui.services;

import java.util.List;

import com.ai.sample.common.dto.ui.CountryDetailsDTO;
import com.ai.sample.common.dto.ui.CountryStateCityDTO;
import com.ai.sample.common.exception.ISellDBException;



public interface CountryDataServices {
	
	public CountryStateCityDTO getListOfAllCitiesStatesForAllCountries() throws ISellDBException;
	public List<CountryDetailsDTO> getListOfAllCountries()throws ISellDBException;
}
