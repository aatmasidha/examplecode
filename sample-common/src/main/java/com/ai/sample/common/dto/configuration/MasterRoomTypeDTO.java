package com.ai.sample.common.dto.configuration;

public class MasterRoomTypeDTO {
	int id;
	String masterRoomType;
	
	public MasterRoomTypeDTO() {
		super();
	}
	
	public MasterRoomTypeDTO(int id, String masterRoomType) {
		super();
		this.id = id;
		this.masterRoomType = masterRoomType;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMasterRoomType() {
		return masterRoomType;
	}
	public void setMasterRoomType(String masterRoomType) {
		this.masterRoomType = masterRoomType;
	}
	
	@Override
	public String toString() {
		return "MasterRoomTypeDTO [id=" + id + ", masterRoomType="
				+ masterRoomType + "]";
	}
}
