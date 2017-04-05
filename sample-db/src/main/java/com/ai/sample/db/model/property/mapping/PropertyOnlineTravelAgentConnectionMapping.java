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

import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Entity
@Table(name="property_ota_connection_mapping",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertyOtaName","propertydetailsid"}))
public class PropertyOnlineTravelAgentConnectionMapping {
	private int id;
	private String propertyOtaName;
	private OnlineTravelAgentDetails otaDetails;
	private PropertyDetails propertyDetails;
	
	public PropertyOnlineTravelAgentConnectionMapping() {
		super();
	}

	public PropertyOnlineTravelAgentConnectionMapping(
			PropertyDetails propertyDetails,
			OnlineTravelAgentDetails onlineTravelAgent, String propertyOtaName) {
		this.propertyOtaName = propertyOtaName;
		this.propertyDetails = propertyDetails;
		this.otaDetails = onlineTravelAgent;
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

	@ManyToOne(/*fetch = FetchType.LAZY,*/ cascade=CascadeType.ALL)
	@JoinColumn(name = "otadetailsid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public OnlineTravelAgentDetails getOtaDetails() {
		return otaDetails;
	}

	public void setOtaDetails(OnlineTravelAgentDetails otaDetails) {
		this.otaDetails = otaDetails;
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
	
	@Column(name="propertyotaname",nullable=false)
	public String getPropertyOtaName() {
		return propertyOtaName;
	}

	public void setPropertyOtaName(String propertyOtaName) {
		this.propertyOtaName = propertyOtaName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((otaDetails.getName() == null) ? 0 : otaDetails.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PropertyOnlineTravelAgentConnectionMapping))
			return false;
		PropertyOnlineTravelAgentConnectionMapping other = (PropertyOnlineTravelAgentConnectionMapping) obj;
		if (id != other.id)
			return false;
		if (otaDetails.getName() == null) {
			if (other.otaDetails.getName() != null)
				return false;
		} else if (!otaDetails.getName().equals(other.otaDetails.getName()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropertyOnlineTravelAgentConnectionMapping [id=" + id
				+ ", otaConnectionDetails=" + otaDetails.toString()
				+ ", propertyDetails=" + propertyDetails.toString() + "]";
	}
	
}
