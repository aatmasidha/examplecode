package com.ai.sample.db.model.property.transaction;

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

import com.ai.sample.db.model.configuration.RoomTypeMaster;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Entity
@Table(name="channel_manager_availability_details",  uniqueConstraints=
@UniqueConstraint(columnNames={ "PropertyId", "occupancyDate", "roomTypeId"}))

public class ChannelManagerAvailabilityDetails {
	private int id;
	private PropertyDetails propertyDetails;
	private RoomTypeMaster roomTypeMaster;
	private Date occupancyDate;
	private int remainingCapacity;
	private float minRatePlanAmount;
	
	public ChannelManagerAvailabilityDetails() {
		super();
	}

	public ChannelManagerAvailabilityDetails(PropertyDetails propertyDetails,
			RoomTypeMaster roomTypeMaster, Date occupancyDate, int remainingCapacity, float minRatePlanAmount) {
		super();
		this.propertyDetails = propertyDetails;
		this.roomTypeMaster = roomTypeMaster;
		this.occupancyDate = occupancyDate;
		this.remainingCapacity = remainingCapacity;
		this.minRatePlanAmount = minRatePlanAmount;
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

	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name = "PropertyId", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public PropertyDetails getPropertyDetails() {
		return propertyDetails;
	}

	public void setPropertyDetails(PropertyDetails propertyDetails) {
		this.propertyDetails = propertyDetails;
	}
	
	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name = "roomTypeId", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public RoomTypeMaster getRoomTypeMaster() {
		return roomTypeMaster;
	}

	public void setRoomTypeMaster(RoomTypeMaster roomTypeMaster) {
		this.roomTypeMaster = roomTypeMaster;
	}

	@Column(nullable = false)
	public Date getOccupancyDate() {
		return occupancyDate;
	}

	public void setOccupancyDate(Date occupancyDate) {
		this.occupancyDate = occupancyDate;
	}

	public int getRemainingCapacity() {
		return remainingCapacity;
	}

	public void setRemainingCapacity(int remainingCapacity) {
		this.remainingCapacity = remainingCapacity;
	}

	
	public float getMinRatePlanAmount() {
		return minRatePlanAmount;
	}

	public void setMinRatePlanAmount(float minRatePlanAmount) {
		this.minRatePlanAmount = minRatePlanAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((occupancyDate == null) ? 0 : occupancyDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ChannelManagerAvailabilityDetails))
			return false;
		ChannelManagerAvailabilityDetails other = (ChannelManagerAvailabilityDetails) obj;
		if (id != other.id)
			return false;
		if (occupancyDate == null) {
			if (other.occupancyDate != null)
				return false;
		} else if (!occupancyDate.equals(other.occupancyDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChannelManagerAvailabilityDetails [propertyDetails="
				+ propertyDetails + ", roomTypeMaster=" + roomTypeMaster
				+ ", occupancyDate=" + occupancyDate + ", remainingCapacity="
				+ remainingCapacity + ", minRatePlanAmount="
				+ minRatePlanAmount + "]";
	}
}
