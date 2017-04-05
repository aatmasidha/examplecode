package com.ai.sample.db.model.isell;

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

import com.ai.sample.db.model.configuration.RoomTypeMaster;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Entity
@Table(name="ota_performance_summary_details_history_by_day",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertydetailsid", "occupanyDate", "updateDate"}))
public class OtaPerformanceSummaryDetailsHistoryByDay {
	private int id;
	private PropertyDetails propertyDetails;
	Date occupanyDate;
	int otaSold;
	float otaTotalRevenue;
	int pickUpFromLastLR;
	float avgDailyRate;
	Date updateDate;
	
	public OtaPerformanceSummaryDetailsHistoryByDay() {
		super();
	}

	public OtaPerformanceSummaryDetailsHistoryByDay(PropertyDetails propertyDetails, Date occupanyDate, int otaSold,
			float otaTotalRevenue, int pickUpFromLastLR, Date updateDate) {
		super();
		this.propertyDetails = propertyDetails;
		this.occupanyDate = occupanyDate;
		this.otaSold = otaSold;
		this.otaTotalRevenue = otaTotalRevenue;
		this.avgDailyRate = otaTotalRevenue / otaSold;
		this.pickUpFromLastLR = pickUpFromLastLR;
		this.updateDate = updateDate;
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

	public Date getOccupanyDate() {
		return occupanyDate;
	}

	public void setOccupanyDate(Date occupanyDate) {
		this.occupanyDate = occupanyDate;
	}

	public int getOtaSold() {
		return otaSold;
	}

	public void setOtaSold(int otaSold) {
		this.otaSold = otaSold;
	}

	public int getPickUpFromLastLR() {
		return pickUpFromLastLR;
	}

	public void setPickUpFromLastLR(int pickUpFromLastLR) {
		this.pickUpFromLastLR = pickUpFromLastLR;
	}
	
	public float getOtaTotalRevenue() {
		return otaTotalRevenue;
	}

	public void setOtaTotalRevenue(float otaTotalRevenue) {
		this.otaTotalRevenue = otaTotalRevenue;
	}

	public float getAvgDailyRate() {
		return avgDailyRate;
	}

	public void setAvgDailyRate(float avgDailyRate) {
		this.avgDailyRate = this.otaTotalRevenue / this.otaSold;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((occupanyDate == null) ? 0 : occupanyDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RoomTypeMaster))
			return false;
		OtaPerformanceSummaryDetailsHistoryByDay other = (OtaPerformanceSummaryDetailsHistoryByDay) obj;
		if (id != other.id)
			return false;
		if (occupanyDate == null) {
			if (other.occupanyDate != null)
				return false;
		} else if (!occupanyDate.equals(other.occupanyDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OtaPerformanceSummaryDetailsByDay [id=" + id
				+ ", occupanyDate=" + occupanyDate + ", otaSold=" + otaSold
				+ ", otaTotalRevenue=" + otaTotalRevenue
				+ ", pickUpFromLastLR=" + pickUpFromLastLR + ", avgDailyRate="
				+ avgDailyRate + ", updateDate=" + updateDate
				+ "]";
	}

}
