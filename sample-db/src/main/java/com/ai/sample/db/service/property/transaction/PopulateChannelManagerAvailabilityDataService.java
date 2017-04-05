package com.ai.sample.db.service.property.transaction;

import java.util.Date;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellProcessingException;


public interface PopulateChannelManagerAvailabilityDataService {

	public void readChannelManagerData(String propertyName, CityDTO cityDto, String fileName, Date businessDate ) throws ISellProcessingException;
}
