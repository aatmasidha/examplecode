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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.ai.sample.db.model.configuration.RoomTypeMaster;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.type.MyJson;
import com.ai.sample.db.type.StringJsonUserType;

@Entity
@Table(name="rate_shopping_rates_summary_by_day",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertydetailsid", "businessdate"}))

@TypeDefs({ @TypeDef(name = "CustomJsonObject", typeClass = StringJsonUserType.class) })
public class RateShoppingRatesSummaryByDay {
	private int id;
	private PropertyDetails propertyDetails;
	private Date businessDate;
	
	@Type(type = "CustomJsonObject" )
	@Column(name = "rateshoppingrates")
	public MyJson rateshoppingrates;
	
	public RateShoppingRatesSummaryByDay() {
		super();
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


	public MyJson getRateshoppingrates() {
		return rateshoppingrates;
	}

	public void setRateshoppingrates(MyJson rateshoppingrates) {
		this.rateshoppingrates = rateshoppingrates;
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
		result = prime * result + ((businessDate == null) ? 0 : businessDate.hashCode());
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
		RateShoppingRatesSummaryByDay other = (RateShoppingRatesSummaryByDay) obj;
		if (id != other.id)
			return false;
		if (businessDate == null) {
			if (other.businessDate != null)
				return false;
		} else if (!businessDate.equals(other.businessDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ISellRateShoppingDetailsByDay [id=" + id + ", propertyDetails="
				+ propertyDetails + ", businessDate=" + businessDate
				+ ", rateshoppingrates=" + rateshoppingrates + "]";
	}

}
