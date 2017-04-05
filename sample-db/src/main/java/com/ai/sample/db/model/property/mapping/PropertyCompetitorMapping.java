package com.ai.sample.db.model.property.mapping;

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

import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Entity
@Table(name="property_competitor_mapping",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertydetailsid", "competitordetailsid"}))
public class PropertyCompetitorMapping {
	private int id;
	private PropertyDetails propertyDetails;
	private PropertyDetails competitorDetails;
	private int competitorSequence;
	private Date updateDate;
	private boolean isactive;

	public PropertyCompetitorMapping() {
		super();
	}
	
	public PropertyCompetitorMapping(
			PropertyDetails propertyDetails, PropertyDetails competitorDetails,
			int competitorId, Date updateDate, boolean isactive) {
		super();
		this.propertyDetails = propertyDetails;
		this.competitorDetails = competitorDetails;
		this.competitorSequence = competitorId;
		this.updateDate = updateDate;
		this.isactive = isactive;
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
	@JoinColumn(name = "competitordetailsid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public PropertyDetails getCompetitorDetails() {
		return competitorDetails;
	}

	public void setCompetitorDetails(PropertyDetails competitorDetails) {
		this.competitorDetails = competitorDetails;
	}

	public int getCompetitorSequence() {
		return competitorSequence;
	}

	public void setCompetitorSequence(int competitorSequence) {
		this.competitorSequence = competitorSequence;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((competitorDetails.getName() == null) ? 0 : competitorDetails.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PropertyCompetitorMapping))
			return false;
		PropertyCompetitorMapping other = (PropertyCompetitorMapping) obj;
		if (id != other.id)
			return false;
		if (competitorDetails.getName() == null) {
			if (other.competitorDetails.getName() != null)
				return false;
		} else if (!competitorDetails.getName().equals(other.competitorDetails.getName()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropertyCompetitorMapping [id=" + id + ", propertyDetails="
				+ propertyDetails + ", competitorDetails=" + competitorDetails
				+ ", competitorSequence=" + competitorSequence
				+ ", updateDate=" + updateDate + ", isactive=" + isactive + "]";
	}
}
