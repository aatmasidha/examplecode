package com.ai.sample.db.model.process;

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
@Table(name="daily_extract_status_by_property",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertydetailsid", "businessDate"}))

public class DailyExtractStatusByProperty {
	private int id;
	private PropertyDetails propertyDetails;
	private Date businessDate;
	private ProcessStatuses processStatus;
	

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

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}


	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "processstatusid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public ProcessStatuses getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(ProcessStatuses processStatus) {
		this.processStatus = processStatus;
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
		if (!(obj instanceof DailyExtractStatusByProperty))
			return false;
		DailyExtractStatusByProperty other = (DailyExtractStatusByProperty) obj;
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
		return "DailyExtractStatusByProperty [id=" + id + ", propertyDetails="
				+ propertyDetails + ", businessDate=" + businessDate
				+ ", processStatus=" + processStatus + "]";
	}
}
