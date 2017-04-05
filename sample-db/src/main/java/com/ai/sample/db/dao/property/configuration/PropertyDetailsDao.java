package com.ai.sample.db.dao.property.configuration;

import java.util.List;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface PropertyDetailsDao {

	void savePropertyDetails(PropertyDetails propertyDetails) throws ISellDBException;
	
	List<PropertyDetails> findAllPropertyDetails() throws ISellDBException;
	
	List<PropertyDetails> findPropertyDetailsByName(String name) throws ISellDBException;
	PropertyDetails findPropertyDetails(PropertyDetailsDTO propertyDetailsDto) throws ISellDBException;
	void deletePropertyDetailsByName(String name) throws ISellDBException;
	
	void deletePropertyDetails(PropertyDetails propertyDetails) throws ISellDBException;

	PropertyDetails findPropertyDetailsByUID(String propertyUID) throws ISellDBException;
	
	List<PropertyDetails> findPropertyDetailsByCity(CityDTO cityDto) throws ISellDBException;

	void flush() throws ISellDBException;

	void clear() throws ISellDBException;
}
