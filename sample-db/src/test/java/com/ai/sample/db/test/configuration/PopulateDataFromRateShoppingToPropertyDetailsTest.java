package com.ai.sample.db.test.configuration;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.configuration.OnlineTravelAgentDetailsDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.service.RateShoppingPropertyDetailsService;
import com.ai.sample.db.service.ota.OnlineTravelAgentDetailsService;
import com.ai.sample.db.service.property.configuration.PropertyDetailsService;
import com.ai.sample.db.test.BaseTestConfiguration;


@ContextConfiguration(classes={RateShoppingPropertyDetailsService.class, PropertyDetailsService.class, OnlineTravelAgentDetailsService.class})
public class PopulateDataFromRateShoppingToPropertyDetailsTest  extends BaseTestConfiguration {

	static Logger logger = Logger.getLogger(PopulateDataFromRateShoppingToPropertyDetailsTest.class);
	
	@Autowired
	RateShoppingPropertyDetailsService rateShoppingPropertyDetailsService;
	
	@Autowired
	PropertyDetailsService propertyDetailsService;

	@Autowired
	OnlineTravelAgentDetailsService onlineTravelAgentDetailsService;
//	@Rollback(false)
//	@Test
//	public void testPropertyDetailsFromRateShoppingData() throws Exception{
//	
	public static void main(String args[])
	{
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("config.xml");

		try
		{
			PopulateDataFromRateShoppingToPropertyDetailsTest populateTest = context.getBean(PopulateDataFromRateShoppingToPropertyDetailsTest.class);
			 populateTest.start(args);
		}
		catch(Exception e)
		{
			logger.error("Exception is" + e.getMessage());
		}
		 finally
			{
				((ClassPathXmlApplicationContext) context).close();
			}
	}
	
	

//	@Test
	 private void start(String[] args) {
		PopulateDataFromRateShoppingToPropertyDetailsOptionsBuilder optionsBuilder = new PopulateDataFromRateShoppingToPropertyDetailsOptionsBuilder();


		try
		{
			CommandLineParser parser = new GnuParser();

			CommandLine line = parser.parse(optionsBuilder.getOptions(), args);

			PopulateDataFromRateShoppingToPropertyDetailsParams params = new PopulateDataFromRateShoppingToPropertyDetailsParams(line);

	        // returns the ClassLoader object associated with this Class.
			 final ClassLoader cl = this.getClass().getClassLoader();
		        
			File csvFile = new File(cl.getResource("otalist.csv").getFile());

//			TODO read all the entries from rateshopping_property_details
			
//			rateShoppingPropertyDetailsService = new RateShoppingPropertyDetailsServiceImpl();
			
			List<RateShoppingPropertyDTO> rateShoppingPropertyList =  rateShoppingPropertyDetailsService.findAllHotelRateShoppingPropertyDetails();
		
			for(RateShoppingPropertyDTO rateShoppingPropertyDTO : rateShoppingPropertyList)
			{
				CityDTO cityDto = new CityDTO(rateShoppingPropertyDTO.getCity(), rateShoppingPropertyDTO.getState(), rateShoppingPropertyDTO.getCountry());
				PropertyDetailsDTO propertyDetailsDTO =  new PropertyDetailsDTO(rateShoppingPropertyDTO.getName(), rateShoppingPropertyDTO.getHotelChainName(),
						rateShoppingPropertyDTO.getAddress(), cityDto, rateShoppingPropertyDTO.getPinCode(), 
						rateShoppingPropertyDTO.getCapcity(), rateShoppingPropertyDTO.getPropertyStatus(), rateShoppingPropertyDTO.getHotelCurrency(), 
						new Date(), rateShoppingPropertyDTO.getLatitude(), rateShoppingPropertyDTO.getLongitude());
				
				String onlineAgentName = params.getOtaName();
				OnlineTravelAgentDetailsDTO otaDto = onlineTravelAgentDetailsService.findAllOnlineTravelAgentDetails(onlineAgentName);
						
				String rateShoppingOtaID = params.getRateShoppingOtaID();
				RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO 
				
				= new RateShoppingPropertyDetailsDTO(propertyDetailsDTO, rateShoppingPropertyDTO.getPropertyCode(), otaDto, rateShoppingOtaID, "rateshopping");

 				propertyDetailsService.saveOrUpdatePropertyDetails(rateShoppingPropertyDetailsDTO);
			}
		}
		catch (ParseException e) {
			logger.error("Parsing failed.  Reason: " + e.getMessage());
			new HelpFormatter().printHelp("CreateExtractByProperty",
					optionsBuilder.getOptions(), true);
		} catch (ISellDBException e) {
			logger.error("ISellDBException  Reason: " + e.getMessage());
		} 
		catch(Exception e)
		{
			logger.error("Exception reason:" + e.getMessage());
		}
		
	}
	
}
