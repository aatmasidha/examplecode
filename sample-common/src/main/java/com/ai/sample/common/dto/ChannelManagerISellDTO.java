package com.ai.sample.common.dto;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class ChannelManagerISellDTO {

	private Date occupancyDate;
	private float ratePlanValue; 
	private int remainingCapacity;

	public ChannelManagerISellDTO() {
		super();
	}

	public ChannelManagerISellDTO(Date occupancyDate, float ratePlanValue, int remainingCapacity) {
		super();
		this.remainingCapacity = remainingCapacity;
		this.occupancyDate = occupancyDate;
		this.ratePlanValue = ratePlanValue;
	}

	public Date getOccupancyDate() {
		return occupancyDate;
	}

	public void setOccupancyDate(Date occupancyDate) {
		this.occupancyDate = occupancyDate;
	}

	public float getRatePlanValue() {
		return ratePlanValue;
	}

	public void setRatePlanValue(float ratePlanValue) {
		this.ratePlanValue = ratePlanValue;
	}

	public int getRemainingCapacity() {
		return remainingCapacity;
	}

	public void setRemainingCapacity(int remainingCapacity) {
		this.remainingCapacity = remainingCapacity;
	}

	@Override
	public String toString() {
		return "ChannelManagerISellDTO [occupancyDate=" + occupancyDate
				+ ", ratePlanValue=" + ratePlanValue + ", remainingCapacity="
				+ remainingCapacity + "]";
	}


}
