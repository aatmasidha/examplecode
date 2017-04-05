package com.ai.sample.db.dao.isell;

import java.util.Date;
import java.util.List;

import com.ai.sample.common.dto.ota.OtaRevenueSummaryDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.isell.OtaPerformanceSummaryDetailsByDay;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface OtaPerformanceSummaryDetailsByDayDao {

	void saveOtaPerformanceSummaryDetailsByDay(OtaPerformanceSummaryDetailsByDay otaPerformanceSummaryDetailsByDay) throws ISellDBException;

	List<OtaRevenueSummaryDetailsDTO> findOtaPerformanceForISell(PropertyDetails propertyDetails,
			Date businessDate, int reportNumDays) throws ISellDBException;
	
	OtaPerformanceSummaryDetailsByDay findOtaPerformanceForISellForADate(PropertyDetails propertyDetails, Date businessDate) throws ISellDBException;
	
	public void deleteOTAPerformanceForProperty(PropertyDetails propertyDetails)
			throws ISellDBException;
}
