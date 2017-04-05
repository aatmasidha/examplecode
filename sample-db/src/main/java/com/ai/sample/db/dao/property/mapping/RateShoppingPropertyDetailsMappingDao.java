package com.ai.sample.db.dao.property.mapping;

import java.util.List;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.RateShoppingPropertyDetailsMapping;

public interface RateShoppingPropertyDetailsMappingDao {
	public void saveOrUpdateRateShoppingPropertyDetails(RateShoppingPropertyDetailsMapping rateShoppingPropertyDetailsMapping) throws ISellDBException;
	public RateShoppingPropertyDetailsMapping findRateShoppingPropertyDetails(String rateShoppingPropertyUID, String otaName) throws ISellDBException;
	public void updatePropertyDetailsForRateShopping(RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO, PropertyDetailsDTO propDetails) throws ISellDBException;
	public List<RateShoppingPropertyDetailsMapping> findAllRateShoppingPropertyDetails(PropertyDetails propertyDetails)  throws ISellDBException;
	public void updatePropertyDetailsForRateShopping(
				PropertyDetails propDetails,
				RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO)   throws ISellDBException;
	public RateShoppingPropertyDetailsMapping findRateShoppingPropertyDetailsByPropertyIDAndRateShoppingOtaID(
			PropertyDetails propertyDetails, String rateShoppingOtaID)  throws ISellDBException;
	void deleteRateShoppingPropertyDetailsByProperty(PropertyDetails propertyDetails) throws ISellDBException;
}
