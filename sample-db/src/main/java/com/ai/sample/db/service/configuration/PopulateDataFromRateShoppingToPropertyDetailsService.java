package com.ai.sample.db.service.configuration;

import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;

public interface PopulateDataFromRateShoppingToPropertyDetailsService {
	public void mapRateShoppingPropertyToClientProperty(RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO);

	void mapRateShoppingPropertyToClientProperty(String[] args);
}
