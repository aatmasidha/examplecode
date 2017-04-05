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
@Table(name="season_details",  uniqueConstraints=
@UniqueConstraint(columnNames={"propertyid", "fromWeek", "toWeek"}))
public class SeasonDetails {
	
	private int id;
	int seasonNumber;
	private PropertyDetails propertyDetails;
	int fromWeek;
	String fromWeekCondition;
	int  toWeek;
	String toWeekCondition;
	String condition;
	

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


	@Column(nullable = false)
	public int getFromWeek() {
		return fromWeek;
	}

	public void setFromWeek(int fromWeek) {
		this.fromWeek = fromWeek;
	}

	@Column(nullable = false)
	public int getToWeek() {
		return toWeek;
	}

	public void setToWeek(int toWeek) {
		this.toWeek = toWeek;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	

	public String getFromWeekCondition() {
		return fromWeekCondition;
	}

	public void setFromWeekCondition(String fromWeekCondition) {
		this.fromWeekCondition = fromWeekCondition;
	}

	public String getToWeekCondition() {
		return toWeekCondition;
	}

	public void setToWeekCondition(String toWeekCondition) {
		this.toWeekCondition = toWeekCondition;
	}

	
	public int getSeasonNumber() {
		return seasonNumber;
	}

	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	@Override
	public String toString() {
		return "SeasonDetails [id=" + id + ", seasonNumber=" + seasonNumber
				+ ", propertyDetails=" + propertyDetails + ", fromWeek="
				+ fromWeek + ", fromWeekCondition=" + fromWeekCondition
				+ ", toWeek=" + toWeek + ", toWeekCondition=" + toWeekCondition
				+ ", condition=" + condition + "]";
	}
}
