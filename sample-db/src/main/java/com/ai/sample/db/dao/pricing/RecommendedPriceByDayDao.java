package com.ai.sample.db.dao.pricing;

import java.util.Date;
import java.util.List;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.pricing.RecommendedPriceByDayDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.pricing.RecommendedPriceByDay;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface RecommendedPriceByDayDao {

	void saveRecommendedPriceByDay(RecommendedPriceByDay recommendedPriceByDay) throws ISellDBException;
	
	List<RecommendedPriceByDay> findRecommendedPriceByDay() throws ISellDBException;
	
	RecommendedPriceByDay findRecommendedPriceForPropertyFromDate(PropertyDetailsDTO propertyDetailsDto, Date businessDate) throws ISellDBException;
	
	List<RecommendedPriceByDayDTO> findRecommendedPriceForProperty(PropertyDetails propertyDetails, Date businessDate,
			int numReportDays) throws ISellDBException;
	
	void deleteRecommendedPriceByDay(PropertyDetailsDTO propertyDetailsDto) throws ISellDBException;
	
	void deleteRecommendedPriceByDayForProperty(PropertyDetails propertyDetails) throws ISellDBException;
}
