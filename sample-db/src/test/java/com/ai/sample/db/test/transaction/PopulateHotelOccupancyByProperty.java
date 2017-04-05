package com.ai.sample.db.test.transaction;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.service.property.transaction.PopulateHotelOccupancyDataService;
import com.ai.sample.db.test.BaseTestConfiguration;

public class PopulateHotelOccupancyByProperty  extends BaseTestConfiguration{

	
	Logger logger =  Logger.getLogger(PopulateHotelOccupancyByProperty.class);
	
	@Autowired
	PopulateHotelOccupancyDataService populateHotelOccupancyDataService;
	
	@Test
	@Rollback(false)
	public void populateOccupancyData()
	{
		String propertyName = "Hotel Aurora Towers";
		CityDTO cityDto = new CityDTO("Pune", "Maharashtra", "India");
		String filePath = "D:/Documents/isell/ISellIntrimFiles/HnF1.xlsx";
		try {
			populateHotelOccupancyDataService.readHistoryCSVData(propertyName, cityDto, filePath);	
		} catch (ISellProcessingException e) {
			logger.error("Exception in PopulateHotelOccupancyByProperty::populateOccupancyData" + e.getMessage() );
		}
		
	}
	
}
