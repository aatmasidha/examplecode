package com.ai.sample.integration.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.sample.integration.rateshopping.DTO.PropertyInformationRequestDetailsDTO;
import com.ai.sample.integration.rateshopping.dataapi.test.PropertyDetailsThread;

public class PropertyDetailsFetchTest {

	static Logger logger = Logger.getLogger(PropertyDetailsFetchTest.class);
	public static void main(String[] args) {

		logger.info("Executing class PropertyDetailsFetchTest");
		PropertyDetailsFetchOptionsBuilder optionsBuilder = new PropertyDetailsFetchOptionsBuilder();
		
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("config.xml");
		try
		{
			CommandLineParser parser = new GnuParser();

			CommandLine line = parser.parse(optionsBuilder.getOptions(), args);

			PropertyDetailsFetchParams params = new PropertyDetailsFetchParams(line);

			SimpleDateFormat checkinDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");

			String checkInDateString = checkinDateFormat.format(new Date());
			String processDate = checkInDateString;

			PropertyInformationRequestDetailsDTO propertyDetailsRequestDTO = new PropertyInformationRequestDetailsDTO();
			propertyDetailsRequestDTO.setCountry(params.getCountryName());
			propertyDetailsRequestDTO.setCity(params.getCityName());
			propertyDetailsRequestDTO.setHotelName(params.getPropertyName());
			propertyDetailsRequestDTO.setZip(params.getZipCode());
			propertyDetailsRequestDTO.setKeyword(params.getKeyWord());
			/*PropertyDetailsThread propertyDetailsThread = new PropertyDetailsThread(
					propertyDetailsRequestDTO, processDate, context);

			logger.info("Calling API to fetch property details with criteria: " + propertyDetailsRequestDTO.toString());
			propertyDetailsThread.start();*/

			PropertyDetailsThread propertyDetailsThread = new PropertyDetailsThread(
					propertyDetailsRequestDTO, processDate, context);

			logger.info("Calling API to fetch property details with criteria: " + propertyDetailsRequestDTO.toString());
			propertyDetailsThread.start();
			while( Thread.activeCount() > 4)
			{
				Thread.sleep(500);
			}
			((ClassPathXmlApplicationContext) context).close();
			logger.info("Exiting class PropertyDetailsFetchTest");
		}
		catch( InterruptedException e)
		{
			logger.error("InterruptedException while  fetching data for property is: " + e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			logger.error("Parsing failed.  Reason: " + e.getMessage());
			new HelpFormatter().printHelp("CreateExtractByProperty",
					optionsBuilder.getOptions(), true);
		}
		finally
		{
			((ClassPathXmlApplicationContext) context).close();
		}
	}

}
