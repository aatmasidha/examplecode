package com.ai.sample.common.dto.ui;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.configuration.OnlineTravelAgentDetailsDTO;

public class PropertyOnlineTravelAgentDetailsJsonDTO {

	@XmlElement(name = "propertyName")
	private String propertyName;
	private CityDTO cityDTO;
	private List<OnlineTravelAgentDetailsDTO> masterOTADTOList = new ArrayList<OnlineTravelAgentDetailsDTO>();
 	private List<PropertyOnlineTravelAgentMappingDTO> propertyOTAMappingDTOList 
					=  new ArrayList<PropertyOnlineTravelAgentMappingDTO>();
 	ExecutionStatusDTO executionStatusDTO = new ExecutionStatusDTO();
 	
 	public PropertyOnlineTravelAgentDetailsJsonDTO() {
		super();
	}
 	
	public PropertyOnlineTravelAgentDetailsJsonDTO(String propertyName,
			CityDTO cityDTO,
			List<OnlineTravelAgentDetailsDTO> masterOTADTOList,
			List<PropertyOnlineTravelAgentMappingDTO> propertyOTAMappingDTOList) {
		super();
		this.propertyName = propertyName;
		this.cityDTO = cityDTO;
		this.masterOTADTOList = masterOTADTOList;
		this.propertyOTAMappingDTOList = propertyOTAMappingDTOList;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public CityDTO getCityDTO() {
		return cityDTO;
	}
	public void setCityDTO(CityDTO cityDTO) {
		this.cityDTO = cityDTO;
	}
	public List<OnlineTravelAgentDetailsDTO> getMasterOTADTOList() {
		return masterOTADTOList;
	}
	public void setMasterOTADTOList(
			List<OnlineTravelAgentDetailsDTO> masterOTADTOList) {
		this.masterOTADTOList = masterOTADTOList;
	}
	public List<PropertyOnlineTravelAgentMappingDTO> getPropertyOTAMappingDTOList() {
		return propertyOTAMappingDTOList;
	}
	public void setPropertyOTAMappingDTOList(
			List<PropertyOnlineTravelAgentMappingDTO> propertyOTAMappingDTOList) {
		this.propertyOTAMappingDTOList = propertyOTAMappingDTOList;
	}
	
	public ExecutionStatusDTO getExecutionStatusDTO() {
		return executionStatusDTO;
	}

	public void setExecutionStatusDTO(ExecutionStatusDTO executionStatusDTO) {
		this.executionStatusDTO = executionStatusDTO;
	}

	@Override
	public String toString() {
		return "PropertyOnlineTravelAgentDetailsJsonDTO [propertyName="
				+ propertyName + ", cityDTO=" + cityDTO + ", masterOTADTOList="
				+ masterOTADTOList + ", propertyOTAMappingDTOList="
				+ propertyOTAMappingDTOList + "]";
	}
}
