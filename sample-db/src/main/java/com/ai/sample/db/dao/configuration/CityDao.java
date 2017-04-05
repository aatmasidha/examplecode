package com.ai.sample.db.dao.configuration;

import java.util.List;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.configuration.City;
import com.ai.sample.db.model.configuration.State;

public interface CityDao {

	void saveCity(City city) throws ISellDBException;
	
	List<City> findAllCities() throws ISellDBException;
	
	City findCityByName(CityDTO cityDto) throws ISellDBException;
	
	void deleteCityByName(String city) throws ISellDBException;
	
	City findCityByStateAndName(String cityName, String stateName, String countryName) throws ISellDBException;
	
	City findOrCreateCityByStateAndName(String cityName, String stateName, String countryName) throws ISellDBException;

	List<City> findCitiesForState(State stateObj) throws ISellDBException;
}
