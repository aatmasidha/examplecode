package com.ai.sample.integration.pms.response.availability;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


//@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="AvailRequestSegments")
public class AvailabilityByDayResponse {

	
	List<RoomTypeDateAvailabilityListResponse> roomTypeDateAvailabilityList =  new ArrayList<RoomTypeDateAvailabilityListResponse>();

	
//	@XmlElementWrapper(name = "roomTypeDateAvailabilityList")
	@XmlElement(name = "Rooms")
	public List<RoomTypeDateAvailabilityListResponse> getRoomTypeDateAvailabilityList() {
		return roomTypeDateAvailabilityList;
	}

	public void setRoomTypeDateAvailabilityList(List<RoomTypeDateAvailabilityListResponse> roomTypeDateAvailabilityList) {
		this.roomTypeDateAvailabilityList = roomTypeDateAvailabilityList;
	}

	@Override
	public String toString() {
		return "AvailabilityByDayResponse [roomTypeDateAvailabilityList="
				+ roomTypeDateAvailabilityList + "]";
	}
}
