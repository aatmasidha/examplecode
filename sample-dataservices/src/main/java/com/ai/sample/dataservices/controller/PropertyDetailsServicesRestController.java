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
import com.ai.sample.common.dto.ui.ExecutionStatusDTO;
import com.ai.sample.common.dto.ui.PropertyDetailsDeleteJsonDTO;
import com.ai.sample.common.dto.ui.PropertyDetailsJsonDTO;
import com.ai.sample.common.dto.ui.PropertyDetailsPersistJsonDTO;
import com.ai.sample.common.dto.ui.PropertyRoomTypeDeleteJsonDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.service.mapping.PropertyRoomTypeMappingService;
import com.ai.sample.db.service.property.configuration.PropertyDetailsService;

@RestController
@RequestMapping("/configuration/")
public class PropertyDetailsServicesRestController {

	static final Logger logger = Logger.getLogger(PropertyDetailsServicesRestController.class);

	@RequestMapping(value  ="/getallproperties/", method = RequestMethod.GET)
	public PropertyDetailsJsonDTO getPropertyRoomtypes(){//REST Endpoint.
		PropertyDetailsJsonDTO propertyDetailsJsonDTO =  new PropertyDetailsJsonDTO();
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");
			PropertyDetailsService propertyDetailsService = context.getBean(PropertyDetailsService.class);
			
			List<PropertyDetailsDTO>  propertyRoomTypeMappingDTOList = propertyDetailsService.findAllHotelPropertyDetails();

			propertyDetailsJsonDTO.setPropertyDetailsList(propertyRoomTypeMappingDTOList);
			
			ExecutionStatusDTO executionStatusDTO = propertyDetailsJsonDTO.getExecutionStatusDTO();
			executionStatusDTO.setStatusCode(ExecutionStatusCodeConstant.SUCCESS.getCode());
			executionStatusDTO.setStatusDescription(ExecutionStatusDescConstant.SUCCESS.getStatus());
			
			((AbstractApplicationContext)context).close();
			
			return propertyDetailsJsonDTO;
		} catch (ISellDBException e) {
			logger.error("Exception while fetching list of all properties. " );
			logger.error("Exception is: " + e.getMessage());
			ExecutionStatusDTO executionStatusDTO = propertyDetailsJsonDTO.getExecutionStatusDTO();
			executionStatusDTO.setStatusCode(e.getErrorCode());
			executionStatusDTO.setStatusDescription( ExecutionStatusDescConstant.FAILURE.getStatus() + " to get room type information: " + e.getMessage());
			return propertyDetailsJsonDTO;
		}
	}
	
	@RequestMapping(value = "/setpropertydetails/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public PropertyDetailsPersistJsonDTO setPropertyRoomTypeInformation(
			@RequestBody PropertyDetailsPersistJsonDTO propertyDetailsPersistJsonDTO)
			throws Exception {
		ExecutionStatusDTO executionStatusDTO = propertyDetailsPersistJsonDTO
				.getExecutionStatusDTO();
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");

			PropertyDetailsService propertyRoomTypeMappingService = context
					.getBean(PropertyDetailsService.class);

			PropertyDetailsDTO oldPropertyDetails = propertyDetailsPersistJsonDTO
					.getOldPropertyDetails();
			PropertyDetailsDTO newPropertyDetails = propertyDetailsPersistJsonDTO
					.getNewPropertyDetails();

			PropertyDetailsDTO propertyDetailsDto = new PropertyDetailsDTO(
					propertyDetailsPersistJsonDTO.getPropertyName(),
					propertyDetailsPersistJsonDTO.getCityDTO());
			propertyRoomTypeMappingService.saveOrUpdatePropertyDetails(
					propertyDetailsDto, oldPropertyDetails, newPropertyDetails,
					propertyDetailsPersistJsonDTO.isEdit());

			((AbstractApplicationContext) context).close();
			executionStatusDTO
					.setStatusCode(ExecutionStatusCodeConstant.SUCCESS
							.getCode());
			executionStatusDTO
					.setStatusDescription(ExecutionStatusDescConstant.SUCCESS
							.getStatus());
		} catch (Exception e) {
			executionStatusDTO
					.setStatusCode(ExecutionStatusCodeConstant.EXCEPTION
							.getCode());
			executionStatusDTO
					.setStatusDescription(ExecutionStatusDescConstant.FAILURE
							.getStatus()
							+ " while updating details for Room Type: "
							+ e.getMessage());
		}
		return propertyDetailsPersistJsonDTO;
	}
	
	@RequestMapping(value = "/deleteproperty/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public PropertyDetailsDeleteJsonDTO deletePropertyInformation(
			@RequestBody PropertyDetailsDeleteJsonDTO deletePropertyDetailsDto)
	{
		ExecutionStatusDTO executionStatusDTO = deletePropertyDetailsDto.getExecutionStatusDTO();
		try
		{
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");
			PropertyDetailsService propertyPropertyDetailsService = context
					.getBean(PropertyDetailsService.class);
			
			PropertyDetailsDTO propertyDetailsDto = new PropertyDetailsDTO(deletePropertyDetailsDto.getPropertyDetailsDto().getName(), 
													deletePropertyDetailsDto.getPropertyDetailsDto().getCityDto());
			propertyPropertyDetailsService.deletePropertyDetails(propertyDetailsDto);
			
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
					+ " while deleting details for Property: "
					+ e.getMessage());
		}
		return deletePropertyDetailsDto;
	}
}
