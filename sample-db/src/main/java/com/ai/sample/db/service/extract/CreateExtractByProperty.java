package com.ai.sample.db.service.extract;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.service.isell.ISellGenerationServices;
import com.ai.sample.db.util.DBConfigurationProperties;

public class CreateExtractByProperty {
	final static Logger logger = Logger
			.getLogger(CreateExtractByProperty.class);

	// String basePath = "D://development//temp";
	// ISellGenerationServicesImpl iSellGenerationServices = new
	// ISellGenerationServicesImpl();

	public static void main(String[] args) {
		CreateExtractByProperty createExtractByProperty = new CreateExtractByProperty();

		Properties extractProperties = DBConfigurationProperties
				.getProperties();
		String extractBasePath = extractProperties.getProperty(
				"extracttemppath", "D://development//temp");
		createExtractByProperty.callRunAnalysis(args, extractBasePath);
		logger.info("Extract Creation Completed...");
		System.exit(0);
	}

	private void callRunAnalysis(String[] args, String basePath) {
		CreateExtractOptionsBuilder optionsBuilder = new CreateExtractOptionsBuilder();

		CommandLineParser parser = new GnuParser();
		try {
			CommandLine line = parser.parse(optionsBuilder.getOptions(), args);
			CreateExtractParams params = new CreateExtractParams(line);

			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");

			// PropertyName, City, State, Country, Extract date should be passed
			// as command line
			// arguments
			String propertyName = params.getPropertyName();
			Date businessDate = params.getBusinessDate();
			if (businessDate == null) {
				businessDate = new Date();
			}

			// TODO get command lines arguments
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			// Using DateFormat format method we can create a string
			// representation of a date with the defined format.
			String reportDate = df.format(businessDate);

			boolean isHistory = params.isHistory();

			CityDTO cityDto = new CityDTO(params.getCityName(),
					params.getStateName(), params.getCountryName());
			PropertyDetailsDTO propertyDetailsDto = new PropertyDetailsDTO();
			propertyDetailsDto.setCityDto(cityDto);
			propertyDetailsDto.setName(propertyName);

			String propertyPath = basePath + File.separator
					+ propertyDetailsDto.getName() + File.separator
					+ reportDate;

			PopulateDataForExtractsByProperty populateDataForExtractsByProperty = new PopulateDataForExtractsByProperty();
			populateDataForExtractsByProperty.populateOfflineDailyExtractData(
					propertyPath + File.separator + "input",
					propertyDetailsDto, businessDate);

			if (!isHistory) {			
				PopulateHotelOccupancyByProperty populateHotelOccupancyByProperty = new PopulateHotelOccupancyByProperty();
				populateHotelOccupancyByProperty
						.populateOfflineDailyOccupancyData(propertyPath
								+ File.separator + "input", propertyDetailsDto,
								businessDate);

				PopulateAvailabilityByProperty populateAvailabilityByPorperty = new PopulateAvailabilityByProperty();
				populateAvailabilityByPorperty
						.populateOfflineDailyChannelManagerData(propertyPath
								+ File.separator + "input", propertyDetailsDto,
								businessDate);
			}
			
			ISellGenerationServices iSellGenerationServices = context
					.getBean(ISellGenerationServices.class);

			iSellGenerationServices.generateISellData(businessDate,
					propertyDetailsDto, 90);
			iSellGenerationServices.writeISellData(businessDate,
					propertyDetailsDto, propertyPath + File.separator
							+ "output", 90);
			
			((AbstractApplicationContext)context).close();
			
		} catch (ISellProcessingException e) {
			logger.error("Error in CreateExtractByProperty::callRunAnalysis is: "
					+ e.getMessage());
			logger.error("Error in CreateExtractByProperty::callRunAnalysis");
		} catch (ParseException e) {
			logger.error("Parsing failed.  Reason: " + e.getMessage());
			new HelpFormatter().printHelp("CreateExtractByProperty",
					optionsBuilder.getOptions(), true);
		}

	}

}
