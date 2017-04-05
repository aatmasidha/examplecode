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
import com.ai.sample.db.model.rateshopping.RateShoppingEngineData;

@Entity
@Table(name = "rate_shopping_property_details_mapping",  uniqueConstraints=
@UniqueConstraint(columnNames={"rateShoppingengineid","propertydetailsid", "otaid"}))
public class RateShoppingPropertyDetailsMapping {
	private int id;
	private RateShoppingEngineData rateShoppingEngineData;
	private PropertyDetails propertyDetails;
	private String rateShoppingPropertyUID;
	private OnlineTravelAgentDetails otaID;
	private String rateShoppingOtaID;
	
	public RateShoppingPropertyDetailsMapping() {
		super();
	}
	
	public RateShoppingPropertyDetailsMapping(
			RateShoppingEngineData rateShoppingEngineData,
			PropertyDetails propertyDetails, String rateShoppingPropertyUID,
			OnlineTravelAgentDetails otaID, String rateShoppingOtaID) {
		super();
		this.rateShoppingEngineData = rateShoppingEngineData;
		this.propertyDetails = propertyDetails;
		this.rateShoppingPropertyUID = rateShoppingPropertyUID;
		this.otaID = otaID;
		this.rateShoppingOtaID = rateShoppingOtaID;
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

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "rateShoppingengineid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public RateShoppingEngineData getRateShoppingEngineData() {
		return rateShoppingEngineData;
	}

	public void setRateShoppingEngineData(
			RateShoppingEngineData rateShoppingEngineData) {
		this.rateShoppingEngineData = rateShoppingEngineData;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "propertydetailsid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public PropertyDetails getPropertyDetails() {
		return propertyDetails;
	}

	public void setPropertyDetails(PropertyDetails propertyDetails) {
		this.propertyDetails = propertyDetails;
	}

	public String getRateShoppingPropertyUID() {
		return rateShoppingPropertyUID;
	}

	public void setRateShoppingPropertyUID(String rateShoppingPropertyUID) {
		this.rateShoppingPropertyUID = rateShoppingPropertyUID;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "otaid", nullable = true)
	public OnlineTravelAgentDetails getOtaID() {
		return otaID;
	}

	public void setOtaID(OnlineTravelAgentDetails otaID) {
		this.otaID = otaID;
	}

	public String getRateShoppingOtaID() {
		return rateShoppingOtaID;
	}

	public void setRateShoppingOtaID(String rateShoppingOtaID) {
		this.rateShoppingOtaID = rateShoppingOtaID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime
				* result
				+ ((rateShoppingPropertyUID == null) ? 0
						: rateShoppingPropertyUID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RateShoppingPropertyDetailsMapping))
			return false;
		RateShoppingPropertyDetailsMapping other = (RateShoppingPropertyDetailsMapping) obj;
		if (id != other.id)
			return false;
		if (rateShoppingPropertyUID == null) {
			if (other.rateShoppingPropertyUID != null)
				return false;
		} else if (!rateShoppingPropertyUID
				.equals(other.rateShoppingPropertyUID))
			return false;
		return true;
	}

}
