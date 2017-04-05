package com.ai.sample.db.model.configuration.events;

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
import com.ai.sample.db.model.configuration.events.Severity;
import com.ai.sample.db.type.MyJson;
import com.ai.sample.db.type.StringJsonUserType;

@Entity
@Table(name="event_configuration",  uniqueConstraints=
@UniqueConstraint(columnNames={"name", "eventDate", "eventcategoryid", "eventtypeid", "severityid"  }))

@TypeDefs({ @TypeDef(name = "CustomJsonObject", typeClass = StringJsonUserType.class) })
public class EventConfiguration {
	
	private int id;
	private String name;
	private Date eventDate;
	private EventCategoryConfiguration eventCategory;
	private EventTypeConfiguration eventType;
	private Severity severity;
	private boolean recurring = false;
	
	@Type(type = "CustomJsonObject" )
	@Column(name = "rateshoppingrates")
	private MyJson region;
	
	public EventConfiguration() {
		super();
	}
	
	public EventConfiguration(String name, Date eventDate,
			EventCategoryConfiguration eventCategory,
			EventTypeConfiguration eventType,Severity severity, boolean recurring) {
		super();
		this.eventDate = eventDate;
		this.name = name;
		this.eventCategory = eventCategory;
		this.eventType = eventType;
		this.recurring = recurring;
		this.severity = severity;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "eventcategoryid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public EventCategoryConfiguration getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(EventCategoryConfiguration eventCategory) {
		this.eventCategory = eventCategory;
	}


	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "eventtypeid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public EventTypeConfiguration getEventType() {
		return eventType;
	}

	public void setEventType(EventTypeConfiguration eventType) {
		this.eventType = eventType;
	}

	public boolean isRecurring() {
		return recurring;
	}
	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "severityid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	public MyJson getRegion() {
		return region;
	}

	public void setRegion(MyJson region) {
		this.region = region;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RoomTypeMaster))
			return false;
		EventConfiguration other = (EventConfiguration) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "EventConfiguration [id=" + id + ", name=" + name
				+ ", eventCategory=" + eventCategory + ", eventType="
				+ eventType + ", recurring=" + recurring + "]";
	}
}
