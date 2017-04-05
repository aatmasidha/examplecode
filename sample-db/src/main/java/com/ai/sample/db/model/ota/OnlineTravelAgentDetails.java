package com.ai.sample.db.model.ota;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="online_travel_agent",  uniqueConstraints=
@UniqueConstraint(columnNames={"name"}))
public class OnlineTravelAgentDetails {
	private int id;
	private String name;
	private String DisplayName;
	
	
	
	public OnlineTravelAgentDetails() {
		super();
	}

	public OnlineTravelAgentDetails(String name, String displayName) {
		super();
		this.name = name;
		DisplayName = displayName;
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

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "displayname", nullable = false)
	public String getDisplayName() {
		return DisplayName;
	}

	public void setDisplayName(String displayName) {
		DisplayName = displayName;
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
		if (!(obj instanceof OnlineTravelAgentDetails))
			return false;
		OnlineTravelAgentDetails other = (OnlineTravelAgentDetails) obj;
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
		return "OnlineTravelAgentDetails [id=" + id + ", name=" + name
				+ ", displayName=" + DisplayName + "]";
	}
}
