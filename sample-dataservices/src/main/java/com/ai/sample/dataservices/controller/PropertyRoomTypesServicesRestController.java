package com.ai.sample.dataservices.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ai.sample.common.constants.EnumConstants.ExecutionStatusCodeConstant;
import com.ai.sample.common.constants.EnumConstants.ExecutionStatusDescConstant;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.MasterRoomTypeDTO;
import com.ai.sample.common.dto.mapping.PropertyRoomTypeMappingDTO;
import com.ai.sample.common.dto.ui.ExecutionStatusDTO;
import com.ai.sample.common.dto.ui.PropertyDetailsInputJsonDTO;
import com.ai.sample.common.dto.ui.PropertyRoomTypeDeleteJsonDTO;
import com.ai.sample.common.dto.ui.PropertyRoomTypeDetailsJsonDTO;
import com.ai.sample.common.dto.ui.PropertyRoomTypePersistJsonDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.service.configuration.MasterRoomTypeService;
import com.ai.sample.db.service.mapping.PropertyRoomTypeMappingService;

@RestController
@RequestMapping("/configuration/")
public class PropertyRoomTypesServicesRestController {

	static final Logger logger = Logger
			.getLogger(PropertyRoomTypesServicesRestController.class);

	@RequestMapping(value = "/getpropertyroomtypes/", method = RequestMethod.POST, consumes = "application/json")
	public PropertyRoomTypeDetailsJsonDTO getPropertyRoomtypes(
			@RequestBody PropertyDetailsInputJsonDTO propertyDetailsJSON) {// REST
																			// Endpoint.

		PropertyRoomTypeDetailsJsonDTO propertyRoomTypeDetailsJsonDTO = new PropertyRoomTypeDetailsJsonDTO();
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");

			MasterRoomTypeService masterRoomTypeService = context
					.getBean(MasterRoomTypeService.class);

			List<MasterRoomTypeDTO> masterRoomTypeList = masterRoomTypeService
					.findAllMasterRoomTypes();

			PropertyRoomTypeMappingService propertyRoomTypeMappingService = context
					.getBean(PropertyRoomTypeMappingService.class);

			PropertyDetailsDTO propertyDetailsDTO = new PropertyDetailsDTO(
					propertyDetailsJSON.getPropertyName(),
					propertyDetailsJSON.getCityDTO());
			List<PropertyRoomTypeMappingDTO> propertyRoomTypeMappingDTOList = propertyRoomTypeMappingService
					.findAllRoomTypesForProperty(propertyDetailsDTO);

			propertyRoomTypeDetailsJsonDTO.setPropertyName(propertyDetailsJSON
					.getPropertyName());
			propertyRoomTypeDetailsJsonDTO.setCityDTO(propertyDetailsJSON
					.getCityDTO());
			propertyRoomTypeDetailsJsonDTO
					.setPropertyRoomTypeMappingDTOList(propertyRoomTypeMappingDTOList);
			propertyRoomTypeDetailsJsonDTO
					.setMasterRoomTypeList(masterRoomTypeList);

			((AbstractApplicationContext) context).close();

			ExecutionStatusDTO executionStatusDTO = propertyRoomTypeDetailsJsonDTO
					.getExecutionStatusDTO();
			executionStatusDTO.setStatusCode(ExecutionStatusCodeConstant.SUCCESS.getCode());
			executionStatusDTO
					.setStatusDescription(ExecutionStatusDescConstant.SUCCESS
							.getStatus());
			return propertyRoomTypeDetailsJsonDTO;
		} catch (ISellDBException e) {
			logger.error("Exception while fetching room type information for property: "
					+ propertyDetailsJSON.toString());
			logger.error("Exception is: " + e.getMessage());
			ExecutionStatusDTO executionStatusDTO = propertyRoomTypeDetailsJsonDTO
					.getExecutionStatusDTO();
			executionStatusDTO.setStatusCode(ExecutionStatusCodeConstant.EXCEPTION.getCode());
			executionStatusDTO
					.setStatusDescription(ExecutionStatusDescConstant.FAILURE
							.getStatus()
							+ " to get room type information: "
							+ e.getMessage());
			return propertyRoomTypeDetailsJsonDTO;
		}
	}

	@RequestMapping(value = "/setpropertyroomtype/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public PropertyRoomTypePersistJsonDTO setPropertyRoomTypeInformation(
			@RequestBody PropertyRoomTypePersistJsonDTO propertyRoomTypePersistJsonDTO)
			throws Exception {
		ExecutionStatusDTO executionStatusDTO = propertyRoomTypePersistJsonDTO
				.getExecutionStatusDTO();
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");

			PropertyRoomTypeMappingService propertyRoomTypeMappingService = context
					.getBean(PropertyRoomTypeMappingService.class);

			PropertyRoomTypeMappingDTO oldRoomTypeConf = propertyRoomTypePersistJsonDTO
					.getOldRoomTypeConf();
			PropertyRoomTypeMappingDTO newRoomTypeConf = propertyRoomTypePersistJsonDTO
					.getNewRoomTypeConf();

			PropertyDetailsDTO propertyDetailsDto = new PropertyDetailsDTO(
					propertyRoomTypePersistJsonDTO.getPropertyName(),
					propertyRoomTypePersistJsonDTO.getCityDTO());
			propertyRoomTypeMappingService.savePropertyRoomTypeMapping(
					propertyDetailsDto, oldRoomTypeConf, newRoomTypeConf,
					propertyRoomTypePersistJsonDTO.isEdit());

			((AbstractApplicationContext) context).close();
			executionStatusDTO
					.setStatusCode(ExecutionStatusCodeConstant.SUCCESS
							.getCode());
			executionStatusDTO
					.setStatusDescription(ExecutionStatusDescConstant.SUCCESS
							.getStatus());
		} catch (ISellDBException e) {
			executionStatusDTO
					.setStatusCode(ExecutionStatusCodeConstant.EXCEPTION
							.getCode());
			executionStatusDTO
					.setStatusDescription(ExecutionStatusDescConstant.FAILURE
							.getStatus()
							+ " while updating details for Room Type: "
							+ e.getMessage());
//			return propertyRoomTypePersistJsonDTO;
		}
		return propertyRoomTypePersistJsonDTO;
	}

	@RequestMapping(value = "/deletepropertyroomtype/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public PropertyRoomTypeDeleteJsonDTO deletePropertyRoomTypeInformation(
			@RequestBody PropertyRoomTypeDeleteJsonDTO deletePropertyRoomTypeDTO)
	{
		ExecutionStatusDTO executionStatusDTO = deletePropertyRoomTypeDTO.getExecutionStatusDTO();
		try
		{
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");
			PropertyRoomTypeMappingService propertyRoomTypeMappingService = context
					.getBean(PropertyRoomTypeMappingService.class);
			
			PropertyDetailsDTO propertyDetailsDto = new PropertyDetailsDTO(deletePropertyRoomTypeDTO.getPropertyName(), 
													deletePropertyRoomTypeDTO.getCityDTO());
			propertyRoomTypeMappingService.deletePropertyRoomTypeMapping(propertyDetailsDto, deletePropertyRoomTypeDTO.getRoomTypeConf().getPropertyRoomTypeName());
			
			((AbstractApplicationContext) context).close();
			executionStatusDTO
					.setStatusCode(ExecutionStatusCodeConstant.SUCCESS
							.getCode());
			executionStatusDTO
					.setStatusDescription(ExecutionStatusDescConstant.SUCCESS
							.getStatus());
		}
		catch(ISellDBException e)
		{
			executionStatusDTO
			.setStatusCode(ExecutionStatusCodeConstant.EXCEPTION
					.getCode());
			executionStatusDTO
			.setStatusDescription(ExecutionStatusDescConstant.FAILURE
					.getStatus()
					+ " while deleting details for Room Type: "
					+ e.getMessage());
		}
		return deletePropertyRoomTypeDTO;
	}
}
