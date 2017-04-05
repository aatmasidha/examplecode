package com.ai.sample.db.dao.rateshopping;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.rateshopping.RateShoppingRateCodeType;

public interface RateShoppingRateCodeTypeDao {

	RateShoppingRateCodeType findRateCodeForType(String rateCode, String rateShoppingEngine) throws ISellDBException;
}
