package com.ai.sample.db.service.mapping;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.constants.EnumConstants.ExecutionStatusCodeConstant;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.MasterRoomTypeDTO;
import com.ai.sample.common.dto.mapping.PropertyRoomTypeMappingDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.configuration.RoomTypeMasterDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.mapping.PropertyRoomTypeMappingDao;
import com.ai.sample.db.model.configuration.RoomTypeMaster;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;

@Service("propertyRoomTypeMappingServiceImpl")
@Transactional
public class PropertyRoomTypeMappingServiceImpl implements PropertyRoomTypeMappingService{

	Logger logger = Logger.getLogger(PropertyRoomTypeMappingServiceImpl.class);

	@Autowired
	PropertyRoomTypeMappingDao  propertyRoomTypeMappingDao;
	
	@Autowired 
	PropertyDetailsDao propertyDetailsDao;
	
	@Autowired
	RoomTypeMasterDao roomTypeMasterDao;
	
	@Override
	public void savePropertyRoomTypeMapping(
			PropertyDetailsDTO propertyDetailsDto, String roomType,
			String propertyRoomType, int numRooms, boolean isDefault) throws ISellDBException {
		PropertyRoomTypeMapping propertyRoomTypeMapping = new PropertyRoomTypeMapping();
		
		PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);
		if( propertyDetails == null)
		{
			logger.error("PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Property data to map to roomtype missing: " + propertyDetailsDto);
			throw new ISellDBException(ExecutionStatusCodeConstant.DATAERROR.getCode(), "PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Property data to map to roomtype missing: " + propertyDetailsDto);
		}
		RoomTypeMaster roomTypeMaster = roomTypeMasterDao.findRoomTypeByName(roomType);
		if( roomTypeMaster == null)
		{
			logger.error("PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Master Roomtype to map to property is missing: " + roomTypeMaster);
			throw new ISellDBException(ExecutionStatusCodeConstant.DATAERROR.getCode(), "PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Master Roomtype to map to property is missing: " + roomTypeMaster);
		}
		propertyRoomTypeMapping.setPropertyDetails(propertyDetails);
		propertyRoomTypeMapping.setName(propertyRoomType);
		propertyRoomTypeMapping.setRoomTypeMaster(roomTypeMaster);
		propertyRoomTypeMapping.setNumRooms(numRooms);
		propertyRoomTypeMapping.setDefault(isDefault);
		propertyRoomTypeMappingDao.saveOrUpdateRoomType(propertyRoomTypeMapping);
	}

	@Override
	public List<PropertyRoomTypeMappingDTO> findAllRoomTypesForProperty(
			PropertyDetailsDTO propertyDetailsDto)  throws ISellDBException{
			List<PropertyRoomTypeMappingDTO> propertyRoomTypeMappingDtoList = new ArrayList<PropertyRoomTypeMappingDTO>();
			List<PropertyRoomTypeMapping> propertyRoomTypeMappingList = propertyRoomTypeMappingDao.findAllPropertyRoomTypes(propertyDetailsDto);
			for(PropertyRoomTypeMapping propertyRoomTypeMapping : propertyRoomTypeMappingList)
			{
				MasterRoomTypeDTO masterRoomTypeDTO = new MasterRoomTypeDTO(propertyRoomTypeMapping.getRoomTypeMaster().getId(), propertyRoomTypeMapping.getRoomTypeMaster().getName());
				PropertyRoomTypeMappingDTO propertyRoomTypeDto = new PropertyRoomTypeMappingDTO(masterRoomTypeDTO, propertyRoomTypeMapping.getName(), propertyRoomTypeMapping.getId(), propertyRoomTypeMapping.isDefault(),propertyRoomTypeMapping.getNumRooms());
				propertyRoomTypeMappingDtoList.add(propertyRoomTypeDto);
			}
		 
		return propertyRoomTypeMappingDtoList;
	}

	@Override
	public void savePropertyRoomTypeMapping(
			PropertyDetailsDTO propertyDetailsDto,
			PropertyRoomTypeMappingDTO oldRoomTypeConf,
			PropertyRoomTypeMappingDTO newRoomTypeConf, boolean isEdit) throws ISellDBException {
		if(isEdit)
		{
			if( oldRoomTypeConf == null || newRoomTypeConf == null)
			{
				logger.error("PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Missing data to save room type mapping: " + propertyDetailsDto);
				throw new ISellDBException(ExecutionStatusCodeConstant.DATAERROR.getCode(), "PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Missing data to save room type mapping: " + propertyDetailsDto);
			}
			PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);
			if( propertyDetails == null)
			{
				logger.error("PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Property data to map to roomtype missing: " + propertyDetailsDto);
				throw new ISellDBException(ExecutionStatusCodeConstant.DATAERROR.getCode(), "PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Property data to map to roomtype missing: " + propertyDetailsDto);
			}
			
			String propertyRoomTypeOld = oldRoomTypeConf.getPropertyRoomTypeName();
			
			PropertyRoomTypeMapping propertyRoomTypeDataOld = propertyRoomTypeMappingDao.findRoomTypeByNameForProperty(propertyRoomTypeOld, propertyDetails);
			if(propertyRoomTypeDataOld == null)
			{
				logger.error("Room type to update the configuration does not exist: " + oldRoomTypeConf);
				throw new ISellDBException(ExecutionStatusCodeConstant.DATAERROR.getCode(), "Room type to update the configuration does not exist" + oldRoomTypeConf);
			}
			
			String propertyRoomTypeNew = newRoomTypeConf.getPropertyRoomTypeName();
			PropertyRoomTypeMapping propertyRoomTypeDataNew = propertyRoomTypeMappingDao.findRoomTypeByNameForProperty(propertyRoomTypeNew, propertyDetails);
			if( (propertyRoomTypeDataNew != null )&& (propertyRoomTypeDataNew.getId() !=  propertyRoomTypeDataOld.getId()) )
			{
				logger.error("Room type already exists: " + newRoomTypeConf + " Can not rename room type: " + oldRoomTypeConf);
				throw new ISellDBException(ExecutionStatusCodeConstant.DATAERROR.getCode(), "Room type already exists: " + newRoomTypeConf + " Can not rename room type: " + oldRoomTypeConf);
			}

			propertyRoomTypeMappingDao.updatePropertyRoomTypes(propertyRoomTypeDataOld, newRoomTypeConf);
		}
		else
		{
			if( newRoomTypeConf == null)
			{
				logger.error("PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Missing data to save room type mapping: " + propertyDetailsDto);
				throw new ISellDBException(ExecutionStatusCodeConstant.DATAERROR.getCode(), "PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Missing data to save room type mapping: " + propertyDetailsDto);
			}
			String propertyRoomType =newRoomTypeConf.getPropertyRoomTypeName();
			PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);
			if( propertyDetails == null)
			{
				logger.error("PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Property data missing: " + propertyDetailsDto);
				throw new ISellDBException(ExecutionStatusCodeConstant.DATAERROR.getCode(), "PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Property data missing: " + propertyDetailsDto);
			}

			PropertyRoomTypeMapping propertyRoomTypeData = propertyRoomTypeMappingDao.findRoomTypeByNameForProperty( propertyRoomType, propertyDetails);
			if(propertyRoomTypeData != null)
			{
				logger.error("PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Room type already exists: " + propertyRoomType + " For property : " + propertyDetailsDto.getName());
				throw new ISellDBException(ExecutionStatusCodeConstant.DATAERROR.getCode(), "PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Room type already exists: " + propertyRoomType + " For property : " + propertyDetailsDto.getName());
			}
			
			savePropertyRoomTypeMapping(propertyDetailsDto, newRoomTypeConf.getMasterRoomTypeDTO().getMasterRoomType(),
					propertyRoomType, newRoomTypeConf.getNumRooms(), newRoomTypeConf.isDefault());	
		}
	}

	@Override
	public void deletePropertyRoomTypeMapping(
			PropertyDetailsDTO propertyDetailsDto, String propertyRoomTypeName)
			throws ISellDBException {
		
		PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);
		PropertyRoomTypeMapping propertyRoomTypeDataOld = propertyRoomTypeMappingDao.findRoomTypeByNameForProperty(propertyRoomTypeName, propertyDetails);
		if(propertyRoomTypeDataOld == null)
		{
			logger.error("Room type to delete the configuration does not exist: " + propertyRoomTypeName);
			throw new ISellDBException(ExecutionStatusCodeConstant.DATAERROR.getCode(), "Room type to delete the configuration does not exist" + propertyRoomTypeName + " For property" + propertyDetails);
		}
		
		propertyRoomTypeMappingDao.deletePropertyRoomTypeByName(propertyRoomTypeName, propertyDetailsDto);
	}
}
