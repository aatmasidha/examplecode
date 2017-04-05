package com.ai.sample.common.dto.mapping;

import com.ai.sample.common.dto.configuration.MasterRoomTypeDTO;

public class PropertyRoomTypeMappingDTO {
	private int id;
	private MasterRoomTypeDTO masterRoomTypeDTO;
	private String propertyRoomTypeName;
	private boolean isDefault;
	private int numRooms;

	public PropertyRoomTypeMappingDTO() {
		super();
	}

	public PropertyRoomTypeMappingDTO(MasterRoomTypeDTO masterRoomTypeDTO,
			String propertyRoomTypeName, int id, boolean isDefault, int numRooms) {
		super();
		this.masterRoomTypeDTO = masterRoomTypeDTO;
		this.propertyRoomTypeName = propertyRoomTypeName;
		this.id = id;
		this.isDefault = isDefault;
		this.numRooms = numRooms;
	}

	public String getPropertyRoomTypeName() {
		return propertyRoomTypeName;
	}

	public void setPropertyRoomTypeName(String propertyRoomTypeName) {
		this.propertyRoomTypeName = propertyRoomTypeName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public MasterRoomTypeDTO getMasterRoomTypeDTO() {
		return masterRoomTypeDTO;
	}

	public void setMasterRoomTypeDTO(MasterRoomTypeDTO masterRoomTypeDTO) {
		this.masterRoomTypeDTO = masterRoomTypeDTO;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public int getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	@Override
	public String toString() {
		return "PropertyRoomTypeMappingDTO [id=" + id + ", masterRoomTypeDTO="
				+ masterRoomTypeDTO + ", propertyRoomTypeName="
				+ propertyRoomTypeName + ", isDefault=" + isDefault
				+ ", numRooms=" + numRooms + "]";
	}
}
