package com.ai.sample.common.dto.ui;

import com.ai.sample.common.dto.configuration.CityDTO;

public class PropertyDetailsInputJsonDTO {

	private String propertyName;
	private CityDTO cityDTO;
	
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
	
	@Override
	public String toString() {
		return "PropertyDetailsInputJsonDTO [propertyName=" + propertyName
				+ ", cityDTO=" + cityDTO + "]";
	}
}
