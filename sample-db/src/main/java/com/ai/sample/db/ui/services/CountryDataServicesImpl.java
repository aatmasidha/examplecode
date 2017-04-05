package com.ai.sample.db.ui.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.configuration.CountryDTO;
import com.ai.sample.common.dto.ui.CountryDetailsDTO;
import com.ai.sample.common.dto.ui.CountryStateCityDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.service.configuration.CityService;
import com.ai.sample.db.service.configuration.CountryService;

@Service("countryDataServices")
@Transactional
public class CountryDataServicesImpl implements CountryDataServices{
	
	@Autowired
	CountryService countryService;
	
	@Autowired
	CityService cityService;
	
	public CountryStateCityDTO getListOfAllCitiesStatesForAllCountries() throws ISellDBException
	{
//		CountryStateCityDTO countryStateCityDTO = new CountryStateCityDTO();
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//				"config.xml");
//		CountryService countryService = context.getBean(CountryService.class);
		List<CountryDTO>  countryDetailsDTOList = countryService.findAllCountries();
		CountryStateCityDTO countryStateCityDTO = new CountryStateCityDTO();
		Map<String, Map<String, CityDTO>>  countryStateCityDTOMap = countryStateCityDTO.getCountryStateCityMap();
		for(CountryDTO countryDTO : countryDetailsDTOList)
		{
			Map  cityStateMap = new HashMap<String, CityDTO>();
			
			String countryName = countryDTO.getCountryName();
			Map<String, List<CityDTO>> StateCityList =  cityService.findCitiesByStateForCountry(countryName);
			for (Map.Entry<String,List<CityDTO>> entry : StateCityList.entrySet()) {
				  String StateName = entry.getKey();
				  List<CityDTO> cityList = entry.getValue();
				  cityStateMap.put(StateName, cityList);
				  countryStateCityDTOMap.put(countryName, cityStateMap);
				}

			
		}
		
		return countryStateCityDTO;
	}

	@Override
	public List<CountryDetailsDTO> getListOfAllCountries() throws ISellDBException{
		// TODO Auto-generated method stub
		return null;
	}
}
