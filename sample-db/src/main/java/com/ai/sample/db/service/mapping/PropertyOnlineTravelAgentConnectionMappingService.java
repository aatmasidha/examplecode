package com.ai.sample.db.service.mapping;

import java.util.List;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.PropertyOtaConnectionMappingDTO;
import com.ai.sample.common.dto.ui.PropertyOnlineTravelAgentMappingDTO;
import com.ai.sample.common.exception.ISellDBException;

public interface PropertyOnlineTravelAgentConnectionMappingService {

	public void saveOnlineTravelAgent(PropertyDetailsDTO propertyDetailsDto, String otaName, String propertyOtaName) throws ISellDBException;

	public List<PropertyOtaConnectionMappingDTO> findAllOtsForProperty(PropertyDetailsDTO propertyDetailsDto) throws ISellDBException;

	//This is called from UI with a flag called edit
	public void saveOnlineTravelAgent(
			PropertyDetailsDTO propertyDetailsDto,
			PropertyOnlineTravelAgentMappingDTO propertyOTANameOld,
			PropertyOnlineTravelAgentMappingDTO propertyOTANameNew,
			boolean edit)  throws ISellDBException;
	
	public void deleteOnlineTravelAgent(PropertyDetailsDTO propertyDetailsDto, PropertyOnlineTravelAgentMappingDTO propertyOnlineTravelAgentMappingDTO) throws ISellDBException;
}
