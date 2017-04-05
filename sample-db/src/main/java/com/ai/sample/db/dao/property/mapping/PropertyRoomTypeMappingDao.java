package com.ai.sample.db.dao.property.mapping;

import java.util.List;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.mapping.PropertyRoomTypeMappingDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;

public interface PropertyRoomTypeMappingDao {
	void saveOrUpdateRoomType (PropertyRoomTypeMapping roomType) throws ISellDBException;
	PropertyRoomTypeMapping findRoomTypeByNameForProperty(String roomType, PropertyDetails propertyDetails) throws ISellDBException;
	List<PropertyRoomTypeMapping> findRoomTypesMappedToMasterRoomType(String masterRoomType) throws ISellDBException;
	List<PropertyRoomTypeMapping> findAllRoomTypes() throws ISellDBException;
	List<PropertyRoomTypeMapping> findAllPropertyRoomTypes(PropertyDetailsDTO propertyDetailsDto) throws ISellDBException;
	void deletePropertyRoomTypeByName(String roomTypeName, PropertyDetailsDTO propertyDetailsDTO) throws ISellDBException;
	
	void deleteRoomTypesForProperty(PropertyDetails propertyDetails) throws ISellDBException;
	
	void deletePropertyRoomTypeByName(PropertyRoomTypeMappingDTO propertyRoomTypeMappingDTO) throws ISellDBException;
	/*PropertyRoomTypeMapping findRoomTypeByName(String roomTypeName,
			PropertyDetails propertyDetails)throws HibernateException;*/
	List<PropertyRoomTypeMapping> findAllPropertyRoomTypes(
			PropertyDetails propertyDetails) throws ISellDBException;
	
	void updatePropertyRoomTypes(PropertyRoomTypeMapping propertyRoomTypeMapping, PropertyRoomTypeMappingDTO propertyRoomTypeMappingNewValuesDTO) throws ISellDBException;
	PropertyRoomTypeMapping findRoomTypeById(int id) throws ISellDBException;
}