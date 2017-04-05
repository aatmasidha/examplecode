package com.ai.sample.db.dao.isell;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.model.algo.SeasonDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface SlopePointDetailsDao {

	float findSlopeForPropertyDayOfWeekAndSeason(PropertyDetails propertyDetails, SeasonDetails season, String dow) throws ISellProcessingException;

	public void deleteSlopeDetailsForProperty(PropertyDetails propertyDetails)
			throws ISellDBException;
}
