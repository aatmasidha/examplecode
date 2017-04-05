package com.ai.sample.common.dto.isell;

import java.util.Date;

public class ISellEventDetailsDTO {
	public Date occupancyDate;
	public String dayOfWeek;
	public String eventName;
	public String eventType;
	
	public ISellEventDetailsDTO() {
		super();
	}

	public ISellEventDetailsDTO(Date occupancyDate, String dayOfWeek,
			String eventName, String eventType) {
		super();
		this.occupancyDate = occupancyDate;
		this.dayOfWeek = dayOfWeek;
		this.eventName = eventName;
		this.eventType = eventType;
	}

	public Date getOccupancyDate() {
		return occupancyDate;
	}

	public void setOccupancyDate(Date occupancyDate) {
		this.occupancyDate = occupancyDate;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@Override
	public String toString() {
		return "ISellEventDetailsDTO [occupancyDate=" + occupancyDate
				+ ", dayOfWeek=" + dayOfWeek + ", eventName=" + eventName
				+ ", eventType=" + eventType + "]";
	}
	
}
