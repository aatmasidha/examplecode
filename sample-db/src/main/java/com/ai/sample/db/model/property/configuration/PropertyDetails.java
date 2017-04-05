package com.ai.sample.db.model.property.configuration;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ai.sample.db.model.configuration.City;
import com.ai.sample.db.model.configuration.Country;
import com.ai.sample.db.model.configuration.Currency;
import com.ai.sample.db.model.configuration.PropertyStatusMaster;
import com.ai.sample.db.model.configuration.State;

@Entity
@Table(name="property_details",  uniqueConstraints=
@UniqueConstraint(columnNames={"name","cityid","address"}))
public class PropertyDetails {
	private int id;
	private String name;
	private String propertyUID;
	private HotelChainDetails hotelChainDetails;
	private String address;
/*	private Country country;
	private State state;*/
	private City city;
	private String pinCode;
	private int capcity; 
	private PropertyStatusMaster propertyStatus;
	private Currency hotelCurrency;
	private double longitude = 0;
	private double latitude = 0;
	private Date createDate;
	private Date lastUpdateDate;
	
	public PropertyDetails() {
		super();
	}

	public PropertyDetails( String name, String propertyUID,
			HotelChainDetails hotelChainDetails, String address, Country country,
			State state, City city, String pinCode, int capcity,
			PropertyStatusMaster propertyStatus, Currency hotelCurrency,
			Date lastUpdateDate, double longitude, double latitude) {
		super();
		this.name = name;
		this.propertyUID = propertyUID;
		this.hotelChainDetails = hotelChainDetails;
		this.address = address;
	/*	this.country = country;
		this.state = state;*/
		this.city = city;
		this.pinCode = pinCode;
		this.capcity = capcity;
		this.propertyStatus = propertyStatus;
		this.hotelCurrency = hotelCurrency;
		this.lastUpdateDate = lastUpdateDate;
		this.createDate = new Date();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name =  "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name =  "propertyuid", nullable = false, unique=true)
	public String getPropertyUID() {
		return propertyUID;
	}

	public void setPropertyUID(String propertyUID) {
		this.propertyUID = propertyUID;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name =  "hotelchainid", nullable = true)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public HotelChainDetails getHotelChainDetails() {
		return hotelChainDetails;
	}

	public void setHotelChainDetails(HotelChainDetails hotelChainID) {
		this.hotelChainDetails = hotelChainID;
	}

	@Column(name =  "address",  length = 3000)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name =  "cityid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Column(name =  "pincode",  length = 15)
	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	@Column(name =  "capacity")
	public int getCapcity() {
		return capcity;
	}

	public void setCapcity(int capcity) {
		this.capcity = capcity;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name =  "propertystatusid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public PropertyStatusMaster getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyStatus(PropertyStatusMaster propertyStatus) {
		this.propertyStatus = propertyStatus;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JoinColumn(name =  "hotelcurrencyid")
	public Currency getHotelCurrency() {
		return hotelCurrency;
	}

	public void setHotelCurrency(Currency hotelCurrency) {
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
		result = prime * result + ((propertyUID == null) ? 0 : propertyUID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PropertyDetails))
			return false;
		PropertyDetails other = (PropertyDetails) obj;
		if (id != other.id)
			return false;
		if (propertyUID == null) {
			if (other.propertyUID != null)
				return false;
		} else if (!propertyUID.equals(other.propertyUID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropertyDetails [id=" + id + ", name=" + name
				+ ", propertyUID=" + propertyUID + ", address=" + address + /*", country="
				+ country.toString() + ", state=" + state.toString() + */", city=" + city.toString()
				+ ", pinCode=" + pinCode + ", capcity=" + capcity
				+ ", propertyStatus=" + propertyStatus.toString() + ", hotelCurrency="
				+ hotelCurrency.toString() + ", longitude=" + longitude + ", latitude="
				+ latitude + ", createDate=" + createDate + ", lastUpdateDate="
				+ lastUpdateDate + "]";
	}

}
