package com.ai.sample.common.dto;

import java.util.Date;

import com.ai.sample.common.dto.configuration.CityDTO;

public class PropertyDetailsDTO {

	private int id;
	private String name;
	private String propertyCode;
	private String hotelChainName;
	private String address;
	private CityDTO cityDto;

	private String pinCode;
	private int capacity; 
	private String propertyStatus;
	private String hotelCurrency;
	private Date updateDate;
	private double latitude;
	private double longitude;
	
	
	public PropertyDetailsDTO(int id, String name, String propertyUID,
			String hotelChainName, String address, CityDTO city, String pinCode, int capacity,
			String propertyStatus, String hotelCurrency, double latitude, 
			double longitude, Date updateDate) {
		super();
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.hotelChainName = hotelChainName;
		this.address = address;
	/*	this.country = country;
		this.state = state;*/
		this.cityDto = city;
		this.pinCode = pinCode;
		this.propertyStatus = propertyStatus;
		this.hotelCurrency = hotelCurrency;
		this.latitude = latitude;
		this.longitude = longitude;
		this.updateDate = updateDate;
	}

	public PropertyDetailsDTO(String name, String hotelChainName, String address/*, String country,
			String state*/, CityDTO city, String pinCode, int capacity,
			String propertyStatus, String hotelCurrency, Date updateDate,
			double latitude, double longitude) {
		super();
		this.name = name;
		this.hotelChainName = hotelChainName;
		this.address = address;
		/*this.country = country;
		this.state = state;*/
		this.cityDto = city;
		this.pinCode = pinCode;
		this.capacity = capacity;
		this.propertyStatus = propertyStatus;
		this.hotelCurrency = hotelCurrency;
		this.updateDate = updateDate;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public PropertyDetailsDTO(String name, 	CityDTO cityDto) {
		super();
		this.cityDto = cityDto;
		this.name = name;
	}

	public PropertyDetailsDTO() {
		super();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getPropertyCode() {
		return propertyCode;
	}
	
	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}

	public String getHotelChainName() {
		return hotelChainName;
	}
	public void setHotelChainName(String hotelChainName) {
		this.hotelChainName = hotelChainName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public CityDTO getCityDto() {
		return cityDto;
	}
	public void setCityDto(CityDTO city) {
		this.cityDto = city;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getPropertyStatus() {
		return propertyStatus;
	}
	public void setPropertyStatus(String propertyStatus) {
		this.propertyStatus = propertyStatus;
	}
	public String getHotelCurrency() {
		return hotelCurrency;
	}
	public void setHotelCurrency(String hotelCurrency) {
		this.hotelCurrency = hotelCurrency;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	@Override
	public String toString() {
		return "PropertyDetailsDTO [id=" + id + ", name=" + name
				+ ", propertyCode=" + propertyCode + ", hotelChainName="
				+ hotelChainName + ", address=" + address + ", country="
				/*+ country + ", state=" + state +*/ + ", city=" + cityDto.toString()
				+ ", pinCode=" + pinCode + ", capacity=" + capacity
				+ ", propertyStatus=" + propertyStatus + ", hotelCurrency="
				+ hotelCurrency + ", updateDate=" + updateDate + ", latitude="
				+ latitude + ", longitude=" + longitude  +"]";
		
	}
}
