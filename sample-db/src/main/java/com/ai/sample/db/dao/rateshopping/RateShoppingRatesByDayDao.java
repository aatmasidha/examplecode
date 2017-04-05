package com.ai.sample.db.dao.rateshopping;

import java.util.Date;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;
import com.ai.sample.db.model.property.mapping.RateShoppingPropertyDetailsMapping;
import com.ai.sample.db.model.rateshopping.RateShoppingRatesByDay;

public interface RateShoppingRatesByDayDao {

	void saveRateShoppingRatesByDay(RateShoppingRatesByDay rateData) throws ISellDBException;
	void findMinimumRateForPropertyForDay(PropertyDetails propertyDetails, Date date) throws ISellDBException;

	RateShoppingRatesByDay findRateShoppingRateByCheckinDayRoomTypeDiscount(
			RateShoppingPropertyDetailsMapping rateShoppingPropertyDetails,
			Date checkinDate, String rateType, double discount) throws ISellDBException;
	void deleteRatesByPropertyRoomTypeMapping(
			PropertyRoomTypeMapping propertyRoomTypeMapping) throws ISellDBException;
	
	void deleteRatesForProperty(
			PropertyDetails propertyDetails) throws ISellDBException;
}
