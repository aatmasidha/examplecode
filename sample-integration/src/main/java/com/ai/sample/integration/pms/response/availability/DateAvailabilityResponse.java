package com.ai.sample.integration.pms.response.availability;

import javax.xml.bind.annotation.XmlAttribute;

//@XmlAccessorType(XmlAccessType.NONE)
public class DateAvailabilityResponse {
	String occupancyDate;
	String soldCount;
	
	public DateAvailabilityResponse() {
		super();
	}

	public DateAvailabilityResponse(String occupancyDate, String soldCount) {
		super();
		this.occupancyDate = occupancyDate;
		this.soldCount = soldCount;
	}

	@XmlAttribute(name="EffectiveDate")
	public String getOccupancyDate() {
		return occupancyDate;
	}

	public void setOccupancyDate(String occupancyDate) {
		this.occupancyDate = occupancyDate;
	}

	@XmlAttribute(name="Count")
	public String getSoldCount() {
		return soldCount;
	}

	public void setSoldCount(String soldCount) {
		this.soldCount = soldCount;
	}

	@Override
	public String toString() {
		return "DateAvailabilityResponse [occupancyDate=" + occupancyDate
				+ ", soldCount=" + soldCount + "]";
	}
}
