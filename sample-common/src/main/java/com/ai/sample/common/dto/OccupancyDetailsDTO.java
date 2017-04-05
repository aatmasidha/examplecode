package com.ai.sample.common.dto;

import java.util.Date;

public class OccupancyDetailsDTO {

	private PropertyDetailsDTO propertyDetailsDto;
	String hotelRoomType;
	private Date occupancyDate;
	private int capacity;
	private int occupancy;
	private int outOfOrderCount;
	private int vacantCount;
	private String roomTypeName;
	private int lastPickUp;


	public OccupancyDetailsDTO() {
		super();
	}

	public OccupancyDetailsDTO(PropertyDetailsDTO propertyDetailsDto,
			String hotelRoomType, Date occupancyDate, int capacity,
			int occupancy, int outOfOrderCount, int vacantCount, String roomTypeName, int lastPickUp) {
		super();
		this.propertyDetailsDto = propertyDetailsDto;
		this.hotelRoomType = hotelRoomType;
		this.occupancyDate = occupancyDate;
		this.capacity = capacity;
		this.occupancy = occupancy;
		this.outOfOrderCount = outOfOrderCount;
		this.roomTypeName = roomTypeName;
		this.lastPickUp = lastPickUp;
	}

	public OccupancyDetailsDTO(Date occupancyDate, int capacity,
			int occupancy, int outOfOrder, int vacantCount, int lastPickUp) {
		this.occupancyDate = occupancyDate;
		this.capacity = capacity;
		this.occupancy = occupancy;
		this.outOfOrderCount = outOfOrder;
		this.vacantCount = vacantCount;
		this.lastPickUp = lastPickUp;
	}

	public PropertyDetailsDTO getPropertyDetailsDto() {
		return propertyDetailsDto;
	}

	public void setPropertyDetailsDto(PropertyDetailsDTO propertyDetailsDto) {
		this.propertyDetailsDto = propertyDetailsDto;
	}

	public String getHotelRoomType() {
		return hotelRoomType;
	}

	public void setHotelRoomType(String hotelRoomType) {
		this.hotelRoomType = hotelRoomType;
	}

	public Date getOccupancyDate() {
		return occupancyDate;
	}

	public void setOccupancyDate(Date occupancyDate) {
		this.occupancyDate = occupancyDate;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}
	
	public int getOutOfOrderCount() {
		return outOfOrderCount;
	}

	public void setOutOfOrderCount(int outOfOrderCount) {
		this.outOfOrderCount = outOfOrderCount;
	}

	
	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	
	public int getVacantCount() {
		return vacantCount;
	}

	public void setVacantCount(int vacantCount) {
		this.vacantCount = vacantCount;
	}

	
	public int getLastPickUp() {
		return lastPickUp;
	}

	public void setLastPickUp(int lastPickUp) {
		this.lastPickUp = lastPickUp;
	}

	@Override
	public String toString() {
		return "OccupancyDetailsDTO [propertyDetailsDto=" + propertyDetailsDto
				+ ", hotelRoomType=" + hotelRoomType + ", occupancyDate="
				+ occupancyDate + ", capacity=" + capacity + ", occupancy="
				+ occupancy + ", outOfOrderCount=" + outOfOrderCount
				+ ", vacantCount=" + vacantCount + ", roomTypeName="
				+ roomTypeName + ", lastPickUp=" + lastPickUp + "]";
	}
}
