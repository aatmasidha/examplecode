package com.ai.sample.integration.pms.response.availability;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class RoomTypeDateAvailabilityListResponse {

	List<RoomTypeDateAvailabilityResponse> roomTypeDateAvailabilityList =  new ArrayList<RoomTypeDateAvailabilityResponse>();

	@XmlElement(name = "room")	
	public List<RoomTypeDateAvailabilityResponse> getDateAvailabilityList() {
		return roomTypeDateAvailabilityList;
	}

	public void setDateAvailabilityList(
			List<RoomTypeDateAvailabilityResponse> roomTypeDateAvailabilityList) {
		this.roomTypeDateAvailabilityList = roomTypeDateAvailabilityList;
	}

	@Override
	public String toString() {
		return "RoomTypeDateAvailabilityListResponse [roomTypeDateAvailabilityList="
				+ roomTypeDateAvailabilityList + "]";
	}
}
