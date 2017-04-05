package com.ai.sample.transdata.utilities;

import java.util.Date;

public class InputExcelData {
	private String bookingID;
	private Date creationDate;
	private Date arrivalDate;
	private Date departureDate;
	private String channelName;
	private String bookingStatus;
	private String currency;
	private int numGuests;
	private float totalAmount;
	
	public String getBookingID() {
		return bookingID;
	}
	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arriavalDate) {
		this.arrivalDate = arriavalDate;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getNumGuests() {
		return numGuests;
	}
	public void setNumGuests(int numGuests) {
		this.numGuests = numGuests;
	}
	
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Override
	public String toString() {
		return "InputExcelData [bookingID=" + bookingID + ", creationDate="
				+ creationDate + ", arrivalDate=" + arrivalDate
				+ ", departureDate=" + departureDate + ", channelName="
				+ channelName + ", bookingStatus=" + bookingStatus
				+ ", currency=" + currency + ", numGuests=" + numGuests
				+ ", totalAmount=" + totalAmount + "]";
	}	
}
