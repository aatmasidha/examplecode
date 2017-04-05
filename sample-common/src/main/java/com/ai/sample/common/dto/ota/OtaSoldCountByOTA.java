package com.ai.sample.common.dto.ota;

import java.util.Date;

public class OtaSoldCountByOTA {
	private Date businessDate;
	private String OtaName;
	private int otaSoldCount;
	
	public OtaSoldCountByOTA(Date businessDate, String otaName, int otaSoldCount) {
		super();
		this.businessDate = businessDate;
		OtaName = otaName;
		this.otaSoldCount = otaSoldCount;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public String getOtaName() {
		return OtaName;
	}

	public void setOtaName(String otaName) {
		OtaName = otaName;
	}

	public int getOtaSoldCount() {
		return otaSoldCount;
	}

	public void setOtaSoldCount(int otaSoldCount) {
		this.otaSoldCount = otaSoldCount;
	}

	@Override
	public String toString() {
		return "OtaSoldCountByOTA [businessDate=" + businessDate + ", OtaName="
				+ OtaName + ", otaSoldCount=" + otaSoldCount + "]";
	}
}
