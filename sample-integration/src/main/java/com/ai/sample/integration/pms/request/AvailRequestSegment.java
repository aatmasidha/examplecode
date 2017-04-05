package com.ai.sample.integration.pms.request;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;


public class AvailRequestSegment {
	Date startDate;
	Date endDate;
		
	public AvailRequestSegment() {
		super();
	}

	public AvailRequestSegment(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	@XmlElement(name = "start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@XmlElement(name = "end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "AvailRequestSegment [startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}	
}
