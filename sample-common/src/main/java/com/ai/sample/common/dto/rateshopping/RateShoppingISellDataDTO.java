package com.ai.sample.common.dto.rateshopping;

import java.util.Date;

public class RateShoppingISellDataDTO {
	
	String rateShophotelCode;
	String rateShopOtaCode;
	float netRate;
	float onSiteRate;
	Date checkinDate;
	boolean isPromotional;
	boolean isClosed;
	double discount;
	
	
	public RateShoppingISellDataDTO() {
		super();
	}


	public RateShoppingISellDataDTO(String rateShophotelCode,
			String rateShopOtaCode, float netRate, float onSiteRate,
			Date checkinDate, boolean isPromotional, boolean isClosed,
			double discount) {
		super();
		this.rateShophotelCode = rateShophotelCode;
		this.rateShopOtaCode = rateShopOtaCode;
		this.netRate = netRate;
		this.onSiteRate = onSiteRate;
		this.checkinDate = checkinDate;
		this.isPromotional = isPromotional;
		this.isClosed = isClosed;
		this.discount = discount;
	}


	public String getRateShophotelCode() {
		return rateShophotelCode;
	}


	public void setRateShophotelCode(String rateShophotelCode) {
		this.rateShophotelCode = rateShophotelCode;
	}


	public String getRateShopOtaCode() {
		return rateShopOtaCode;
	}


	public void setRateShopOtaCode(String rateShopOtaCode) {
		this.rateShopOtaCode = rateShopOtaCode;
	}


	public float getNetRate() {
		return netRate;
	}


	public void setNetRate(float netRate) {
		this.netRate = netRate;
	}


	public float getOnSiteRate() {
		return onSiteRate;
	}


	public void setOnSiteRate(float onSiteRate) {
		this.onSiteRate = onSiteRate;
	}


	public Date getCheckinDate() {
		return checkinDate;
	}


	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}


	public boolean isPromotional() {
		return isPromotional;
	}


	public void setPromotional(boolean isPromotional) {
		this.isPromotional = isPromotional;
	}


	public boolean isClosed() {
		return isClosed;
	}


	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}


	public double getDiscount() {
		return discount;
	}


	public void setDiscount(double discount) {
		this.discount = discount;
	}


	@Override
	public String toString() {
		return "RateShoppingISellDataDTO [rateShophotelCode="
				+ rateShophotelCode + ", rateShopOtaCode=" + rateShopOtaCode
				+ ", netRate=" + netRate + ", onSiteRate=" + onSiteRate
				+ ", checkinDate=" + checkinDate + ", isPromotional="
				+ isPromotional + ", isClosed=" + isClosed + ", discount="
				+ discount + "]";
	}
}
