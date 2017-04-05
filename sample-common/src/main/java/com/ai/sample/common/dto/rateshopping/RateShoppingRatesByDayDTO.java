package com.ai.sample.common.dto.rateshopping;

import java.util.Date;

public class RateShoppingRatesByDayDTO {
	int id;
	String hotelCode;
	String otaCode;
	String rateReceivedDate;
	String rateDefinedFor;
	String lengthOfStay;
	String numGuests;
	String roomType;
	double onSiteRate;
	Date checkinDate;
	Date checkoutDate;
	double netRate;
	boolean isPromotional;
	boolean isClosed;
	double discount;
	String rateTypeCode;
	String rateTypeDescription;

	
	public RateShoppingRatesByDayDTO(String hotelCode, String otaCode,
			String rateReceivedDate, String rateDefinedFor,
			String lengthOfStay, String numGuests, String roomType,
			double onSiteRate, Date checkinDate, Date checkoutDate,
			double netRate, boolean isPromotional, boolean isClosed, double discount,
			String rateTypeCode, String rateTypeDescription) {
		super();
		this.hotelCode = hotelCode;
		this.otaCode = otaCode;
		this.rateReceivedDate = rateReceivedDate;
		this.rateDefinedFor = rateDefinedFor;
		this.lengthOfStay = lengthOfStay;
		this.numGuests = numGuests;
		this.roomType = roomType;
		this.onSiteRate = onSiteRate;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.netRate = netRate;
		this.isPromotional = isPromotional;
		this.isClosed = isClosed;
		this.discount = discount;
		this.rateTypeCode = rateTypeCode;
		this.rateTypeDescription = rateTypeDescription;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getHotelCode() {
		return hotelCode;
	}
	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}
	public String getOtaCode() {
		return otaCode;
	}
	public void setOtaCode(String otaCode) {
		this.otaCode = otaCode;
	}
	public String getRateReceivedDate() {
		return rateReceivedDate;
	}
	public void setRateReceivedDate(String rateReceivedDate) {
		this.rateReceivedDate = rateReceivedDate;
	}
	public String getRateDefinedFor() {
		return rateDefinedFor;
	}
	public void setRateDefinedFor(String rateDefinedFor) {
		this.rateDefinedFor = rateDefinedFor;
	}
	public String getLengthOfStay() {
		return lengthOfStay;
	}
	public void setLengthOfStay(String lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
	}
	public String getNumGuests() {
		return numGuests;
	}
	public void setNumGuests(String numGuests) {
		this.numGuests = numGuests;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public double getOnSiteRate() {
		return onSiteRate;
	}
	public void setOnSiteRate(double onSiteRate) {
		this.onSiteRate = onSiteRate;
	}
	public Date getCheckinDate() {
		return checkinDate;
	}
	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}
	public Date getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public double getNetRate() {
		return netRate;
	}
	public void setNetRate(double netRate) {
		this.netRate = netRate;
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
	public String getRateTypeCode() {
		return rateTypeCode;
	}
	public void setRateTypeCode(String rateTypeCode) {
		this.rateTypeCode = rateTypeCode;
	}

	
	public String getRateTypeDescription() {
		return rateTypeDescription;
	}

	public void setRateTypeDescription(String rateTypeDescription) {
		this.rateTypeDescription = rateTypeDescription;
	}

	
	@Override
	public String toString() {
		return "RateShoppingRatesByDayDTO [id=" + id + ", hotelCode="
				+ hotelCode + ", otaCode=" + otaCode + ", rateReceivedDate="
				+ rateReceivedDate + ", rateDefinedFor=" + rateDefinedFor
				+ ", lengthOfStay=" + lengthOfStay + ", numGuests=" + numGuests
				+ ", roomType=" + roomType + ", onSiteRate=" + onSiteRate
				+ ", checkinDate=" + checkinDate + ", checkoutDate="
				+ checkoutDate + ", netRate=" + netRate + ", isPromotional="
				+ isPromotional + ", isClosed=" + isClosed + ", discount="
				+ discount + ", rateTypeCode=" + rateTypeCode
				+ ", rateTypeDescription=" + rateTypeDescription + "]";
	}
}
