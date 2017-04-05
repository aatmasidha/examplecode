package com.ai.sample.db.dao.pricing;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface RecommendedPriceAllByDayDao {
	void deleteRecommendedPriceAllByDayForProperty(PropertyDetails propertyDetails) throws ISellDBException;
}
