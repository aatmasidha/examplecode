package com.ai.sample.common.dto.ui;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.configuration.MasterRoomTypeDTO;
import com.ai.sample.common.dto.mapping.PropertyRoomTypeMappingDTO;

public class PropertyRoomTypeDetailsJsonDTO {

	@XmlElement(name = "propertyName")
	private String propertyName;
	private CityDTO cityDTO;
	private List<MasterRoomTypeDTO> masterRoomTypeList = new ArrayList<MasterRoomTypeDTO>();
 	private List<PropertyRoomTypeMappingDTO> propertyRoomTypeMappingDTOList 
					=  new ArrayList<PropertyRoomTypeMappingDTO>();
 	private ExecutionStatusDTO executionStatusDTO = new ExecutionStatusDTO();
 	
	public PropertyRoomTypeDetailsJsonDTO() {
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

	public List<MasterRoomTypeDTO> getMasterRoomTypeList() {
		return masterRoomTypeList;
	}

	public void setMasterRoomTypeList(List<MasterRoomTypeDTO> masterRoomTypeList) {
		this.masterRoomTypeList = masterRoomTypeList;
	}

	public List<PropertyRoomTypeMappingDTO> getPropertyRoomTypeMappingDTOList() {
		return propertyRoomTypeMappingDTOList;
	}

	public void setPropertyRoomTypeMappingDTOList(
			List<PropertyRoomTypeMappingDTO> propertyRoomTypeMappingDTOList) {
		this.propertyRoomTypeMappingDTOList = propertyRoomTypeMappingDTOList;
	}
	
	public ExecutionStatusDTO getExecutionStatusDTO() {
		return executionStatusDTO;
	}

	public void setExecutionStatusDTO(ExecutionStatusDTO executionStatusDTO) {
		this.executionStatusDTO = executionStatusDTO;
	}

	@Override
	public String toString() {
		return "PropertyRoomTypeDetailsJsonDTO [propertyName=" + propertyName
				+ ", cityDTO=" + cityDTO + ", masterRoomTypeList="
				+ masterRoomTypeList + ", propertyRoomTypeMappingDTOList="
				+ propertyRoomTypeMappingDTOList + ", executionStatusDTO="
				+ executionStatusDTO + "]";
	}
}
