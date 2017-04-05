package com.ai.sample.db.model.property.recommendations;

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
import com.ai.sample.db.model.property.mapping.PropertyOnlineTravelAgentConnectionMapping;



@Entity
@Table(name="pqm_algo_data",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertydetailsid","parameterid","propertyotamappingid"}))
public class PQMAlgoData {
	private int id;
	private PropertyDetails propertyDetails;
	private PQMParameterData  pqmParameterData;
	private PropertyOnlineTravelAgentConnectionMapping propertyOnlineTravelAgentConnectionMapping;
	private float value;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((propertyDetails == null) ? 0 : propertyDetails.hashCode());
		return result;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "propertydetailsid", nullable = false)
	public PropertyDetails getPropertyDetails() {
		return propertyDetails;
	}

	public void setPropertyDetails(PropertyDetails propertyDetails) {
		this.propertyDetails = propertyDetails;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "parameterid", nullable = false)
	public PQMParameterData getPqmParameterData() {
		return pqmParameterData;
	}

	public void setPqmParameterData(PQMParameterData pqmParameterData) {
		this.pqmParameterData = pqmParameterData;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "propertyotamappingid", nullable = false)
	public PropertyOnlineTravelAgentConnectionMapping getPropertyOnlineTravelAgentConnectionMapping() {
		return propertyOnlineTravelAgentConnectionMapping;
	}

	public void setPropertyOnlineTravelAgentConnectionMapping(
			PropertyOnlineTravelAgentConnectionMapping propertyOnlineTravelAgentConnectionMapping) {
		this.propertyOnlineTravelAgentConnectionMapping = propertyOnlineTravelAgentConnectionMapping;
	}
	
	

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PQMAlgoData))
			return false;
		PQMAlgoData other = (PQMAlgoData) obj;
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
		return "PQMAlgoData [id=" + id + ", propertyDetails=" + propertyDetails
				+ ", pqmParameterData=" + pqmParameterData
				+ ", propertyOnlineTravelAgentConnectionMapping="
				+ propertyOnlineTravelAgentConnectionMapping + ", value="
				+ value + "]";
	}
}
