package com.ai.sample.db.service;

import java.util.List;

import com.ai.sample.common.dto.rateshopping.RateShoppingRatesByDayDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.rateshopping.RateShoppingRatesByDay;

public interface RateShoppingRatesByDayService {

	void saveRateShoppingRatesByDay(RateShoppingRatesByDayDTO rateShoppingRatesByDay) throws ISellDBException ;
	
	RateShoppingRatesByDayDTO findRateShoppingRateByCheckinDayRoomTypeDiscount(RateShoppingRatesByDayDTO rateShoppingRatesByDayDTO) throws ISellDBException;
	
	List<RateShoppingRatesByDay> findRateShoppingRateByDay(RateShoppingRatesByDayDTO rateShoppingRatesByDay) throws ISellDBException;
}
