package com.ai.sample.db.model.configuration.events;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ai.sample.db.model.configuration.RoomTypeMaster;

@Entity
@Table(name="event_type_configuration",  uniqueConstraints=
@UniqueConstraint(columnNames={"name"}))
public class EventTypeConfiguration {
	private int id;
	private String name;
	
	public EventTypeConfiguration() {
		super();
	}
	
	public EventTypeConfiguration(String name) {
		super();
		this.name = name;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		EventTypeConfiguration other = (EventTypeConfiguration) obj;
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
		return "EventTypeConfiguration [id=" + id + ", name=" + name
				+  "]";
	}
}
