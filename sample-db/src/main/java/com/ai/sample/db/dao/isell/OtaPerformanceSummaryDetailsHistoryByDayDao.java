package com.ai.sample.db.dao.isell;

import java.util.Date;
import java.util.List;

import com.ai.sample.common.dto.ota.OtaRevenueSummaryDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.isell.OtaPerformanceSummaryDetailsHistoryByDay;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface OtaPerformanceSummaryDetailsHistoryByDayDao {

	void saveOtaPerformanceSummaryDetailsHistoryByDay(OtaPerformanceSummaryDetailsHistoryByDay otaPerformanceSummaryHistoryDetailsByDay) throws ISellDBException;

	List<OtaRevenueSummaryDetailsDTO> findOtaPerformanceHistoryForISell(PropertyDetails propertyDetails,
			Date businessDate, int reportNumDays) throws ISellDBException;
	
	OtaPerformanceSummaryDetailsHistoryByDay findOtaPerformanceHistoryForISellForADate(PropertyDetails propertyDetails, Date businessDate) throws ISellDBException;
}
