package com.ai.sample.db.dao.rateshopping;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface RateShoppingRatesSummaryByDayDao {
	
	void deleteRatesSummaryForProperty(
			PropertyDetails propertyDetails) throws ISellDBException;
}
