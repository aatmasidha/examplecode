package com.ai.sample.integration.rateshopping.DTO;

import com.google.gson.annotations.SerializedName;

public class PropertyRateDetailsDTO {
	
	@SerializedName("hotelcode")
	String hotelCode;
	@SerializedName("websitecode")
	String otaCode;
	@SerializedName("dtcollected")
	String rateReceivedDate;
	@SerializedName("ratedate")
	String rateDefinedFor;
	@SerializedName("los")
	String lengthOfStay;
	@SerializedName("guests")
	String numGuests;
	@SerializedName("roomtype")
	String roomType;
	
	@SerializedName("onsiterate")
	String onSiteRate;
	
	@SerializedName("checkin")
	String checkinDate;
	
	@SerializedName("checkout")
	String checkoutDate;

	@SerializedName("netrate")
	String netRate;
	
	@SerializedName("ispromo")
	String isPromotional;
	
	double discount;
	
	@SerializedName("ratetype")
	String rateTypeDescription;
	
	@SerializedName("conditionscode")
	String rateTypeCode;
	
	
	@SerializedName("closed")
	String closed;
	
	
	String currency;	
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

	public String getOnSiteRate() {
		return onSiteRate;
	}

	public void setOnSiteRate(String onSiteRate) {
		this.onSiteRate = onSiteRate;
	}

	public String getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public String getNetRate() {
		return netRate;
	}

	public void setNetRate(String netRate) {
		this.netRate = netRate;
	}

	public String getIsPromotional() {
		return isPromotional;
	}

	public void setIsPromotional(String isPromotional) {
		this.isPromotional = isPromotional;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getRateTypeDescription() {
		return rateTypeDescription;
	}

	public void setRateTypeDescription(String rateTypeDescription) {
		this.rateTypeDescription = rateTypeDescription;
	}


	public String getClosed() {
		return closed;
	}

	public void setClosed(String closed) {
		this.closed = closed;
	}

	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	
	public String getRateTypeCode() {
		return rateTypeCode;
	}

	public void setRateTypeCode(String rateTypeCode) {
		this.rateTypeCode = rateTypeCode;
	}

	@Override
	public String toString() {
		return "PropertyRateDetailsDTO [hotelCode=" + hotelCode + ", otaCode="
				+ otaCode + ", rateReceivedDate=" + rateReceivedDate
				+ ", rateDefinedFor=" + rateDefinedFor + ", lengthOfStay="
				+ lengthOfStay + ", numGuests=" + numGuests + ", roomType="
				+ roomType + ", onSiteRate=" + onSiteRate + ", checkinDate="
				+ checkinDate + ", checkoutDate=" + checkoutDate + ", netRate="
				+ netRate + ", isPromotional=" + isPromotional + ", discount="
				+ discount + ", rateTypeDescription=" + rateTypeDescription
				+ ", rateypecode=" + rateTypeCode + ", closed=" + closed
				+ ", currency=" + currency + "]";
	}
}
