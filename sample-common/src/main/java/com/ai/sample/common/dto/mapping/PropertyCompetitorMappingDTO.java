package com.ai.sample.common.dto.mapping;

import com.ai.sample.common.dto.PropertyDetailsDTO;

public class PropertyCompetitorMappingDTO {

	private int id;
	private PropertyDetailsDTO propertyDetailsDto;
	
	private String propertyRoomTypeName;
	
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

	public String getPropertyRoomTypeName() {
		return propertyRoomTypeName;
	}

	public void setPropertyRoomTypeName(String propertyRoomTypeName) {
		this.propertyRoomTypeName = propertyRoomTypeName;
	}

	@Override
	public String toString() {
		return "PropertyCompetitorMappingDTO [id=" + id
				+ ", propertyDetailsDto=" + propertyDetailsDto
				+ ", propertyRoomTypeName=" + propertyRoomTypeName + "]";
	}

}
