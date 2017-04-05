package com.ai.sample.db.service.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.configuration.CityDao;
import com.ai.sample.db.dao.configuration.CountryDao;
import com.ai.sample.db.dao.configuration.StateDao;
import com.ai.sample.db.model.configuration.City;
import com.ai.sample.db.model.configuration.State;

@Service("cityService")
@Transactional
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDao cityDao;

	@Autowired
	private StateDao stateDao;

	@Autowired
	private CountryDao countryDao;

	@Override
	public void saveCity(CityDTO cityDto) throws ISellDBException {
		City city = cityDao.findOrCreateCityByStateAndName(
				cityDto.getCityName(), cityDto.getStateName(),
				cityDto.getCountryName());

		cityDao.saveCity(city);
	}

	@Override
	public List<CityDTO> findAllCities() throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CityDTO> findCitiesByState(String stateName)
			throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CityDTO findCitiesByName(String cityName, String StateName)
			throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCityByName(String cityName, String stateName)
			throws ISellDBException {
		// TODO Auto-generated method stub
	}

	@Override
	public List<CityDTO> findCitiesByCountry(String countryName)
			throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<CityDTO>> findCitiesByStateForCountry(
			String countryName) throws ISellDBException {

		Map<String, List<CityDTO>> stateCityDtoMap = new HashMap<String, List<CityDTO>>();
		List<State> stateList = stateDao.findStatesForCountry(countryName);
		for (State state : stateList) {
			List<CityDTO> cityDtoList = new ArrayList<CityDTO>();
			List<City> cityList = cityDao.findCitiesForState(state);
			for (City city : cityList) {

				String stateName = "";
				State stateObj = city.getState();
				if (stateObj != null) {
					stateName = stateObj.getName();
				}
				CityDTO cityDto = new CityDTO(city.getName(), stateName, city
						.getState().getCountry().getName());
				
				cityDtoList.add(cityDto);
			}
			
			stateCityDtoMap.put(state.getName(), cityDtoList);
		}
		return stateCityDtoMap;
	}
}
