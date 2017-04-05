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
@Table(name="recommended_price_by_day",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertydetailsid", "checkindate", "roomtypemasterid"}))

public class RecommendedPriceByDay {
	private int id;
	private PropertyDetails propertyDetails;
	private RoomTypeMaster roomTypeMaster;
	private Date checkindate;
	private float finalRecommendedRate;
	private float systemDefinedRate;
	private boolean isOverwritten;
	
//	TODO here user has to set value as RCP, ARI, PQM, 
//	MPI and user(if value is over written)
	private String overrideType;
	private Date updatedDate;
	

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

	public void setRoomTypeMaster(RoomTypeMaster roomTypeMaster) {
		this.roomTypeMaster = roomTypeMaster;
	}

	public Date getCheckindate() {
		return checkindate;
	}


	public void setCheckindate(Date checkindate) {
		this.checkindate = checkindate;
	}

	public float getFinalRecommendedRate() {
		return finalRecommendedRate;
	}

	public void setFinalRecommendedRate(float finalRecommendedRate) {
		this.finalRecommendedRate = finalRecommendedRate;
	}

	public float getSystemDefinedRate() {
		return systemDefinedRate;
	}

	public void setSystemDefinedRate(float systemDefinedRate) {
		this.systemDefinedRate = systemDefinedRate;
	}

	public boolean isOverwritten() {
		return isOverwritten;
	}

	public void setOverwritten(boolean isOverwritten) {
		this.isOverwritten = isOverwritten;
	}

	
	public String getOverrideType() {
		return overrideType;
	}

	public void setOverrideType(String overrideType) {
		this.overrideType = overrideType;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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
		if (!(obj instanceof RecommendedPriceByDay))
			return false;
		RecommendedPriceByDay other = (RecommendedPriceByDay) obj;
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
		return "RecommendedPriceByDay [id=" + id + ", propertyDetails="
				+ propertyDetails + ", roomTypeMaster=" + roomTypeMaster
				+ ", checkindate=" + checkindate + ", finalRecommendedRate="
				+ finalRecommendedRate + ", systemDefinedRate="
				+ systemDefinedRate + ", isOverwritten=" + isOverwritten
				+ ", updatedDate=" + updatedDate + "]";
	}
}
