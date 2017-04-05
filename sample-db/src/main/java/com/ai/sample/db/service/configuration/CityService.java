package com.ai.sample.db.service.configuration;

import java.util.List;
import java.util.Map;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellDBException;

public interface CityService {

	public void saveCity(CityDTO city) throws ISellDBException;
	
	public List<CityDTO> findAllCities() throws ISellDBException;
	
	public List<CityDTO> findCitiesByState(String stateName) throws ISellDBException;
	
	public List<CityDTO> findCitiesByCountry(String countryName) throws ISellDBException;
	
	public CityDTO findCitiesByName(String cityName, String StateName) throws ISellDBException;
	
	public void deleteCityByName(String cityName, String stateName) throws ISellDBException;

	public Map<String, List<CityDTO>> findCitiesByStateForCountry(
			String countryName) throws ISellDBException;
}
