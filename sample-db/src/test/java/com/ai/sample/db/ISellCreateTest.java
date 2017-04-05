package com.ai.sample.db;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.db.service.isell.ISellGenerationServices;
import com.ai.sample.db.test.BaseTestConfiguration;

public class ISellCreateTest  extends BaseTestConfiguration {

	static final Logger logger = Logger.getLogger(ISellCreateTest.class);
	
	@Autowired
	ISellGenerationServices iSellGenerationServices;
	
	@Test
	@Rollback(false)
	public void testCSVExtract() throws Exception{
		CityDTO cityDto = new CityDTO("Pune", "Maharashtra", "India");
		PropertyDetailsDTO propertyDetailsDto = new PropertyDetailsDTO();
		propertyDetailsDto.setName("Hotel Aurora Towers");
		propertyDetailsDto.setCityDto(cityDto);
		iSellGenerationServices.generateISellData(new Date(), propertyDetailsDto, 90);
		iSellGenerationServices.writeISellData(new Date(), propertyDetailsDto, "D:/Development/temp", 90);
	}
}
