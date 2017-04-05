package com.ai.sample.db.model.property.configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.ai.sample.db.type.MyJson;
import com.ai.sample.db.type.StringJsonUserType;



@Entity
@Table(name="hotel_chain_details")

@TypeDefs({ @TypeDef(name = "CustomJsonObject", typeClass = StringJsonUserType.class) })

public class HotelChainDetails {
	private int id;
	private String name;
	private String description;
	
	@Type(type = "CustomJsonObject" )
	@Column(name = "additionaldetails")
	public MyJson additionalDetails;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int getId() {
		return id;
	}
	
	public HotelChainDetails() {
		super();
	}

	public HotelChainDetails(int id, String name, String description,
			MyJson additionalDetails) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.additionalDetails = additionalDetails;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	@Column(name = "name", nullable = false, unique=true)
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column(name = "description", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
	
	/*
	 * Additional details will contain additional information about 
	 * about hotel chain
	 */
	@Type(type = "CustomJsonObject")
	@Column(name = "additionaldetails", nullable = true)
	public MyJson getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(final MyJson additionalDetails) {
		this.additionalDetails = additionalDetails;
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
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		else if (obj == null)
			return false;
		else if (!(obj instanceof HotelChainDetails))
			return false;
		HotelChainDetails other = (HotelChainDetails) obj;
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
		return "HotelChainDetails [id=" + id + ", name=" + name
				+ ", description=" + description + ", additionalDetails="
				+ additionalDetails + "]";
	}

}
