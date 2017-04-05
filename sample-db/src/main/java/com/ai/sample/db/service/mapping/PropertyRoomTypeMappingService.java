package com.ai.sample.db.service.mapping;

import java.util.List;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.mapping.PropertyRoomTypeMappingDTO;
import com.ai.sample.common.exception.ISellDBException;

public interface PropertyRoomTypeMappingService {

	public void savePropertyRoomTypeMapping(PropertyDetailsDTO propertyDetailsDto, String roomType, String propertyRoomType, int numRooms, boolean isDefault) throws ISellDBException;

	public List<PropertyRoomTypeMappingDTO> findAllRoomTypesForProperty(PropertyDetailsDTO propertyDetailsDto) throws ISellDBException;

	public void savePropertyRoomTypeMapping(
			PropertyDetailsDTO propertyDetailsDto,
			PropertyRoomTypeMappingDTO oldRoomTypeConf,
			PropertyRoomTypeMappingDTO newRoomTypeConf, boolean isEdit) throws ISellDBException;
	
	public void deletePropertyRoomTypeMapping(PropertyDetailsDTO propertyDetailsDto, String propertyRoomTypeName) throws ISellDBException;
}
