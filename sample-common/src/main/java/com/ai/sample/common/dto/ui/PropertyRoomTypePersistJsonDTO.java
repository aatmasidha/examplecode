package com.ai.sample.common.dto.ui;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.mapping.PropertyRoomTypeMappingDTO;

public class PropertyRoomTypePersistJsonDTO {

	private String propertyName;
	private CityDTO cityDTO;

	
	private PropertyRoomTypeMappingDTO oldRoomTypeConf;
	private PropertyRoomTypeMappingDTO newRoomTypeConf;
	
	private boolean edit;
	private ExecutionStatusDTO executionStatusDTO = new ExecutionStatusDTO();
	
	public PropertyRoomTypePersistJsonDTO() {
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

	public PropertyRoomTypeMappingDTO getOldRoomTypeConf() {
		return oldRoomTypeConf;
	}

	public void setOldRoomTypeConf(PropertyRoomTypeMappingDTO oldRoomTypeConf) {
		this.oldRoomTypeConf = oldRoomTypeConf;
	}

	public PropertyRoomTypeMappingDTO getNewRoomTypeConf() {
		return newRoomTypeConf;
	}

	public void setNewRoomTypeConf(PropertyRoomTypeMappingDTO newRoomTypeConf) {
		this.newRoomTypeConf = newRoomTypeConf;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public ExecutionStatusDTO getExecutionStatusDTO() {
		return executionStatusDTO;
	}

	public void setExecutionStatusDTO(ExecutionStatusDTO executionStatusDTO) {
		this.executionStatusDTO = executionStatusDTO;
	}

	@Override
	public String toString() {
		return "PropertyRoomTypePersistJsonDTO [propertyName=" + propertyName
				+ ", cityDTO=" + cityDTO + ", oldRoomTypeConf="
				+ oldRoomTypeConf + ", newRoomTypeConf=" + newRoomTypeConf
				+ ", edit=" + edit + ", executionStatusDTO="
				+ executionStatusDTO + "]";
	}
}
