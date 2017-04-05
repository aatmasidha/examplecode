package com.ai.sample.common.dto;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class ChannelManagerAvailabilityDTO {

	private PropertyDetailsDTO propertyDetailsDto;
	private Date occupancyDate;
	private float rateOnChannelManager; 
	String ratePlan;
//  Map will store the value of 
	Map<String, Integer> roomTypeCapcityMap = new  LinkedHashMap<>();

	public ChannelManagerAvailabilityDTO() {
		super();
	}

	

	public ChannelManagerAvailabilityDTO(PropertyDetailsDTO propertyDetailsDto,
			Date occupancyDate, float rateOnChannelManager, 
			Map<String, Integer> roomTypeCapcityMap, String ratePlan) {
		super();
		this.propertyDetailsDto = propertyDetailsDto;
		this.occupancyDate = occupancyDate;
		this.rateOnChannelManager = rateOnChannelManager;
		this.roomTypeCapcityMap = roomTypeCapcityMap;
	}



	public PropertyDetailsDTO getPropertyDetailsDto() {
		return propertyDetailsDto;
	}



	public void setPropertyDetailsDto(PropertyDetailsDTO propertyDetailsDto) {
		this.propertyDetailsDto = propertyDetailsDto;
	}

	public Date getOccupancyDate() {
		return occupancyDate;
	}

	public void setOccupancyDate(Date occupancyDate) {
		this.occupancyDate = occupancyDate;
	}

	public float getRateOnChannelManager() {
		return rateOnChannelManager;
	}


	public void setRateOnChannelManager(float rateOnChannelManager) {
		this.rateOnChannelManager = rateOnChannelManager;
	}



	public Map<String, Integer> getRoomTypeCapcityMap() {
		return roomTypeCapcityMap;
	}



	public void setRoomTypeCapcityMap(Map<String, Integer> roomTypeCapcityMap) {
		this.roomTypeCapcityMap = roomTypeCapcityMap;
	}



	public String getRatePlan() {
		return ratePlan;
	}



	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}

	@Override
	public String toString() {
		return "ChannelManagerAvailabilityDTO [propertyDetailsDto="
				+ propertyDetailsDto 
				+ ", occupancyDate=" + occupancyDate
				+ ", rateOnChannelManager=" + rateOnChannelManager
				+ ", ratePlan=" + ratePlan + ", roomTypeCapcityMap="
				+ roomTypeCapcityMap + "]";
	}
}
