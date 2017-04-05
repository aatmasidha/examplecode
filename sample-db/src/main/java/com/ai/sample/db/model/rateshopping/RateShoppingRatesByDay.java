package com.ai.sample.db.model.rateshopping;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;
import com.ai.sample.db.model.property.mapping.RateShoppingPropertyDetailsMapping;

@Entity
@Table(name="rate_shopping_rates_by_day",  uniqueConstraints=
@UniqueConstraint(columnNames={"rateshoppingpropertydetailsid","checkindate","roomtypedetailsid", "netRate", "ratecodetypeid"}))
public class RateShoppingRatesByDay {
	private int id;
	private RateShoppingPropertyDetailsMapping rateShoppingPropertyDetailsID;
	private double netRate;
	private double onsiteRate;
	private Date checkinDate;
	private Date checkoutDate;
	private boolean isPromotional;
	private boolean isClosed;
	private double discount;
	private RateShoppingRateCodeType rateCodeType;
	private String rateTypeDescription;
	private PropertyRoomTypeMapping roomTypeDetails;
	private Date updatedOn;
	
	public RateShoppingRatesByDay() {
		super();
	}

	
	public RateShoppingRatesByDay(
			RateShoppingPropertyDetailsMapping rateShoppingPropertyDetailsID,
			double netRate, double onsiteRate, Date checkinDate,
			Date checkoutDate, boolean isPromotional, boolean isClosed, double discount,
			RateShoppingRateCodeType rateCodeType, String rateTypeDescription, PropertyRoomTypeMapping roomTypeDetails, Date updatedOn) {
		super();
		this.rateShoppingPropertyDetailsID = rateShoppingPropertyDetailsID;
		this.netRate = netRate;
		this.onsiteRate = onsiteRate;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.isPromotional = isPromotional;
		this.isClosed = isClosed;
		this.discount = discount;
		this.rateCodeType = rateCodeType;
		this.roomTypeDetails = roomTypeDetails;
		this.updatedOn = updatedOn;
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

	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name = "rateshoppingpropertydetailsid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public RateShoppingPropertyDetailsMapping getRateShoppingPropertyDetailsID() {
		return rateShoppingPropertyDetailsID;
	}

	public void setRateShoppingPropertyDetailsID(
			RateShoppingPropertyDetailsMapping rateShoppingPropertyDetailsID) {
		this.rateShoppingPropertyDetailsID = rateShoppingPropertyDetailsID;
	}

	public double getNetRate() {
		return netRate;
	}

	public void setNetRate(double netRate) {
		this.netRate = netRate;
	}

	public double getOnsiteRate() {
		return onsiteRate;
	}

	public void setOnsiteRate(double onsiteRate) {
		this.onsiteRate = onsiteRate;
	}

	public Date getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public boolean isPromotional() {
		return isPromotional;
	}

	public void setPromotional(boolean isPromotional) {
		this.isPromotional = isPromotional;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}


	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "ratecodetypeid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public RateShoppingRateCodeType getRateCodeType() {
		return rateCodeType;
	}

	public void setRateCodeType(RateShoppingRateCodeType rateCodeType) {
		this.rateCodeType = rateCodeType;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "roomtypedetailsid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public PropertyRoomTypeMapping getRoomTypeDetails() {
		return roomTypeDetails;
	}

	public void setRoomTypeDetails(PropertyRoomTypeMapping roomTypeDetails) {
		this.roomTypeDetails = roomTypeDetails;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	
	
	public String getRateTypeDescription() {
		return rateTypeDescription;
	}


	public void setRateTypeDescription(String rateTypeDescription) {
		this.rateTypeDescription = rateTypeDescription;
	}


	@Override
	public String toString() {
		return "RateShoppingRatesByDay [id=" + id
				+ ", rateShoppingPropertyDetailsID="
				+ rateShoppingPropertyDetailsID + ", netRate=" + netRate
				+ ", onsiteRate=" + onsiteRate + ", checkinDate=" + checkinDate
				+ ", checkoutDate=" + checkoutDate + ", isPromotional="
				+ isPromotional + ", isClosed=" + isClosed + ", discount="
				+ discount + ", rateTypeCode=" + rateCodeType
				+ ", rateTypeDescription=" + rateTypeDescription
				+ ", roomTypeDetails=" + roomTypeDetails + ", updatedOn="
				+ updatedOn + "]";
	}
}
