package com.ai.sample.db.dao.property.mapping;

import java.util.List;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyOnlineTravelAgentConnectionMapping;

public interface PropertyOnlineTravelAgentConnectionMappingDao {
	void saveOrUpdatePropertyOtaMapping (PropertyOnlineTravelAgentConnectionMapping propertyOnlineTravelAgentConnectionMapping) throws ISellDBException;
	PropertyOnlineTravelAgentConnectionMapping findPropertyOnlineTravelAgentByPropertyAndOta(PropertyDetailsDTO propertyDetailsDto, String otaName) throws ISellDBException;
	List<PropertyOnlineTravelAgentConnectionMapping> findPropertyOnlineTravelAgentByPropertyAndOta(PropertyDetailsDTO propertyDetailsDto) throws ISellDBException;
	void deleteConnectionForOta(PropertyDetailsDTO propertyDetailsDto, String propertyOtaName) throws ISellDBException;
	void deleteConnectionDetailsForProperty(PropertyDetails propertyDetails) throws ISellDBException;
}