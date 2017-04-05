package com.ai.sample.common.dto.ui;

import com.ai.sample.common.dto.configuration.CityDTO;

public class PropertyOTADeleteJsonDTO {

	private String propertyName;
	private CityDTO cityDTO;

	private PropertyOnlineTravelAgentMappingDTO otaConf;
	private ExecutionStatusDTO executionStatusDTO = new ExecutionStatusDTO();
	
	public PropertyOTADeleteJsonDTO() {
		super();
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

	public PropertyOnlineTravelAgentMappingDTO getOtaConf() {
		return otaConf;
	}

	public void setOtaConf(PropertyOnlineTravelAgentMappingDTO otaConf) {
		this.otaConf = otaConf;
	}

	public ExecutionStatusDTO getExecutionStatusDTO() {
		return executionStatusDTO;
	}

	public void setExecutionStatusDTO(ExecutionStatusDTO executionStatusDTO) {
		this.executionStatusDTO = executionStatusDTO;
	}

	@Override
	public String toString() {
		return "PropertyOTADeleteJsonDTO [propertyName=" + propertyName
				+ ", cityDTO=" + cityDTO + ", otaConf=" + otaConf
				+ ", executionStatusDTO=" + executionStatusDTO + "]";
	}
}
