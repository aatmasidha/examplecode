package com.ai.sample.common.dto.configuration;

import com.ai.sample.common.dto.PropertyDetailsDTO;

public class PropertyOtaConnectionMappingDTO {
	int id;
	PropertyDetailsDTO propertyDetailsDto;
	OnlineTravelAgentDetailsDTO onlineTravelAgentDetailsDto;
	String propertyOtaName;
	
	
	public PropertyOtaConnectionMappingDTO() {
		super();
	}
	
	public PropertyOtaConnectionMappingDTO(int id,
			PropertyDetailsDTO propertyDetailsDto,
			OnlineTravelAgentDetailsDTO onlineTravelAgentDetailsDto,
			String propertyOtaName) {
		super();
		this.id = id;
		this.propertyDetailsDto = propertyDetailsDto;
		this.onlineTravelAgentDetailsDto = onlineTravelAgentDetailsDto;
		this.propertyOtaName = propertyOtaName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PropertyDetailsDTO getPropertyDetailsDto() {
		return propertyDetailsDto;
	}
	public void setPropertyDetailsDto(PropertyDetailsDTO propertyDetailsDto) {
		this.propertyDetailsDto = propertyDetailsDto;
	}
	public OnlineTravelAgentDetailsDTO getOnlineTravelAgentDetailsDto() {
		return onlineTravelAgentDetailsDto;
	}
	public void setOnlineTravelAgentDetailsDto(
			OnlineTravelAgentDetailsDTO onlineTravelAgentDetailsDto) {
		this.onlineTravelAgentDetailsDto = onlineTravelAgentDetailsDto;
	}
	public String getPropertyOtaName() {
		return propertyOtaName;
	}
	public void setPropertyOtaName(String propertyOtaName) {
		this.propertyOtaName = propertyOtaName;
	}

	@Override
	public String toString() {
		return "PropertyOtaConnectionMappingDTO [id=" + id
				+ ", propertyDetailsDto=" + propertyDetailsDto
				+ ", onlineTravelAgentDetailsDto="
				+ onlineTravelAgentDetailsDto + ", propertyOtaName="
				+ propertyOtaName + "]";
	}

}
