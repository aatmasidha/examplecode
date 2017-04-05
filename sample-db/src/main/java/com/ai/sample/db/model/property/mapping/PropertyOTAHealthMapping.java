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

import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Entity
@Table(name="property_ota_health_mapping",  uniqueConstraints=
@UniqueConstraint(columnNames={"parameter","propertyotaconnectionmappingid", "propertydetailsid"}))
public class PropertyOTAHealthMapping {
	private int id;
	private PropertyOnlineTravelAgentConnectionMapping propertyOTAConnectionMapping;
	private PropertyDetails propertyDetails;
	private String parameter;
	private String value;
	
	public PropertyOTAHealthMapping() {
		super();
	}
	
	public PropertyOTAHealthMapping( PropertyOnlineTravelAgentConnectionMapping propertyOTAConnectionMapping,
			PropertyDetails propertyDetails, String parameter, String value) {
		super();
		this.propertyOTAConnectionMapping = propertyOTAConnectionMapping;
		this.propertyDetails = propertyDetails;
		this.parameter = parameter;
		this.value = value;
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


	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "propertyotaconnectionmappingid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public PropertyOnlineTravelAgentConnectionMapping getPropertyOTAConnectionMapping() {
		return propertyOTAConnectionMapping;
	}

	public void setPropertyOTAConnectionMapping(
			PropertyOnlineTravelAgentConnectionMapping propertyOTAConnectionMapping) {
		this.propertyOTAConnectionMapping = propertyOTAConnectionMapping;
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

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((parameter == null) ? 0 : parameter.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PropertyOTAHealthMapping))
			return false;
		PropertyOTAHealthMapping other = (PropertyOTAHealthMapping) obj;
		if (id != other.id)
			return false;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropertyOTAHealthMapping [id=" + id
				+ ", propertyOTAConnectionMapping="
				+ propertyOTAConnectionMapping + ", propertyDetails="
				+ propertyDetails + ", parameter=" + parameter + ", value="
				+ value + "]";
	}

}
