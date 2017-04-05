package com.ai.sample.db.model.pricing;

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

import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Entity
@Table(name="property_day_of_week_definition",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertydetailsid", "seasonNumber", "dayOfWeek", "dayType"}))

public class PropertyDayOfWeekDefinition {
	private int id;
	private PropertyDetails propertyDetails;
	private int seasonNumber = 0;
	private String dayOfWeek;
	private String dayType;
	

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

	
	public int getSeasonNumber() {
		return seasonNumber;
	}

	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getDayType() {
		return dayType;
	}

	public void setDayType(String dayType) {
		this.dayType = dayType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((dayOfWeek == null) ? 0 : dayOfWeek.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PropertyDayOfWeekDefinition))
			return false;
		PropertyDayOfWeekDefinition other = (PropertyDayOfWeekDefinition) obj;
		if (id != other.id)
			return false;
		if (dayOfWeek == null) {
			if (other.dayOfWeek != null)
				return false;
		} else if (!dayOfWeek.equals(other.dayOfWeek))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropertyDayOfWeekDefinition [id=" + id + ", propertyDetails="
				+ propertyDetails + ", seasonNumber=" + seasonNumber
				+ ", dayOfWeek=" + dayOfWeek + ", dayType=" + dayType + "]";
	}
}
