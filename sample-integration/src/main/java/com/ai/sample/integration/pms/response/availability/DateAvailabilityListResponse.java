package com.ai.sample.integration.pms.response.availability;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;


//@XmlAccessorType(XmlAccessType.NONE)
public class DateAvailabilityListResponse {

	List<DateAvailabilityResponse> dateAvailabilityList =  new ArrayList<DateAvailabilityResponse>();

	@XmlElement(name = "Date")	
/*	@XmlElementWrapper(name = "dateAvailabilityList")*/
	public List<DateAvailabilityResponse> getDateAvailabilityList() {
		return dateAvailabilityList;
	}

	public void setDateAvailabilityList(
			List<DateAvailabilityResponse> dateAvailabilityList) {
		this.dateAvailabilityList = dateAvailabilityList;
	}

	@Override
	public String toString() {
		return "DateAvailabilityListResponse [dateAvailabilityList="
				+ dateAvailabilityList + "]";
	}
}
