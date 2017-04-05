package com.ai.sample.db.service.extract;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.service.property.transaction.PopulateHotelOccupancyDataService;

public class PopulateHotelOccupancyByProperty {

	Logger logger =  Logger.getLogger(PopulateHotelOccupancyByProperty.class);
	
	@Autowired
	PopulateHotelOccupancyDataService populateHotelOccupancyDataService;
	
	public void populateOfflineDailyOccupancyData(String basePath, PropertyDetailsDTO propertyDetailsDto, Date businessDate) throws ISellProcessingException
	{
		try {
			FilenameFilter textFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					String lowercaseName = name.toLowerCase();
					if (lowercaseName.contains("occupancy")) {
						return true;
					} else {
						return false;
					}
				}
			};
			
			File baseFilePath = new File(basePath);
			File[] files = baseFilePath.listFiles(textFilter);
			if( files != null && files.length > 0 )
			{
				Arrays.sort(files);
				ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
				populateHotelOccupancyDataService = context.getBean(PopulateHotelOccupancyDataService.class);
				
				for (File fileObj : files) {
					populateHotelOccupancyDataService.readHistoryCSVData(propertyDetailsDto.getName(), propertyDetailsDto.getCityDto(), fileObj.getAbsolutePath());
				}
			}
			else
			{
//				TODO for some properties occupancy data may be missing so we can to keep it optional
				logger.warn("Occupancy Data not found for property: " + propertyDetailsDto.getName() + " For Date: " + businessDate );
//				throw new ISellProcessingException(1, "Occupancy Data not found for property: " + propertyDetailsDto.getName() + " For Date: " + businessDate );
			}
		} catch (ISellProcessingException e) {
			logger.error("Exception in PopulateHotelOccupancyByProperty::populateOfflineDailyOccupancyData" + e.getMessage() );
			throw e;
		}
		
	}
}
