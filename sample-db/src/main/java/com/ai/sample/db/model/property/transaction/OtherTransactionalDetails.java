package com.ai.sample.db.model.property.transaction;

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

import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;
import com.ai.sample.db.type.MyJson;
import com.ai.sample.db.type.StringJsonUserType;



@Entity
@Table(name="other_transactional_details",  uniqueConstraints=
@UniqueConstraint(columnNames={"transactiondetailsid", "propertyroomtypeid"}))

@TypeDefs({ @TypeDef(name = "CustomJsonObject", typeClass = StringJsonUserType.class) })

public class OtherTransactionalDetails {
	private int id;
	private TransactionalDetails transactionalDetails;
	private PropertyRoomTypeMapping roomType;

	private float roomRevenue;
	
	@Type(type = "CustomJsonObject" )
	@Column(name = "revenuebyroomtype")
	public MyJson revenueByRoomType;
	
	@Type(type = "CustomJsonObject" )
	@Column(name = "additionaldetails")
	public MyJson additionalDetails;
	
	
	
	public OtherTransactionalDetails() {
		super();
	}

	
	public OtherTransactionalDetails(TransactionalDetails transactionalDetails,
			PropertyRoomTypeMapping roomType, float roomRevenue, MyJson revenueByRoomType,
			MyJson additionalDetails) {
		super();
		this.roomRevenue = roomRevenue;
		this.transactionalDetails = transactionalDetails;
		this.roomType = roomType;
		this.revenueByRoomType = revenueByRoomType;
		this.additionalDetails = additionalDetails;
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


	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "transactiondetailsid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public TransactionalDetails getTransactionalDetails() {
		return transactionalDetails;
	}

	public void setTransactionalDetails(TransactionalDetails transactionalDetails) {
		this.transactionalDetails = transactionalDetails;
	}

	
	public float getRoomRevenue() {
		return roomRevenue;
	}


	public void setRoomRevenue(float roomRevenue) {
		this.roomRevenue = roomRevenue;
	}


	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "propertyroomtypeid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public PropertyRoomTypeMapping getRoomType() {
		return roomType;
	}

	public void setRoomType(PropertyRoomTypeMapping roomType) {
		this.roomType = roomType;
	}

	@Type(type = "CustomJsonObject")
	@Column(name = "revenuebyroomtype", nullable = true)
	public MyJson getRevenueByRoomType() {
		return revenueByRoomType;
	}

	public void setRevenueByRoomType(MyJson revenueByRoomType) {
		this.revenueByRoomType = revenueByRoomType;
	}

	@Type(type = "CustomJsonObject")
	@Column(name = "additionaldetails", nullable = true)
	public MyJson getAdditionalDetails() {
		return additionalDetails;
	}

	

	public void setAdditionalDetails(MyJson additionalDetails) {
		this.additionalDetails = additionalDetails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((transactionalDetails == null) ? 0 : transactionalDetails.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OtherTransactionalDetails))
			return false;
		OtherTransactionalDetails other = (OtherTransactionalDetails) obj;
		if (id != other.id)
			return false;
		if (transactionalDetails == null) {
			if (other.transactionalDetails != null)
				return false;
		} else if (!transactionalDetails.equals(other.transactionalDetails))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OtherTransactionalDetails [id=" + id
				+ ", transactionalDetails=" + transactionalDetails
				+ ", roomType=" + roomType + ", roomRevenue=" + roomRevenue
				+ ", revenueByRoomType=" + revenueByRoomType
				+ ", additionalDetails=" + additionalDetails + "]";
	}

}
