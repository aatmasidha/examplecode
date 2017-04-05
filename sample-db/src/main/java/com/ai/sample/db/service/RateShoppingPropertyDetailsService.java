package com.ai.sample.db.service;

import java.util.List;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;

public interface RateShoppingPropertyDetailsService {

	void saveOrUpdateRateShoppingRateShoppingPropertyDetails(RateShoppingPropertyDTO propertyDetailsDTO) throws ISellDBException ;
	List<RateShoppingPropertyDTO> findAllHotelRateShoppingPropertyDetails() throws ISellDBException;
	List<RateShoppingPropertyDTO> findRateShoppingPropertyDetailsByPropertyName(String propertyName) throws ISellDBException;
	List<RateShoppingPropertyDTO> findRateShoppingPropertyDetailsByPropertyCode(String propertyCode) throws ISellDBException;
	
	List<RateShoppingPropertyDTO> findRateShoppingUnmappedProperties() throws ISellDBException;
}
