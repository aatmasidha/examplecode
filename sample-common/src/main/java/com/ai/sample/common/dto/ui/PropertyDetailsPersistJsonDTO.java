package com.ai.sample.common.dto.ui;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;

public class PropertyDetailsPersistJsonDTO {

	private String propertyName;
	private CityDTO cityDTO;
	
	private PropertyDetailsDTO oldPropertyDetails;
	private PropertyDetailsDTO newPropertyDetails;
	
	private boolean edit;
	private ExecutionStatusDTO executionStatusDTO = new ExecutionStatusDTO();
	
	public PropertyDetailsPersistJsonDTO() {
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

	public PropertyDetailsDTO getOldPropertyDetails() {
		return oldPropertyDetails;
	}

	public void setOldPropertyDetails(PropertyDetailsDTO oldPropertyDetails) {
		this.oldPropertyDetails = oldPropertyDetails;
	}

	public PropertyDetailsDTO getNewPropertyDetails() {
		return newPropertyDetails;
	}

	public void setNewPropertyDetails(PropertyDetailsDTO newPropertyDetails) {
		this.newPropertyDetails = newPropertyDetails;
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
		return "PropertyDetailsPersistJsonDTO [propertyName=" + propertyName
				+ ", cityDTO=" + cityDTO + ", oldPropertyDetails="
				+ oldPropertyDetails + ", newPropertyDetails="
				+ newPropertyDetails + ", edit=" + edit
				+ ", executionStatusDTO=" + executionStatusDTO + "]";
	}
}
