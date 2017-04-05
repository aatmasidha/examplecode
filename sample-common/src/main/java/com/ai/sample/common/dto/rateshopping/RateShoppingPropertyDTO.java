package com.ai.sample.common.dto.rateshopping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ai.sample.common.constants.EnumConstants.HotelStateConstant;
import com.ai.sample.common.dto.AdditionalInformationDTO;

public class RateShoppingPropertyDTO {

	private int id;
	private String name;
	private String propertyCode;
	private String hotelChainName;
	private String address;
	private String country;
	private String state;
	private String city;
	private String pinCode;
	private int capcity; 
	private String ranking;
	private String propertyStatus;
	private String hotelCurrency;
	private double longitude;
	private double latitude;
	
	public List<AdditionalInformationDTO> additionalDetails = new ArrayList<AdditionalInformationDTO>();
	private Date creationDate;
	private Date lastUpdateDate;
	
	
	public RateShoppingPropertyDTO(String name, String propertyCode,
			String hotelChainName, String address, String country,
			String state, String city, String pinCode, int capcity,
			/*String ranking,*/ String propertyStatus, /*String hotelCurrency,*/
			double longitude, double latitude,
			List<AdditionalInformationDTO> additionalDetails,  Date creationDate, Date lastUpdateDate) {
		super();
		this.name = name;
		this.propertyCode = propertyCode;
		this.hotelChainName = hotelChainName;
		this.address = address;
		this.country = country;
		this.state = state;
		this.city = city;
		this.pinCode = pinCode;
		this.capcity = capcity;
//		this.ranking = ranking;
		if(propertyStatus == null || propertyStatus.length() == 0)
		{
			propertyStatus =HotelStateConstant.LIVE.getsState();
		}
		this.propertyStatus = propertyStatus;
//		this.hotelCurrency = hotelCurrency;
		this.longitude = longitude;
		this.latitude = latitude;
		this.additionalDetails = additionalDetails;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
	}
	
	public RateShoppingPropertyDTO(int id, String name, String propertyCode,
			String hotelChainName, String address, String country,
			String state, String city, String pinCode, int capcity,
			/*String ranking,*/ String propertyStatus, /*String hotelCurrency,*/
			double longitude, double latitude,
			List<AdditionalInformationDTO> additionalDetails,  Date creationDate, Date lastUpdateDate) {
		super();
		this.id = id;
		this.name = name;
		this.propertyCode = propertyCode;
		this.hotelChainName = hotelChainName;
		this.address = address;
		this.country = country;
		this.state = state;
		this.city = city;
		this.pinCode = pinCode;
		this.capcity = capcity;
		this.ranking = ranking;
		this.propertyStatus = propertyStatus;
		this.hotelCurrency = hotelCurrency;
		this.longitude = longitude;
		this.latitude = latitude;
		this.additionalDetails = additionalDetails;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
	}

	public RateShoppingPropertyDTO() {
		
		super();
		if(propertyStatus == null || propertyStatus.length() == 0)
		{
			propertyStatus =HotelStateConstant.LIVE.getsState();
		}
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public int getCapcity() {
		return capcity;
	}
	public void setCapcity(int capcity) {
		this.capcity = capcity;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public String getPropertyStatus() {
		return propertyStatus;
	}
	public void setPropertyStatus(String propertyStatus) {
		if(propertyStatus == null || propertyStatus.length() == 0)
		{
			propertyStatus =HotelStateConstant.LIVE.getsState();
		}
		this.propertyStatus = propertyStatus;
	}
	public String getHotelCurrency() {
		return hotelCurrency;
	}
	public void setHotelCurrency(String hotelCurrency) {
		this.hotelCurrency = hotelCurrency;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public List<AdditionalInformationDTO> getAdditionalDetails() {
		return additionalDetails;
	}
	public void setAdditionalDetails(
			List<AdditionalInformationDTO> additionalDetails) {
		this.additionalDetails = additionalDetails;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	@Override
	public String toString() {
		return "RateShoppingPropertyDTO [id=" + id + ", name=" + name
				+ ", propertyCode=" + propertyCode + ", hotelChainName="
				+ hotelChainName + ", address=" + address + ", country="
				+ country + ", state=" + state + ", city=" + city
				+ ", pinCode=" + pinCode + ", capcity=" + capcity
				+ ", ranking=" + ranking + ", propertyStatus=" + propertyStatus
				+ ", hotelCurrency=" + hotelCurrency + ", longitude="
				+ longitude + ", latitude=" + latitude + ", additionalDetails="
				+ additionalDetails + ", creationDate=" + creationDate
				+ ", lastUpdateDate=" + lastUpdateDate + "]";
	}
}
