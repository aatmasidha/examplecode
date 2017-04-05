package com.ai.sample.db.service.isell;

import java.util.Date;
import java.util.List;

import com.ai.sample.common.dto.rateshopping.RateShoppingISellDataDTO;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface ISellRateShoppoingDailyDetailsServices {
	
	List<RateShoppingISellDataDTO> getRateShoppingDailyDetailsForProperty(Date businessDate, PropertyDetails propertyDetails, int numReportDays) throws ISellProcessingException;
}
