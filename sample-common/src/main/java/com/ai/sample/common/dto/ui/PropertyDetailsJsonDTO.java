package com.ai.sample.common.dto.ui;

import java.util.ArrayList;
import java.util.List;

import com.ai.sample.common.dto.PropertyDetailsDTO;

public class PropertyDetailsJsonDTO {

	List<PropertyDetailsDTO> propertyDetailsList = new ArrayList<PropertyDetailsDTO>();
 	private ExecutionStatusDTO executionStatusDTO = new ExecutionStatusDTO();
 	
	public PropertyDetailsJsonDTO() {
		super();
	}

	public List<PropertyDetailsDTO> getPropertyDetailsList() {
		return propertyDetailsList;
	}
	
	public void setPropertyDetailsList(List<PropertyDetailsDTO> propertyDetailsList) {
		this.propertyDetailsList = propertyDetailsList;
	}

	public ExecutionStatusDTO getExecutionStatusDTO() {
		return executionStatusDTO;
	}

	public void setExecutionStatusDTO(ExecutionStatusDTO executionStatusDTO) {
		this.executionStatusDTO = executionStatusDTO;
	}

	@Override
	public String toString() {
		return "PropertyDetailsJsonDTO [propertyDetailsList="
				+ propertyDetailsList + ", executionStatusDTO="
				+ executionStatusDTO + "]";
	}
}
