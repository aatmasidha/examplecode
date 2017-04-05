package com.ai.sample.dataservices.controller;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.sample.common.dto.ui.CountryDetailsDTO;
import com.ai.sample.common.dto.ui.CountryStateCityDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.dataservices.domain.Message;
import com.ai.sample.db.ui.services.CountryDataServices;

@RestController
public class DataServicesRestController {

	@RequestMapping("/")
	public String welcome() {// Welcome page, non-rest
		return "Welcome to RestTemplate Example.";
	}

	@RequestMapping("/hello/{player}")
	public Message message(@PathVariable String player) {// REST Endpoint.

		Message msg = new Message(player, "Hello " + player);
		return msg;
	}

	@RequestMapping("/getcountry/{country}")
	public CountryDetailsDTO getCountryData(@PathVariable String country) {// REST
		// Endpoint.

		int id = 1234;
		CountryDetailsDTO msg = new CountryDetailsDTO(country, id);
		return msg;
	}

	@RequestMapping("/getallcountries")
	public List<CountryDetailsDTO> getAllCountryList() {// REST Endpoint.

		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");
			CountryDataServices countryDataServices = context
					.getBean(CountryDataServices.class);
			List<CountryDetailsDTO> countryDetailsDtoList = countryDataServices
					.getListOfAllCountries();
			((AbstractApplicationContext) context).close();
			return countryDetailsDtoList;
		} catch (ISellDBException e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("/getallcountrieswithcities")
	public CountryStateCityDTO getAllCountriesAndCitiesList() {// REST Endpoint.

		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");

			CountryDataServices countryDataServices = context
					.getBean(CountryDataServices.class);
			CountryStateCityDTO countryStateCityDTOList = countryDataServices
					.getListOfAllCitiesStatesForAllCountries();

			((AbstractApplicationContext) context).close();

			return countryStateCityDTOList;
		} catch (ISellDBException e) {
			e.printStackTrace();
			return null;
		}
	}

}
