package com.ai.sample.common.dto.ui;

import com.ai.sample.common.dto.PropertyDetailsDTO;

public class PropertyDetailsDeleteJsonDTO {

	private PropertyDetailsDTO propertyDetailsDto;
	private ExecutionStatusDTO executionStatusDTO = new ExecutionStatusDTO();
	
	public PropertyDetailsDeleteJsonDTO() {
		super();
	}

	public PropertyDetailsDTO getPropertyDetailsDto() {
		return propertyDetailsDto;
	}

	public void setPropertyDetailsDto(PropertyDetailsDTO propertyDetailsDto) {
		this.propertyDetailsDto = propertyDetailsDto;
	}

	public ExecutionStatusDTO getExecutionStatusDTO() {
		return executionStatusDTO;
	}

	public void setExecutionStatusDTO(ExecutionStatusDTO executionStatusDTO) {
		this.executionStatusDTO = executionStatusDTO;
	}

	@Override
	public String toString() {
		return "PropertyDetailsDeleteJsonDTO [propertyDetailsDto="
				+ propertyDetailsDto + ", executionStatusDTO="
				+ executionStatusDTO + "]";
	}
}
