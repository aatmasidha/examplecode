package com.ai.sample.db.dao.configuration;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.configuration.Country;

public interface CountryDao {

	void saveCountry(Country country) throws ISellDBException ;
	
	List<Country> findAllCountries() throws ISellDBException;
	
	Country findCountryByName(String countryName) throws ISellDBException;
	
	void deleteCountryByName(String countryName) throws ISellDBException;

	Country getOrCreateCountryByName(String countryName) throws ISellDBException;
}
