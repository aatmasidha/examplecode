package com.ai.sample.integration.rateshopping.services;

import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.service.rateshopping.RateShoppingPropertyDetailsMappingService;

public class FetchRateForAllOTA {
	static Logger logger = Logger.getLogger(FetchRateForAllOTA.class);
	Properties properties = new Properties();

	public static void main(String[] args) {

		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");
			SinglePropertyRateFetchOptionsBuilder optionsBuilder = new SinglePropertyRateFetchOptionsBuilder();

			CommandLineParser parser = new GnuParser();
			CommandLine line = parser.parse(optionsBuilder.getOptions(), args);
			SinglePropertyRateFetchParams params = new SinglePropertyRateFetchParams(line);

//			Properties properties = new Properties();
//			properties.load(FetchRateForAllOTA.class.getClassLoader()
//					.getResourceAsStream("otalist.properties"));
			SinglePropertyRateFetch restFetchTest = new SinglePropertyRateFetch();
			
			PropertyDetailsDTO propertyDetailsDTO = new PropertyDetailsDTO();
			CityDTO cityDto = new CityDTO(params.getCityName(),
					params.getStateName(), params.getCountryName());

			propertyDetailsDTO.setCityDto(cityDto);
			propertyDetailsDTO.setName(params.getPropertyName());
			if( params.getCurrency() != null || params.getCurrency().length() > 0)
			{
				propertyDetailsDTO.setHotelCurrency(params.getCurrency());
			}
			
/*			PropertyOnlineTravelAgentConnectionMappingService propertyOTAMappingService  = context.getBean(PropertyOnlineTravelAgentConnectionMappingService.class);
			
			List<PropertyOtaConnectionMappingDTO> propertyOtaMappingList = propertyOTAMappingService.findAllOtsForProperty(propertyDetailsDTO);
*/			
			RateShoppingPropertyDetailsMappingService rateShoppingPropertyDetailsMappingService = context.getBean(RateShoppingPropertyDetailsMappingService.class);
			List<RateShoppingPropertyDetailsDTO> rateShoppingOTAList =  rateShoppingPropertyDetailsMappingService.findRateShoppingPropertyMappingForProperty( propertyDetailsDTO);
		
			for(RateShoppingPropertyDetailsDTO propertyOtaDto : rateShoppingOTAList)
			{
			/*	String propertyOtaID = properties.getProperty(propertyOtaDto.getPropertyOtaName());
				restFetchTest.fetchAllRates(propertyDetailsDTO,
					params.getNumDays(), params.getBusinessDate(), propertyOtaID, params.getCurrency());*/
				
				restFetchTest.fetchAllRates(propertyDetailsDTO,
					params.getNumDays(), params.getBusinessDate(), propertyOtaDto.getRateShoppingOtaID(), params.getCurrency());
			}
		} catch (org.apache.commons.cli.ParseException /*| IOException*/ | ISellDBException  e) {
			logger.error("ParseException in FetchRateForAllOTA " + e.getMessage());
		}

	}
}
