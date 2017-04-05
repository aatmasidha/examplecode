package com.ai.sample.dataservices.controller;

import java.util.ArrayList;
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
import com.ai.sample.common.dto.configuration.OnlineTravelAgentDetailsDTO;
import com.ai.sample.common.dto.configuration.PropertyOtaConnectionMappingDTO;
import com.ai.sample.common.dto.ui.ExecutionStatusDTO;
import com.ai.sample.common.dto.ui.PropertyDetailsInputJsonDTO;
import com.ai.sample.common.dto.ui.PropertyOTADeleteJsonDTO;
import com.ai.sample.common.dto.ui.PropertyOnlineTravelAgentDetailsJsonDTO;
import com.ai.sample.common.dto.ui.PropertyOnlineTravelAgentMappingDTO;
import com.ai.sample.common.dto.ui.PropertyOnlineTravelAgentPersistJsonDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.service.configuration.OnlineTravelAgentService;
import com.ai.sample.db.service.mapping.PropertyOnlineTravelAgentConnectionMappingService;


@RestController
@RequestMapping("/configuration/")
public class PropertyOnlineTravelAgentServicesRestController{

	static final Logger logger = Logger.getLogger(PropertyOnlineTravelAgentServicesRestController.class);

	@RequestMapping(value  ="/getpropertyota/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public PropertyOnlineTravelAgentDetailsJsonDTO getPropertyOTAList(@RequestBody PropertyDetailsInputJsonDTO propertyDetailsJSON) throws Exception{
		PropertyOnlineTravelAgentDetailsJsonDTO propertyOTADetailsJsonDTO =  new PropertyOnlineTravelAgentDetailsJsonDTO();
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");
			OnlineTravelAgentService onlineTravelAgentService = context.getBean(OnlineTravelAgentService.class);

			List<OnlineTravelAgentDetailsDTO>  onlineTravelAgentList = onlineTravelAgentService.findAllOnlineTravelAgents();
			
			PropertyOnlineTravelAgentConnectionMappingService propertyOTAMappingService = context.getBean(PropertyOnlineTravelAgentConnectionMappingService.class);
			
			PropertyDetailsDTO propertyDetailsDTO = new PropertyDetailsDTO(propertyDetailsJSON.getPropertyName(), propertyDetailsJSON.getCityDTO());
			List<PropertyOtaConnectionMappingDTO>  propertyOTAMappingDTOList = propertyOTAMappingService.findAllOtsForProperty(propertyDetailsDTO);

			propertyOTADetailsJsonDTO.setPropertyName(propertyDetailsJSON.getPropertyName());
			propertyOTADetailsJsonDTO.setCityDTO(propertyDetailsJSON.getCityDTO());
			
			List<PropertyOnlineTravelAgentMappingDTO> propertyOnlineTravelAgentMappingDTOList =  new ArrayList<PropertyOnlineTravelAgentMappingDTO>();
			for( PropertyOtaConnectionMappingDTO propertyOTAMappingDTO : propertyOTAMappingDTOList)
			{
				PropertyOnlineTravelAgentMappingDTO propertyOnlineTravelAgentMappingDTO = new PropertyOnlineTravelAgentMappingDTO( propertyOTAMappingDTO.getId(), propertyOTAMappingDTO.getPropertyOtaName(), propertyOTAMappingDTO.getOnlineTravelAgentDetailsDto());
				propertyOnlineTravelAgentMappingDTOList.add(propertyOnlineTravelAgentMappingDTO);
			}
			propertyOTADetailsJsonDTO.setPropertyOTAMappingDTOList(propertyOnlineTravelAgentMappingDTOList);
			propertyOTADetailsJsonDTO.setMasterOTADTOList(onlineTravelAgentList);
			
			ExecutionStatusDTO executionStatusDTO = propertyOTADetailsJsonDTO.getExecutionStatusDTO();
			executionStatusDTO.setStatusCode(ExecutionStatusCodeConstant.SUCCESS.getCode());
			executionStatusDTO.setStatusDescription(ExecutionStatusDescConstant.SUCCESS.getStatus());
			
			((AbstractApplicationContext)context).close();
			return propertyOTADetailsJsonDTO;
		} catch (ISellDBException e) {
			logger.error("Exception while fetching online travel aganet information for property: " + propertyDetailsJSON.toString());
			logger.error("Exception is: " + e.getMessage());
			ExecutionStatusDTO executionStatusDTO = propertyOTADetailsJsonDTO.getExecutionStatusDTO();
			executionStatusDTO.setStatusCode(e.getErrorCode());
			executionStatusDTO.setStatusDescription(ExecutionStatusDescConstant.FAILURE.getStatus() + " to get room type information: " + e.getMessage());
			return propertyOTADetailsJsonDTO;
		}
	}
	
	
	@RequestMapping(value  ="/setpropertyota/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")

	public PropertyOnlineTravelAgentPersistJsonDTO setPropertyOTAList(@RequestBody PropertyOnlineTravelAgentPersistJsonDTO propertyOnlineTravelAgentPersistJsonDTO) throws Exception{
		
		ExecutionStatusDTO executionStatusDTO = propertyOnlineTravelAgentPersistJsonDTO.getExecutionStatusDTO();
		try
		{
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");
			
			PropertyDetailsDTO propertyDetailsDTO = new PropertyDetailsDTO(propertyOnlineTravelAgentPersistJsonDTO.getPropertyName(), propertyOnlineTravelAgentPersistJsonDTO.getCityDTO());
			PropertyOnlineTravelAgentConnectionMappingService propertyOTAMappingService = context.getBean(PropertyOnlineTravelAgentConnectionMappingService.class);
	
			PropertyOnlineTravelAgentMappingDTO propertyOTANameOld = propertyOnlineTravelAgentPersistJsonDTO.getOtaDtoOld();
			PropertyOnlineTravelAgentMappingDTO propertyOTANameNew = propertyOnlineTravelAgentPersistJsonDTO.getOtaDtoNew();
			boolean edit = propertyOnlineTravelAgentPersistJsonDTO.isEdit();
			propertyOTAMappingService.saveOnlineTravelAgent( propertyDetailsDTO, propertyOTANameOld,
					propertyOTANameNew, edit);
			
			((AbstractApplicationContext)context).close();
			
			executionStatusDTO.setStatusCode(ExecutionStatusCodeConstant.SUCCESS.getCode());
			executionStatusDTO
					.setStatusDescription(ExecutionStatusDescConstant.SUCCESS
							.getStatus());
			return propertyOnlineTravelAgentPersistJsonDTO;

		}
		catch(ISellDBException e)
		{
			executionStatusDTO.setStatusCode(ExecutionStatusCodeConstant.EXCEPTION.getCode());
			executionStatusDTO.setStatusDescription(ExecutionStatusDescConstant.FAILURE.getStatus() + " while updating details for OTA: " + e.getMessage());
			return propertyOnlineTravelAgentPersistJsonDTO;
		}
	}

	@RequestMapping(value = "/deletepropertyota/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public PropertyOTADeleteJsonDTO deletePropertyOtaMapping(
			@RequestBody PropertyOTADeleteJsonDTO deletePropertyOtaDTO)
	{
		ExecutionStatusDTO executionStatusDTO = deletePropertyOtaDTO.getExecutionStatusDTO();
		try
		{
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"config.xml");
			PropertyOnlineTravelAgentConnectionMappingService propertyOtaMappingService = context
					.getBean(PropertyOnlineTravelAgentConnectionMappingService.class);
			
			PropertyDetailsDTO propertyDetailsDto = new PropertyDetailsDTO(deletePropertyOtaDTO.getPropertyName(), 
													deletePropertyOtaDTO.getCityDTO());
			propertyOtaMappingService.deleteOnlineTravelAgent(propertyDetailsDto, deletePropertyOtaDTO.getOtaConf());
			
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
		return deletePropertyOtaDTO;
	}
}
