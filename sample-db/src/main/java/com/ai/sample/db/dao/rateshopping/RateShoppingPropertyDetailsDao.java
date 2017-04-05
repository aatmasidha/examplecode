package com.ai.sample.db.dao.rateshopping;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.rateshopping.RateShoppingPropertyDetails;

public interface RateShoppingPropertyDetailsDao {
	void saveRateShoppingPropertyDetails(RateShoppingPropertyDetails propertyDetails) throws ISellDBException;
	List<RateShoppingPropertyDetails> findAllRateShoppingProperties() throws ISellDBException;
	RateShoppingPropertyDetails findRateShoppingPropertyDetailsPropertyCode(String propertyCode) throws ISellDBException;
	RateShoppingPropertyDetails findRateShoppingPropertyDetails(String name, String city) throws ISellDBException;
	void deleteRateShoppingPropertyDetailsByPropertyCode(String propertyCode) throws ISellDBException;
	public List<RateShoppingPropertyDetails> findListOfUnmappedRateShoppingProperties() throws ISellDBException;
}

