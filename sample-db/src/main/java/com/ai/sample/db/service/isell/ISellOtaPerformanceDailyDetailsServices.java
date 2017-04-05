package com.ai.sample.db.service.isell;

import java.util.Date;
import java.util.Map;

import com.ai.sample.common.dto.ota.OtaSoldCountByOTA;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface ISellOtaPerformanceDailyDetailsServices {
	
	void saveOrUpdateISellOtaPerformanceDailyDetailsData(Date businessDate, PropertyDetails propertyDetails) throws ISellProcessingException;
	
	Map<Date, Map<String, OtaSoldCountByOTA>> findOtaSoldCountByOTA(Date businessDate, PropertyDetails propertyDetails, int numReportDays) throws ISellProcessingException;
}
