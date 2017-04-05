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
import com.ai.sample.db.service.property.transaction.PopulateChannelManagerAvailabilityDataService;
import com.ai.sample.db.service.property.transaction.TransactionalDetailsService;

public class PopulateAvailabilityByProperty {
	Logger logger =  Logger.getLogger(PopulateAvailabilityByProperty.class);
	
	@Autowired
	PopulateChannelManagerAvailabilityDataService populateChannelManagerAvailabilityDataService;


	public  void populateOfflineDailyChannelManagerData(String basePath, PropertyDetailsDTO propertyDetailsDto, Date businessDate ) throws ISellProcessingException {

		try
		{
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");
			
			populateChannelManagerAvailabilityDataService = context.getBean(PopulateChannelManagerAvailabilityDataService.class);
			FilenameFilter textFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					String lowercaseName = name.toLowerCase();
					if (lowercaseName.contains("availability")) {
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
				for (File fileObj : files) {
						populateChannelManagerAvailabilityDataService.readChannelManagerData(propertyDetailsDto.getName(), propertyDetailsDto.getCityDto(), fileObj.getAbsolutePath(), businessDate);
				}
			}
			else
			{
				logger.error("Channel Manager Availability Data not found for property: " + propertyDetailsDto.getName() + " For Date: " + businessDate );
				throw new ISellProcessingException(1, "Channel Manager Availability Data not found for property: " + propertyDetailsDto.getName() + " For Date: " + businessDate );
			}
		}
		 catch (ISellProcessingException e) {
				logger.error("Exception in PopulateAvailabilityByPorperty::populateOfflineDailyChannelManagerData" + e.getMessage() );
				throw  e;
			}
	}
}
