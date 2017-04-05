package com.ai.sample.db.model.property.mapping;

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
@Table(name="property_room_type_mapping",  uniqueConstraints=
//@UniqueConstraint(columnNames={"name","roomtypemasterid", "propertydetailsid"}))
@UniqueConstraint(columnNames={"name","propertydetailsid"}))
public class PropertyRoomTypeMapping {
	private int id;
	private String name;
	private RoomTypeMaster roomTypeMaster;
	private PropertyDetails propertyDetails;
	private int numRooms;
	private boolean isDefault;
	
	public PropertyRoomTypeMapping() {
		super();
	}
	
	public PropertyRoomTypeMapping(String name, RoomTypeMaster roomTypeMaster,
			PropertyDetails propertyDetails, int numRooms) {
		super();
		this.name = name;
		this.roomTypeMaster = roomTypeMaster;
		this.propertyDetails = propertyDetails;
		this.numRooms = numRooms;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "propertydetailsid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public PropertyDetails getPropertyDetails() {
		return propertyDetails;
	}

	public void setPropertyDetails(PropertyDetails propertyDetails) {
		this.propertyDetails = propertyDetails;
	}
	
	public int getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	@Column(name="isdefault")
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PropertyRoomTypeMapping))
			return false;
		PropertyRoomTypeMapping other = (PropertyRoomTypeMapping) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropertyRoomTypeMapping [id=" + id + ", name=" + name
				+ ", roomTypeMaster=" + roomTypeMaster + ", propertyDetails="
				+ propertyDetails + ", numRooms=" + numRooms + ", isDefault="
				+ isDefault + "]";
	}

}
