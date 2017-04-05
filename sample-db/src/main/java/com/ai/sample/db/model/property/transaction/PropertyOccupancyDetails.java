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
@Table(name="property_occupancy_details",  uniqueConstraints=
@UniqueConstraint(columnNames={ "PropertyId", "occupancyDate", "roomTypeId"}))

public class PropertyOccupancyDetails {
	private int id;
	private PropertyDetails propertyDetails;
	private RoomTypeMaster roomTypeMaster;
	private Date occupancyDate;
	private int capacity;
	private int occupancy;
	private int lastPickup = 0;
	private int outOfOrderCount;
	private int vacantCount;
	
	public PropertyOccupancyDetails() {
		super();
	}

	public PropertyOccupancyDetails(PropertyDetails propertyDetails,
			RoomTypeMaster roomTypeMaster, Date occupancyDate, int capacity,
			int occupancy, int lastPickup, int outOfOrderCount, int vacantCount) {
		super();
		this.propertyDetails = propertyDetails;
		this.roomTypeMaster = roomTypeMaster;
		this.occupancyDate = occupancyDate;
		this.capacity = capacity;
		this.occupancy = occupancy;
		this.outOfOrderCount = outOfOrderCount;
		this.vacantCount = vacantCount;
		this.lastPickup = lastPickup;
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}

	public int getOutOfOrderCount() {
		return outOfOrderCount;
	}

	public void setOutOfOrderCount(int outOfOrderCount) {
		this.outOfOrderCount = outOfOrderCount;
	}



	public int getVacantCount() {
		return vacantCount;
	}

	public void setVacantCount(int vacantCount) {
		this.vacantCount = vacantCount;
	}
	
	public int getLastPickup() {
		return lastPickup;
	}

	public void setLastPickup(int lastPickup) {
		this.lastPickup = lastPickup;
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
		if (!(obj instanceof PropertyOccupancyDetails))
			return false;
		PropertyOccupancyDetails other = (PropertyOccupancyDetails) obj;
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
		return "PropertyOccupancyDetails [id=" + id + ", propertyDetails="
				+ propertyDetails + ", roomTypeMaster=" + roomTypeMaster
				+ ", occupancyDate=" + occupancyDate + ", capacity=" + capacity
				+ ", occupancy=" + occupancy + ", lastPickup=" + lastPickup
				+ ", outOfOrderCount=" + outOfOrderCount + ", vacantCount="
				+ vacantCount + "]";
	}

}
