package com.ai.sample.db.model.ota;

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

@Entity
@Table(name="online_travel_agent_connection_details",  uniqueConstraints=
@UniqueConstraint(columnNames={"name", "otadetailsid"}))
public class OnlineTravelAgentConnectionDetails {
	private int id;
	private OnlineTravelAgentDetails otaDetails;
	private String name;
	private String userName;
	private String password;
	private String url;
	
	
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
	@JoinColumn(name = "otadetailsid", nullable = false)
	public OnlineTravelAgentDetails getOtaDetails() {
		return otaDetails;
	}

	public void setOtaDetails(OnlineTravelAgentDetails otaDetails) {
		this.otaDetails = otaDetails;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "username", nullable = false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "url", nullable = false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
		if (!(obj instanceof OnlineTravelAgentConnectionDetails))
			return false;
		OnlineTravelAgentConnectionDetails other = (OnlineTravelAgentConnectionDetails) obj;
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
		return "OnlineTravelAgentConnectionDetails [id=" + id + ", otaDetails="
				+ otaDetails.toString() + ", name=" + name + ", userName=" + userName
				+ ", password=" + password + ", url=" + url + "]";
	}
	
}
