package com.ai.sample.common.dto.ota;

import java.util.Date;

public class OtaRevenueSummaryDetailsDTO {
	private Date businessDate;
	private int otaSoldCount;
	private float otaTotalRevenue;
	private float otaADR;
	private int lastPickupValue;
	
	public OtaRevenueSummaryDetailsDTO(Date businessDate, int otaSoldCount,
			float otaTotalRevenue, float otaADR, int lastPickupValue) {
		super();
		this.businessDate = businessDate;
		this.otaSoldCount = otaSoldCount;
		this.otaTotalRevenue = otaTotalRevenue;
		this.otaADR = otaADR;
		this.lastPickupValue = lastPickupValue;
	}
	
	public Date getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	public int getOtaSoldCount() {
		return otaSoldCount;
	}
	public void setOtaSoldCount(int otaSoldCount) {
		this.otaSoldCount = otaSoldCount;
	}
	public float getOtaTotalRevenue() {
		return otaTotalRevenue;
	}
	public void setOtaTotalRevenue(float otaTotalRevenue) {
		this.otaTotalRevenue = otaTotalRevenue;
	}
	public float getOtaADR() {
		return otaADR;
	}
	public void setOtaADR(float otaADR) {
		this.otaADR = otaADR;
	}

	
	public int getLastPickupValue() {
		return lastPickupValue;
	}

	public void setLastPickupValue(int lastPickupValue) {
		this.lastPickupValue = lastPickupValue;
	}

	@Override
	public String toString() {
		return "OtaRevenueSummaryDetailsDTO [businessDate=" + businessDate
				+ ", otaSoldCount=" + otaSoldCount + ", otaTotalRevenue="
				+ otaTotalRevenue + ", otaADR=" + otaADR + ", lastPickupValue="
				+ lastPickupValue + "]";
	}
	
	
}
