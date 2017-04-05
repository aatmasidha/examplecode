package com.ai.sample.common.dto.configuration;

import java.util.Date;

public class EventConfigurationDTO {

	int id;
	String eventName;
	Date eventDate;
	String severity;
	String eventType;
	String eventCategory;
	boolean recurring = false;
	String regionName;
	
	public EventConfigurationDTO() {
		super();	
	}

	public EventConfigurationDTO(String eventName, Date eventDate,
			String severity, String eventType, String eventCategory, String regionName) {
		super();
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.severity = severity;
		this.eventType = eventType;
		this.eventCategory = eventCategory;
		this.regionName = regionName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	
	public boolean isRecurring() {
		return recurring;
	}

	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}

	
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Override
	public String toString() {
		return "EventConfigurationDTO [eventName=" + eventName + ", eventDate="
				+ eventDate + ", severity=" + severity + ", eventType="
				+ eventType + ", eventCategory=" + eventCategory
				+ ", recurring=" + recurring + ", regionName=" + regionName
				+ "]";
	}
}
