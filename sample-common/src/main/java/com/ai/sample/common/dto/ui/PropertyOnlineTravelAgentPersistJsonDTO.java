package com.ai.sample.common.dto.ui;

import com.ai.sample.common.dto.configuration.CityDTO;

public class PropertyOnlineTravelAgentPersistJsonDTO {

	private String propertyName;
	private CityDTO cityDTO;

	private PropertyOnlineTravelAgentMappingDTO otaDtoOld;
	private PropertyOnlineTravelAgentMappingDTO otaDtoNew;
	
	private boolean edit;
	private ExecutionStatusDTO executionStatusDTO = new ExecutionStatusDTO();
	
	public PropertyOnlineTravelAgentPersistJsonDTO() {
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

	

	public PropertyOnlineTravelAgentMappingDTO getOtaDtoOld() {
		return otaDtoOld;
	}

	public void setOtaDtoOld(PropertyOnlineTravelAgentMappingDTO otaDtoOld) {
		this.otaDtoOld = otaDtoOld;
	}

	public PropertyOnlineTravelAgentMappingDTO getOtaDtoNew() {
		return otaDtoNew;
	}

	public void setOtaDtoNew(PropertyOnlineTravelAgentMappingDTO otaDtoNew) {
		this.otaDtoNew = otaDtoNew;
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
		return "PropertyOnlineTravelAgentPersistJsonDTO [propertyName="
				+ propertyName + ", cityDTO=" + cityDTO + ", otaDtoOld="
				+ otaDtoOld + ", otaDtoNew=" + otaDtoNew + ", edit=" + edit
				+ ", executionStatusDTO=" + executionStatusDTO + "]";
	}
}
