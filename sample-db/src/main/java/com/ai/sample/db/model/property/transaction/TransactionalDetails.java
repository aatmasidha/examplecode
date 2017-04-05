package com.ai.sample.db.model.property.transaction;

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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.ai.sample.db.model.configuration.BookingStatusMaster;
import com.ai.sample.db.model.configuration.City;
import com.ai.sample.db.model.configuration.Country;
import com.ai.sample.db.model.configuration.Currency;
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.type.MyJson;
import com.ai.sample.db.type.StringJsonUserType;

@Entity
@Table(name="transactional_details",  uniqueConstraints=
@UniqueConstraint(columnNames={"bookingRefID", "PropertyId", "OtaId"}))

@TypeDefs({ @TypeDef(name = "CustomJsonObject", typeClass = StringJsonUserType.class) })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TransactionalDetails {
	private int id;
	private PropertyDetails propertyDetails;
	private String bookingRefID;

	private OnlineTravelAgentDetails otaDetails;
	private Date otaUpdateDate;
	private Date arrivalDate;
	private Date departureDate;
	private Date modificationDate;
	private int lengthOfStay;
	private BookingStatusMaster bookingStatus;
	private Currency transactionCurrency;
	private float totalAmount;
	private String address;
	private Country arrivalCountry;
	private City arrivalCity;
	private int numGuests;
	private int numRooms;
	
	@Type(type = "CustomJsonObject" )
	@Column(name = "AdditionalDetails")
	public MyJson additionalDetails;
	
	public TransactionalDetails() {
		super();
	}

	public TransactionalDetails(int id, PropertyDetails propertyDetails,
			String bookingRefID, OnlineTravelAgentDetails otaDetails,
			Date otaUpdateDate, Date arrivalDate, Date departureDate,
			Date modificationDate, int lengthOfStay,
			BookingStatusMaster bookingStatus, Currency transactionCurrency,
			float totalAmount, String address, Country arrivalCountry,
			City arrivalCity, int numGuests, int numRooms, MyJson additionalDetails) {
		super();
		this.id = id;
		this.propertyDetails = propertyDetails;
		this.bookingRefID = bookingRefID;
		this.otaDetails = otaDetails;
		this.otaUpdateDate = otaUpdateDate;
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
		this.modificationDate = modificationDate;
		this.lengthOfStay = lengthOfStay;
		this.bookingStatus = bookingStatus;
		this.transactionCurrency = transactionCurrency;
		this.totalAmount = totalAmount;
		this.address = address;
		this.arrivalCountry = arrivalCountry;
		this.arrivalCity = arrivalCity;
		this.numGuests = numGuests;
		this.numRooms = numRooms;
		this.additionalDetails = additionalDetails;
	}

	public TransactionalDetails(PropertyDetails propertyDetails,
			OnlineTravelAgentDetails onlineTravelAgentDetails,
			Date otaUpdateDate, Date arrivalDate, Date departureDate,
			int lengthOfStay, BookingStatusMaster bookingStatus, Currency currency,
			float totalAmount, int numGuests, String bookingRefID,int numRooms) {
		super();
		this.propertyDetails = propertyDetails;
		this.otaDetails = onlineTravelAgentDetails;
		this.otaUpdateDate = otaUpdateDate;
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
		this.lengthOfStay = lengthOfStay;
		this.bookingStatus = bookingStatus;
		this.transactionCurrency = currency;
		this.totalAmount = totalAmount;
		this.numGuests = numGuests;
		this.bookingRefID = bookingRefID;
		this.numRooms = numRooms;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "PropertyId", nullable = false)
	public PropertyDetails getPropertyDetails() {
		return propertyDetails;
	}

	public void setPropertyDetails(PropertyDetails propertyDetails) {
		this.propertyDetails = propertyDetails;
	}
	
	public String getBookingRefID() {
		return bookingRefID;
	}

	public void setBookingRefID(String bookingRefID) {
		this.bookingRefID = bookingRefID;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "OtaId", nullable = false)
	public OnlineTravelAgentDetails getOtaDetails() {
		return otaDetails;
	}

	public void setOtaDetails(OnlineTravelAgentDetails otaDetails) {
		this.otaDetails = otaDetails;
	}

	public Date getOtaUpdateDate() {
		return otaUpdateDate;
	}

	public void setOtaUpdateDate(Date otaUpdateDate) {
		this.otaUpdateDate = otaUpdateDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(final Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(final Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public int getLengthOfStay() {
		return lengthOfStay;
	}

	public void setLengthOfStay(int lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BookingStatusId", nullable = false)
	public BookingStatusMaster getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatusMaster bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	@Type(type = "CustomJsonObject")
	@Column(name = "AdditionalDetails", nullable = true)
	public MyJson getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(MyJson additionalDetails) {
		this.additionalDetails = additionalDetails;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TransactionCurrencyId", nullable = true)
	public Currency getTransactionCurrency() {
		return transactionCurrency;
	}

	public void setTransactionCurrency(Currency transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Column(name =  "Address", nullable = true, length = 3000)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ArrivalCountryId", nullable = true)
	public Country getArrivalCountry() {
		return arrivalCountry;
	}

	public void setArrivalCountry(Country arrivalCountry) {
		this.arrivalCountry = arrivalCountry;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ArrivalCityId", nullable = true)
	public City getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(City arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	@Column(nullable=true)
	public int getNumGuests() {
		return numGuests;
	}

	public void setNumGuests(int numGuests) {
		this.numGuests = numGuests;
	}
	
	public int getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((bookingRefID == null) ? 0 : bookingRefID.hashCode());
		return result;
	}

	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TransactionalDetails))
			return false;
		TransactionalDetails other = (TransactionalDetails) obj;
		if (id != other.id)
			return false;
		if (bookingRefID == null) {
			if (other.bookingRefID != null)
				return false;
		} else if (!bookingRefID.equals(other.bookingRefID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TransactionalDetails [id=" + id + ", propertyDetails="
				+ propertyDetails + ", bookingRefID=" + bookingRefID
				+ ", otaDetails=" + otaDetails + ", otaUpdateDate=" + otaUpdateDate
				+ ", arrivalDate=" + arrivalDate + ", departureDate="
				+ departureDate + ", modificationDate=" + modificationDate
				+ ", lengthOfStay=" + lengthOfStay + ", bookingStatus="
				+ bookingStatus + ", transactionCurrency="
				+ transactionCurrency + ", totalAmount=" + totalAmount
				+ ", address=" + address + ", arrivalCountry=" + arrivalCountry
				+ ", arrivalCity=" + arrivalCity + ", numGuests=" + numGuests
				+ ", numRooms=" + numRooms + ", additionalDetails="
				+ additionalDetails + "]";
	}
}
