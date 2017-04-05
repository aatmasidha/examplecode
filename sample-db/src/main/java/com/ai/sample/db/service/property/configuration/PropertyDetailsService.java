package com.ai.sample.db.service.property.configuration;

import java.util.List;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface PropertyDetailsService {

	PropertyDetails saveOrUpdatePropertyDetails(PropertyDetailsDTO propertyDetailsDTO) throws ISellDBException ;
	
	void saveOrUpdatePropertyDetails(RateShoppingPropertyDetailsDTO propertyDetailsDTO) throws ISellDBException ;
	
	List<PropertyDetailsDTO> findAllHotelPropertyDetails() throws ISellDBException;
	List<PropertyDetailsDTO> findPropertyDetailsByPropertyName(String propertyName) throws ISellDBException;
	
	PropertyDetailsDTO findPropertyDetailsByPropertyInformation(PropertyDetailsDTO propertyDetailsDto) throws ISellDBException;

	void saveOrUpdatePropertyDetails(PropertyDetailsDTO propertyDetailsDto,
			PropertyDetailsDTO oldPropertyDetails,
			PropertyDetailsDTO newPropertyDetails, boolean isEdit) throws ISellDBException;
	
	void deletePropertyDetails(PropertyDetailsDTO propertyDetailsDto) throws ISellDBException;
}
