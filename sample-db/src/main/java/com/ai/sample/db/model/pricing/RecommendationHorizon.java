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
@Table(name="recommendation_horizon",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertydetailsid", "seasonNum","dow",  "horizonNum"}))

public class RecommendationHorizon {
	private int id;
	private PropertyDetails propertyDetails;
	private int seasonNum;
	private int dow;
	private int horizonNum;
	private int horizonFrom;
	private int horizonTo;

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

	public int getSeasonNum() {
		return seasonNum;
	}

	public void setSeasonNum(int seasonNum) {
		this.seasonNum = seasonNum;
	}

	public int getDow() {
		return dow;
	}

	public void setDow(int dow) {
		this.dow = dow;
	}

	public int getHorizonNum() {
		return horizonNum;
	}

	public void setHorizonNum(int horizonNum) {
		this.horizonNum = horizonNum;
	}

	public float getHorizonFrom() {
		return horizonFrom;
	}

	public void setHorizonFrom(int horizonFrom) {
		this.horizonFrom = horizonFrom;
	}

	public float getHorizonTo() {
		return horizonTo;
	}

	public void setHorizonTo(int horizonTo) {
		this.horizonTo = horizonTo;
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
		if (!(obj instanceof RecommendationHorizon))
			return false;
		RecommendationHorizon other = (RecommendationHorizon) obj;
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
		return "RecommendationHorizon [id=" + id + ", propertyDetails="
				+ propertyDetails + ", seasonNum=" + seasonNum + ", dow=" + dow
				+ ", horizonNum=" + horizonNum + ", horizonFrom=" + horizonFrom
				+ ", horizonTo=" + horizonTo + "]";
	}
}
