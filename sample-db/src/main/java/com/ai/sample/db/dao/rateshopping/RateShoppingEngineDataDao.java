package com.ai.sample.db.dao.rateshopping;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.rateshopping.RateShoppingEngineData;

public interface RateShoppingEngineDataDao {

	void saveRateShoppingEngineData(RateShoppingEngineData rateShoppingEngineData) throws ISellDBException ;
	RateShoppingEngineData findRateShoppingEngineDataByName(String engineName) throws ISellDBException;
}
