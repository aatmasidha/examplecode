package com.ai.sample.integration.pms.request;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AvailRequestSegments")
public class AvailabilityByDayRequest {

	String userName;
	String password;
	String hotelID;
	String version = "1.0";
	
	
	List<AvailRequestSegment> availableRequestSegment;
	
	public AvailabilityByDayRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AvailabilityByDayRequest(String userName, String password, String hotelID, String version) {
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
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@XmlElement(name = "AvailRequestSegment")
	public List<AvailRequestSegment> getAvailableRequestSegment() {
		return availableRequestSegment;
	}

	public void setAvailableRequestSegment(
			List<AvailRequestSegment> availableRequestSegment) {
		this.availableRequestSegment = availableRequestSegment;
	}

	@Override
	public String toString() {
		return "AvailabilityByDayRequest [userName=" + userName + ", password="
				+ password + ", hotelID=" + hotelID + ", version=" + version
				+ ", availableRequestSegment=" + availableRequestSegment + "]";
	}
}
