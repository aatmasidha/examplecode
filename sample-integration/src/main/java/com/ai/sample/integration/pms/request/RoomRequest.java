package com.ai.sample.integration.pms.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "roomrequest")
public class RoomRequest {

	String userName;
	String password;
	String hotelID;

	
	
	public RoomRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoomRequest(String userName, String password, String hotelID) {
		super();
		this.userName = userName;
		this.password = password;
		this.hotelID = hotelID;
	}

	@XmlElement(name = "username")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@XmlElement(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement(name = "hotel_id")
	public String getHotelID() {
		return hotelID;
	}

	public void setHotelID(String hotelID) {
		this.hotelID = hotelID;
	}

	@Override
	public String toString() {
		return "RoomRequest [userName=" + userName + ", password=" + password
				+ ", hotelID=" + hotelID + "]";
	}
}
