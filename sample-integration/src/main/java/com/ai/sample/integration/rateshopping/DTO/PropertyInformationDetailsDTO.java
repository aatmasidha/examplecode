package com.ai.sample.integration.rateshopping.DTO;


import com.google.gson.annotations.SerializedName;

public class PropertyInformationDetailsDTO {
	@SerializedName("HotelCode")
	private String hotelCode;
	
	@SerializedName("HotelName")
	private String hotelName;
	
	@SerializedName("HotelGroup")
	private String hotelGroup;
	
	@SerializedName("Address")
	private String address;
	
	@SerializedName("City")
	private String city;
	
	@SerializedName("State")
	private String state;
	
	@SerializedName("Country")
	private String country;

//	private String rating;
	@SerializedName("Zip")
	private String zip;
	private String status;
	
/*	@SerializedName("currencyCode")
	private String currenyCode;*/
	
	@SerializedName("Lat")
	private String latitude;
	
	@SerializedName("Lng")
	private String longitude;

	public PropertyInformationDetailsDTO(String hotelCode, String hotelName,
			String hotelGroup, String address, String city, String state,
			String country, String zip, String latitude, String longitude) {
		super();
		this.hotelCode = hotelCode;
		this.hotelName = hotelName;
		this.hotelGroup = hotelGroup;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zip = zip;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public PropertyInformationDetailsDTO() {
		super();
	}

	
	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelGroup() {
		return hotelGroup;
	}

	public void setHotelGroup(String hotelGroup) {
		this.hotelGroup = hotelGroup;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/*public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
*/
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

/*	public String getCurrenyCode() {
		return currenyCode;
	}

	public void setCurrenyCode(String currenyCode) {
		this.currenyCode = currenyCode;
	}*/

	@Override
	public String toString() {
		return "PropertyInformationDetailsDTO [hotelCode=" + hotelCode
				+ ", hotelName=" + hotelName + ", hotelGroup=" + hotelGroup
				+ ", address=" + address + ", city=" + city + ", state="
				+ state + ", country=" + country + ", zip=" + zip + ", status="
				+ status + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}
}
