package com.ai.sample.db.service.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.configuration.CountryDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.configuration.CountryDao;
import com.ai.sample.db.model.configuration.Country;

@Service("countryService")
@Transactional
public class CountryServiceImpl implements CountryService{

	
	@Autowired
	private CountryDao countryDao;

	@Override
	public List<CountryDTO> findAllCountries() throws ISellDBException{
		List<Country>  countryList = countryDao.findAllCountries();
		List<CountryDTO> countryDTOList = new ArrayList<CountryDTO>();
		for(Country country : countryList)
		{
			CountryDTO countryDto  = new CountryDTO(country.getName(), country.getId());
			countryDTOList.add(countryDto);
		}
		return countryDTOList;
	}

	
	

}
