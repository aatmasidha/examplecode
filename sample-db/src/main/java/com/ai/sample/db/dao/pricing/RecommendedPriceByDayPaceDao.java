package com.ai.sample.db.dao.pricing;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface RecommendedPriceByDayPaceDao {
	void deleteRecommendedPriceByDayPaceForProperty(PropertyDetails propertyDetails) throws ISellDBException;
}
