package com.ai.sample.integration.pms.response.availability;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "room")
public class RoomTypeDateAvailabilityResponse {
	
	DateAvailabilityListResponse dateAvailabilityListResponse;
	String roomTypeCode;
	String roomTypeName;
	
	
	@XmlElement(name = "Dates")	
	public DateAvailabilityListResponse getDateAvailabilityListResponse() {
		return dateAvailabilityListResponse;
	}

	public void setDateAvailabilityListResponse(
			DateAvailabilityListResponse dateAvailabilityListResponse) {
		this.dateAvailabilityListResponse = dateAvailabilityListResponse;
	}


	@XmlAttribute(name = "RoomTypeCode")
	public String getRoomTypeCode() {
		return roomTypeCode;
	}

	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}

	@XmlAttribute(name = "RoomTypeName")
	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	@Override
	public String toString() {
		return "RoomTypeDateAvailabilityResponse [dateAvailabilityListResponse="
				+ dateAvailabilityListResponse
				+ ", roomTypeCode="
				+ roomTypeCode + ", roomTypeName=" + roomTypeName + "]";
	}
	
}
