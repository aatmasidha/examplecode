package com.ai.sample.integration.rateshopping.DTO;

import com.google.gson.annotations.SerializedName;

public class PropertyRateRequestDTO {
	@SerializedName("hotelcode")
	String hotelCode;

	@SerializedName("websitecode")
	String otaName;
	
	String currency;
	
	@SerializedName("checkin")
	String checkInDate;
	
	@SerializedName("checkout")
	String checkOutDate;
	
	@SerializedName("guests")
	String numGuests = "1";
	
	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getOtaName() {
		return otaName;
	}

	public void setOtaName(String otaName) {
		this.otaName = otaName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getNumGuests() {
		return numGuests;
	}

	public void setNumGuests(String numGuests) {
		this.numGuests = numGuests;
	}

	@Override
	public String toString() {
		return "HotelRateRequestDTO [hotelCode=" + hotelCode + ", otaName="
				+ otaName + ", currency=" + currency + ", checkInDate="
				+ checkInDate + ", checkOutDate=" + checkOutDate
				+ ", numGuests=" + numGuests + "]";
	}
}
