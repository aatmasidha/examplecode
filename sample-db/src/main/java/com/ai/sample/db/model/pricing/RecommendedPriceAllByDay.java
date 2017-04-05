package com.ai.sample.db.model.pricing;

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

import com.ai.sample.db.model.configuration.RoomTypeMaster;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Entity
@Table(name="recommended_price_all_by_day",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertydetailsid", "checkindate", "roomtypemasterid"}))

public class RecommendedPriceAllByDay {
	private int id;
	private PropertyDetails propertyDetails;
	private Date checkindate;
	private RoomTypeMaster roomTypeMaster;
	private float priceRemainingCapacity;
	private float priceARI;
	private float priceMPI;
	private float pricePQM;
	private Date updateDate;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "propertydetailsid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public PropertyDetails getPropertyDetails() {
		return propertyDetails;
	}

	public void setPropertyDetails(PropertyDetails propertyDetails) {
		this.propertyDetails = propertyDetails;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "roomtypemasterid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public RoomTypeMaster getRoomTypeMaster() {
		return roomTypeMaster;
	}

	public Date getCheckindate() {
		return checkindate;
	}

	public void setCheckindate(Date checkindate) {
		this.checkindate = checkindate;
	}

	public float getPriceRemainingCapacity() {
		return priceRemainingCapacity;
	}

	public void setPriceRemainingCapacity(float priceRemainingCapacity) {
		this.priceRemainingCapacity = priceRemainingCapacity;
	}

	public float getPriceARI() {
		return priceARI;
	}

	public void setPriceARI(float priceARI) {
		this.priceARI = priceARI;
	}

	public float getPriceMPI() {
		return priceMPI;
	}

	public void setPriceMPI(float priceMPI) {
		this.priceMPI = priceMPI;
	}

	public float getPricePQM() {
		return pricePQM;
	}

	public void setPricePQM(float pricePQM) {
		this.pricePQM = pricePQM;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setRoomTypeMaster(RoomTypeMaster roomTypeMaster) {
		this.roomTypeMaster = roomTypeMaster;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((checkindate == null) ? 0 : checkindate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RecommendedPriceAllByDay))
			return false;
		RecommendedPriceAllByDay other = (RecommendedPriceAllByDay) obj;
		if (id != other.id)
			return false;
		if (checkindate == null) {
			if (other.checkindate != null)
				return false;
		} else if (!checkindate.equals(other.checkindate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RecommendedPriceAllByDay [id=" + id + ", propertyDetails="
				+ propertyDetails + ", checkindate=" + checkindate
				+ ", roomTypeMaster=" + roomTypeMaster
				+ ", priceRemainingCapacity=" + priceRemainingCapacity
				+ ", priceARI=" + priceARI + ", priceMPI=" + priceMPI
				+ ", pricePQM=" + pricePQM + ", updateDate=" + updateDate + "]";
	}
}
