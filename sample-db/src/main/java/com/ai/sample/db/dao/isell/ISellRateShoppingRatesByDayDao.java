package com.ai.sample.db.dao.isell;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.ai.sample.common.dto.rateshopping.ISellRateShoppingRatesByDayDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.isell.RateShoppingRatesSummaryByDay;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface ISellRateShoppingRatesByDayDao {

	RateShoppingRatesSummaryByDay findRateShoppingRateForPropertyAndDay(PropertyDetails propertyDetails, Date date)  throws ISellDBException;
	RateShoppingRatesSummaryByDay saveOrUpdateRateShoppingRateForPropertyAndDay(RateShoppingRatesSummaryByDay iSellRateShoppingRatesByDay) throws ISellDBException;
	
	Map<Date, ArrayList<ISellRateShoppingRatesByDayDTO>> findRateShoppingRateForPropertyForConfigurableDays(PropertyDetails propertyDetails,
			Date businessDate, int reportNumDays) throws ISellDBException;
}
