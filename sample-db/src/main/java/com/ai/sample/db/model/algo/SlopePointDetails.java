package com.ai.sample.db.model.algo;

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
@Table(name="slope_point_details",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertyid","seasonid"}))
public class SlopePointDetails {
	private int id;
	private PropertyDetails propertyDetails;
//	private SeasonDetails seasonDetails;
	private int seasonid;
	private float allVal;
	private float weekEnds;
	private float weekDays;
	private float dow1;
	private float dow2;
	private float dow3;
	private float dow4;
	private float dow5;
	private float dow6;
	private float dow7;
	
	public SlopePointDetails() {
		super();
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
	@JoinColumn(name = "propertyid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public PropertyDetails getPropertyDetails() {
		return propertyDetails;
	}

	public void setPropertyDetails(PropertyDetails propertyDetails) {
		this.propertyDetails = propertyDetails;
	}

/*	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "seasonid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public SeasonDetails getSeasonDetails() {
		return seasonDetails;
	}

	public void setSeasonDetails(SeasonDetails seasonDetails) {
		this.seasonDetails = seasonDetails;
	}*/

	

	public int getSeasonid() {
		return seasonid;
	}

	public void setSeasonid(int seasonid) {
		this.seasonid = seasonid;
	}


	public float getAllVal() {
		return allVal;
	}

	public void setAllVal(float allVal) {
		this.allVal = allVal;
	}

	public float getWeekEnds() {
		return weekEnds;
	}

	public void setWeekEnds(float weekEnds) {
		this.weekEnds = weekEnds;
	}

	public float getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(float weekDays) {
		this.weekDays = weekDays;
	}

	public float getDow1() {
		return dow1;
	}

	public void setDow1(float dow1) {
		this.dow1 = dow1;
	}

	public float getDow2() {
		return dow2;
	}

	public void setDow2(float dow2) {
		this.dow2 = dow2;
	}

	public float getDow3() {
		return dow3;
	}

	public void setDow3(float dow3) {
		this.dow3 = dow3;
	}

	public float getDow4() {
		return dow4;
	}

	public void setDow4(float dow4) {
		this.dow4 = dow4;
	}

	public float getDow5() {
		return dow5;
	}

	public void setDow5(float dow5) {
		this.dow5 = dow5;
	}

	public float getDow6() {
		return dow6;
	}

	public void setDow6(float dow6) {
		this.dow6 = dow6;
	}

	public float getDow7() {
		return dow7;
	}

	public void setDow7(float dow7) {
		this.dow7 = dow7;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((propertyDetails.getName() == null) ? 0 : propertyDetails.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SlopePointDetails))
			return false;
		SlopePointDetails other = (SlopePointDetails) obj;
		if (id != other.id)
			return false;
		if (propertyDetails.getName() == null) {
			if (other.propertyDetails.getName() != null)
				return false;
		} else if (!propertyDetails.getName().equals(other.propertyDetails.getName()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SlopePointDetails [id=" + id + ", propertyDetails="
				+ propertyDetails + ", seasonid=" + seasonid
				+ ", allVal=" + allVal + ", weekEnds=" + weekEnds + ", weekDays="
				+ weekDays + ", dow1=" + dow1 + ", dow2=" + dow2 + ", dow3="
				+ dow3 + ", dow4=" + dow4 + ", dow5=" + dow5 + ", dow6=" + dow6
				+ ", dow7=" + dow7 + "]";
	}
}
