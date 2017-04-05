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

import com.ai.sample.db.model.configuration.events.EventConfiguration;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Entity
@Table(name="property_event_mapping",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertydetailsid", "eventconfigurationid"}))
public class PropertyEventMapping {
	private int id;

	private PropertyDetails propertyDetails;
	private EventConfiguration eventConfiguration;
	
	public PropertyEventMapping() {
		super();
	}
	
	
	public PropertyEventMapping(PropertyDetails propertyDetails,
			EventConfiguration eventConfiguration) {
		super();
		this.propertyDetails = propertyDetails;
		this.eventConfiguration = eventConfiguration;
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
	@JoinColumn(name = "eventconfigurationid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

	public EventConfiguration getEventConfiguration() {
		return eventConfiguration;
	}


	public void setEventConfiguration(EventConfiguration eventConfiguration) {
		this.eventConfiguration = eventConfiguration;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((eventConfiguration.getName() == null) ? 0 : eventConfiguration.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PropertyEventMapping))
			return false;
		PropertyEventMapping other = (PropertyEventMapping) obj;
		if (id != other.id)
			return false;
		if (eventConfiguration.getName() == null) {
			if (other.eventConfiguration.getName() != null)
				return false;
		} else if (!eventConfiguration.getName().equals(other.eventConfiguration.getName()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropertyEventMapping [propertyDetails=" + propertyDetails
				+ ", eventConfiguration=" + eventConfiguration + "]";
	}

}
