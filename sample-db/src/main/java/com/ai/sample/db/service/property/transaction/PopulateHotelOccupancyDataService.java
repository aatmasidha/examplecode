package com.ai.sample.db.service.property.transaction;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellProcessingException;


public interface PopulateHotelOccupancyDataService {

	public void readHistoryCSVData(String propertyName, CityDTO cityDto, String fileName ) throws ISellProcessingException;
}
