package com.ai.sample.db.model.property.recommendations;

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

import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Entity
@Table(name="rate_recommendation_data",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertydetailsid","recommendationDate", "occupancyDate"}))
public class RateRecommendationData {
	private int id;
	private PropertyDetails propertyDetails;
	private Date recommendationDate;
	private Date occupancyDate;
	private float marketAppropriateRate;
	private float bestAvailableRate;
	private float overridenRate;
	private float recommendedRate;
	private String comments;
	
//  TODO	Check if we want to add room type data as well at present rate recommendation
//	data is at occupancy date level for a hotel
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "propertydetailsid", nullable = false)
	public PropertyDetails getPropertyDetails() {
		return propertyDetails;
	}

	public void setPropertyDetails(PropertyDetails propertyDetails) {
		this.propertyDetails = propertyDetails;
	}

	@Column(name = "recommendationdate", nullable = false)
	public Date getRecommendationDate() {
		return recommendationDate;
	}

	public void setRecommendationDate(Date recommendationDate) {
		this.recommendationDate = recommendationDate;
	}

	@Column(name = "occupancydate", nullable = false)
	public Date getOccupancyDate() {
		return occupancyDate;
	}

	public void setOccupancyDate(Date occupancyDate) {
		this.occupancyDate = occupancyDate;
	}

	@Column(name = "marketappropriaterate", nullable = false)
	public float getMarketAppropriateRate() {
		return marketAppropriateRate;
	}

	public void setMarketAppropriateRate(float marketAppropriateRate) {
		this.marketAppropriateRate = marketAppropriateRate;
	}

	@Column(name = "bestavailablerate", nullable = true)
	public float getBestAvailableRate() {
		return bestAvailableRate;
	}

	public void setBestAvailableRate(float bestAvailableRate) {
		this.bestAvailableRate = bestAvailableRate;
	}

	@Column(name = "overriddenrate", nullable = true)
	public float getOverridenRate() {
		return overridenRate;
	}

	public void setOverridenRate(float overridenRate) {
		this.overridenRate = overridenRate;
	}

	@Column(name = "recommendedrate", nullable = false)
	public float getRecommendedRate() {
		return recommendedRate;
	}

	public void setRecommendedRate(float recommendedRate) {
		this.recommendedRate = recommendedRate;
	}

	@Column(name = "comments", nullable = true, length=50)
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((propertyDetails == null) ? 0 : propertyDetails.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RateRecommendationData))
			return false;
		RateRecommendationData other = (RateRecommendationData) obj;
		if (id != other.id)
			return false;
		if (propertyDetails == null) {
			if (other.propertyDetails != null)
				return false;
		} else if (!propertyDetails.equals(other.propertyDetails))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RateRecommendationData [id=" + id + ", propertyDetails="
				+ propertyDetails + ", recommendationDate="
				+ recommendationDate + ", occupancyDate=" + occupancyDate
				+ ", marketAppropriateRate=" + marketAppropriateRate
				+ ", bestAvailableRate=" + bestAvailableRate
				+ ", overridenRate=" + overridenRate + ", recommendedRate="
				+ recommendedRate + ", comments=" + comments + "]";
	}

}
