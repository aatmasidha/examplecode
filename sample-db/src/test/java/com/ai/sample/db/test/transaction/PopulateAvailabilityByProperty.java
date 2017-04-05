package com.ai.sample.db.test.transaction;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.service.property.transaction.PopulateChannelManagerAvailabilityDataService;
import com.ai.sample.db.test.BaseTestConfiguration;

public class PopulateAvailabilityByProperty  extends BaseTestConfiguration{
	Logger logger =  Logger.getLogger(PopulateAvailabilityByProperty.class);
	
	@Autowired
	PopulateChannelManagerAvailabilityDataService populateChannelManagerAvailabilityDataService;
	
	@Test
	@Rollback(false)
	public void populateOccupancyData()
	{
		String propertyName = "Hotel Aurora Towers";
		CityDTO cityDto = new CityDTO("Pune", "Maharashtra", "India");
		String filePath = "D:/Documents/isell/ISellIntrimFiles/CM_Availability.xlsx";
		try {

			populateChannelManagerAvailabilityDataService.readChannelManagerData(propertyName, cityDto, filePath, new Date());	
		} catch (ISellProcessingException e) {
			logger.error("Exception in PopulateAvailabilityByProperty::populateOccupancyData" + e.getMessage() );
		}
	}
}
