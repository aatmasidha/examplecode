package com.ai.sample.common.dto.ui;

import com.ai.sample.common.dto.configuration.OnlineTravelAgentDetailsDTO;

public class PropertyOnlineTravelAgentMappingDTO {
	int id;
	String propertyOTAName;
	OnlineTravelAgentDetailsDTO otaDetailsDTO;
	
	public PropertyOnlineTravelAgentMappingDTO() {
		super();
	}

	public PropertyOnlineTravelAgentMappingDTO(int id, String propertyOTAName,
			OnlineTravelAgentDetailsDTO otaDetailsDTO) {
		super();
		this.id = id;
		this.propertyOTAName = propertyOTAName;
		this.otaDetailsDTO = otaDetailsDTO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPropertyOTAName() {
		return propertyOTAName;
	}

	public void setPropertyOTAName(String propertyOTAName) {
		this.propertyOTAName = propertyOTAName;
	}

	public OnlineTravelAgentDetailsDTO getOtaDetailsDTO() {
		return otaDetailsDTO;
	}

	public void setOtaDetailsDTO(OnlineTravelAgentDetailsDTO otaDetailsDTO) {
		this.otaDetailsDTO = otaDetailsDTO;
	}

	@Override
	public String toString() {
		return "PropertyOnlineTravelAgentMappingDTO [id=" + id
				+ ", propertyOTAName=" + propertyOTAName + ", otaDetailsDTO="
				+ otaDetailsDTO + "]";
	}
}
