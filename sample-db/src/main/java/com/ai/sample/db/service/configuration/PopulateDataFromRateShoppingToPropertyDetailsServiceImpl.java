package com.ai.sample.db.service.configuration;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.configuration.OnlineTravelAgentDetailsDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.service.RateShoppingPropertyDetailsService;
import com.ai.sample.db.service.ota.OnlineTravelAgentDetailsService;
import com.ai.sample.db.service.property.configuration.PropertyDetailsService;


@ComponentScan({ "com.ai.sample.db" }) 
public class  PopulateDataFromRateShoppingToPropertyDetailsServiceImpl {
	static Logger logger = Logger.getLogger(PopulateDataFromRateShoppingToPropertyDetailsServiceImpl.class);
	
	public static void main(String[] args) {
		PopulateDataFromRateShoppingToPropertyDetailsServiceImpl createExtractByProperty = new PopulateDataFromRateShoppingToPropertyDetailsServiceImpl();
		createExtractByProperty.mapRateShoppingPropertyToClientProperty(args);
	}
	
	public void mapRateShoppingPropertyToClientProperty(String params[]) {
		PopulateDataFromRateShoppingToPropertyDetailsOptionsBuilder optionsBuilder = new PopulateDataFromRateShoppingToPropertyDetailsOptionsBuilder();
		try
		{
			CommandLineParser parser = new GnuParser();
			
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");

			CommandLine line = parser.parse(optionsBuilder.getOptions(), params);
			PopulateDataFromRateShoppingToPropertyDetailsParams rateShoppingPropertyMappingParams = new PopulateDataFromRateShoppingToPropertyDetailsParams(line);
			
			OnlineTravelAgentDetailsService onlineTravelAgentDetailsService  = context.getBean(OnlineTravelAgentDetailsService.class);
			
			PropertyDetailsService propertyDetailsService  = context.getBean(PropertyDetailsService.class);
			RateShoppingPropertyDetailsService rateShoppingPropertyDetailsService = context.getBean(RateShoppingPropertyDetailsService.class);
			
		
			 final ClassLoader cl = this.getClass().getClassLoader();
		        
				File csvFile = new File(cl.getResource("otalist.csv").getFile());

//				TODO read all the entries from rateshopping_property_details
				
//				rateShoppingPropertyDetailsService = new RateShoppingPropertyDetailsServiceImpl();
				
				List<RateShoppingPropertyDTO> rateShoppingPropertyList =  rateShoppingPropertyDetailsService.findAllHotelRateShoppingPropertyDetails();
			
				for(RateShoppingPropertyDTO rateShoppingPropertyDTO : rateShoppingPropertyList)
				{
					CityDTO cityDto = new CityDTO(rateShoppingPropertyDTO.getCity(), rateShoppingPropertyDTO.getState(), rateShoppingPropertyDTO.getCountry());
					PropertyDetailsDTO propertyDetailsDTO =  new PropertyDetailsDTO(rateShoppingPropertyDTO.getName(), rateShoppingPropertyDTO.getHotelChainName(),
							rateShoppingPropertyDTO.getAddress(), cityDto, rateShoppingPropertyDTO.getPinCode(), 
							rateShoppingPropertyDTO.getCapcity(), rateShoppingPropertyDTO.getPropertyStatus(), rateShoppingPropertyDTO.getHotelCurrency(), 
							new Date(), rateShoppingPropertyDTO.getLatitude(), rateShoppingPropertyDTO.getLongitude());
					
					String onlineAgentName = rateShoppingPropertyMappingParams.getOtaName();
					OnlineTravelAgentDetailsDTO otaDto = onlineTravelAgentDetailsService.findAllOnlineTravelAgentDetails(onlineAgentName);
							
					String rateShoppingOtaID = rateShoppingPropertyMappingParams.getRateShoppingOtaID();
					RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO 
					
					= new RateShoppingPropertyDetailsDTO(propertyDetailsDTO, rateShoppingPropertyDTO.getPropertyCode(), otaDto, rateShoppingOtaID, "rateshopping");

	 				propertyDetailsService.saveOrUpdatePropertyDetails(rateShoppingPropertyDetailsDTO);
				}
		}
		catch(ParseException e)
		{
			logger.error("Parsing failed.  Reason: " + e.getMessage());
			new HelpFormatter().printHelp("CreateExtractByProperty",
					optionsBuilder.getOptions(), true);
		} catch (ISellDBException e) {
			logger.error("ISellDBException failed.  Reason: " + e.getMessage());
		}
	}
	
}
