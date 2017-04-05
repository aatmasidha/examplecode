package com.ai.sample.db.service.algo;

import java.util.Date;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.exception.ISellProcessingException;

public interface CalculateRecommendedPrice {
	
	public float[] calculateRecommendedPriceForProperty(PropertyDetailsDTO propertyDetailsDto, Date businessDate, int numReportDays) throws ISellProcessingException;
}
