package com.ai.sample.common.dto.ui;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.mapping.PropertyRoomTypeMappingDTO;

public class PropertyRoomTypeDeleteJsonDTO {

	private String propertyName;
	private CityDTO cityDTO;

	
	private PropertyRoomTypeMappingDTO roomTypeConf;
	private ExecutionStatusDTO executionStatusDTO = new ExecutionStatusDTO();
	
	public PropertyRoomTypeDeleteJsonDTO() {
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

	public PropertyRoomTypeMappingDTO getRoomTypeConf() {
		return roomTypeConf;
	}

	public void setRoomTypeConf(PropertyRoomTypeMappingDTO roomTypeConf) {
		this.roomTypeConf = roomTypeConf;
	}

	public ExecutionStatusDTO getExecutionStatusDTO() {
		return executionStatusDTO;
	}

	public void setExecutionStatusDTO(ExecutionStatusDTO executionStatusDTO) {
		this.executionStatusDTO = executionStatusDTO;
	}

	@Override
	public String toString() {
		return "PropertyRoomTypeDeleteJsonDTO [propertyName=" + propertyName
				+ ", cityDTO=" + cityDTO + ", roomTypeConf=" + roomTypeConf
				+ ", executionStatusDTO=" + executionStatusDTO + "]";
	}
}
