package com.ai.sample.db.service.isell;

import java.util.Date;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.exception.ISellProcessingException;

public interface ISellGenerationServices {

	public void generateISellData(Date businessDate, PropertyDetailsDTO propertyDetailsDto, int reportNumDays) throws ISellProcessingException;
	public void writeISellData(Date businessDate, PropertyDetailsDTO propertyDetailsDto, String basePath, int reportNumDays) throws ISellProcessingException;
	
}
