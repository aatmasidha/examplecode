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
@Table(name="property_recommendation_horizon_definition",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertydetailsid", "seasonNumber","dayDefinition",  "startHorizon"}))

public class RecommentationHorizonByProperty {
	private int id;
	private PropertyDetails propertyDetails;
	private int  seasonNumber;
	private String dayDefinition;
	private int startHorizon;
	private int endHorizon;

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

	public String getDayDefinition() {
		return dayDefinition;
	}

	public void setDayDefinition(String dayDefinition) {
		this.dayDefinition = dayDefinition;
	}

	public int getStartHorizon() {
		return startHorizon;
	}

	public void setStartHorizon(int startHorizon) {
		this.startHorizon = startHorizon;
	}

	public int getEndHorizon() {
		return endHorizon;
	}

	public void setEndHorizon(int endHorizon) {
		this.endHorizon = endHorizon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((propertyDetails.getName() == null) ? 0 : propertyDetails.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RecommentationHorizonByProperty))
			return false;
		RecommentationHorizonByProperty other = (RecommentationHorizonByProperty) obj;
		if (id != other.id)
			return false;
		if (propertyDetails.getName() == null) {
			if (other.propertyDetails.getName() != null)
				return false;
		} else if (!propertyDetails.getName().equals(other.propertyDetails.getName()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RecommentationHorizonByProperty [id=" + id
				+ ", propertyDetails=" + propertyDetails + ", seasonNumber="
				+ seasonNumber + ", dayDefinition=" + dayDefinition
				+ ", startHorizon=" + startHorizon + ", endHorizon="
				+ endHorizon + "]";
	}

}
