package com.ai.sample.integration.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;
import com.ai.sample.db.service.property.configuration.PropertyDetailsService;
import com.ai.sample.db.service.rateshopping.RateShoppingPropertyDetailsMappingService;

public class PropertyDetailsFetchFromRateShoppingTest {

	static Logger logger = Logger.getLogger(PropertyDetailsFetchFromRateShoppingTest.class);
	public static void main(String[] args) {
		logger.info("Executing class PropertyDetailsFetchTest");
		ApplicationContext context = 
	            new ClassPathXmlApplicationContext("config.xml");
		try
		{
			CityDTO cityDTO = new CityDTO("Pune", "Maharashtra", "India");
			PropertyDetailsDTO propertyDetailsDTO = new PropertyDetailsDTO("Aurora Tower", cityDTO);
			RateShoppingPropertyDetailsMappingService rateShoppingPropertymappingService = context.getBean(RateShoppingPropertyDetailsMappingService.class);
			Map<String, RateShoppingPropertyDetailsDTO> rateShoppingPropertyMap = rateShoppingPropertymappingService.getAllRateShoppingDetailsPerProperty(propertyDetailsDTO);
			List<RateShoppingPropertyDetailsDTO> rateShoppingPropertyList = new ArrayList<RateShoppingPropertyDetailsDTO>(rateShoppingPropertyMap.values());
			for( RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO : rateShoppingPropertyList)
			{
				PropertyDetailsService propertyDetailsService = context.getBean(PropertyDetailsService.class);
				
//				propertyDetailsService.
			}
		}
		catch(Exception e)
		{
			logger.error("Exception while fetching the property details is: " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			((ClassPathXmlApplicationContext) context).close();
		}
	}

}
