package com.ai.sample.integration.rateshopping.DTO;

import com.google.gson.annotations.SerializedName;

public class PropertyInformationRequestDetailsDTO {

	@SerializedName("hotelname")
	private String hotelName;
	private String country;


	private String city;
	private String state;

	private String zip;
	private String keyword;

	public PropertyInformationRequestDetailsDTO(String hotelName,
			String country, String city, String state, String zip, String keyword) {
		super();
		this.hotelName = hotelName;
		this.country = country;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.keyword = keyword;
	}

	
	public PropertyInformationRequestDetailsDTO() {
		super();
	}
	
	

	public String getHotelName() {
		return hotelName;
	}


	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	@Override
	public String toString() {
		return "PropertyInformationRequestDetailsDTO [hotelName=" + hotelName
				+ ", country=" + country + ", city=" + city + ", state="
				+ state + ", zip=" + zip + ", keyword=" + keyword + "]";
	}
}
