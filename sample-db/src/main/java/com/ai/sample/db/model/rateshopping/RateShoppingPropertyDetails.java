package com.ai.sample.db.model.rateshopping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.ai.sample.db.type.MyJson;
import com.ai.sample.db.type.StringJsonUserType;

@Entity
@Table(name="rateshopping_property_details",  uniqueConstraints=
@UniqueConstraint(columnNames={"name","propertyCode"}))

@TypeDefs({ @TypeDef(name = "CustomJsonObject", typeClass = StringJsonUserType.class) })

public class RateShoppingPropertyDetails {
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
	
	@Type(type = "CustomJsonObject" )
	@Column(name = "AdditionalDetails")
	public MyJson additionalDetails;
	private Date creationDate;
	private Date lastUpdateDate;
	
	public RateShoppingPropertyDetails() {
		super();
	}

	public RateShoppingPropertyDetails(String name, String propertyCode,
			String hotelChainName, String address, String country,
			String state, String city, String pinCode, int capcity,
			String ranking, String propertyStatus, String hotelCurrency,
			double longitude, double latitude, Date creationDate, Date lastUpdateDate) {
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
		this.ranking = ranking;
		this.propertyStatus = propertyStatus;
		this.hotelCurrency = hotelCurrency;
		this.longitude = longitude;
		this.latitude = latitude;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public int getId() {
		return id;
	}

	public String getRanking() {
		return ranking;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
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

	public void setPropertyCode(String hotelCode) {
		this.propertyCode = hotelCode;
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

	@Type(type = "CustomJsonObject")
	@Column(name = "AdditionalDetails", nullable = true)
	public MyJson getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(MyJson additionalDetails) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((propertyCode == null) ? 0 : propertyCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RateShoppingPropertyDetails))
			return false;
		RateShoppingPropertyDetails other = (RateShoppingPropertyDetails) obj;
		if (id != other.id)
			return false;
		if (propertyCode == null) {
			if (other.propertyCode != null)
				return false;
		} else if (!propertyCode.equals(other.propertyCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RateShoppingPropertyDetails [id=" + id + ", name=" + name
				+ ", hotelCode=" + propertyCode + ", hotelChainName="
				+ hotelChainName + ", address=" + address + ", country="
				+ country + ", state=" + state + ", city=" + city
				+ ", pinCode=" + pinCode + ", capcity=" + capcity
				+ ", ranking=" + ranking + ", propertyStatus=" + propertyStatus
				+ ", hotelCurrency=" + hotelCurrency + ", longitude="
				+ longitude + ", latitude=" + latitude + ", creationDate="
				+ creationDate + ", lastUpdateDate=" + lastUpdateDate + "]";
	}

}
