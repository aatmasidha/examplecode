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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ai.sample.db.model.configuration.BookingStatusMaster;
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;



@Entity
@Table(name="booking_status_ota" ,  uniqueConstraints=
@UniqueConstraint(columnNames={"name", "bookingstatusmasterid","otadetailsid"}))
public class BookingStatusOta {
	private int id;
	private String name;
	
	@Column(name = "bookingstatusmasterid")
	private BookingStatusMaster bookingStatusMaster;
	
	@Column(name = "otadetailsid")
	private OnlineTravelAgentDetails otaDetails;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name =  "bookingstatusmasterid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public BookingStatusMaster getBookingStatusMaster() {
		return bookingStatusMaster;
	}

	public void setBookingStatusMaster(BookingStatusMaster bookingStatusMaster) {
		this.bookingStatusMaster = bookingStatusMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name =  "otadetailsid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public OnlineTravelAgentDetails getOtaDetails() {
		return otaDetails;
	}

	public void setOtaDetails(OnlineTravelAgentDetails otaDetails) {
		this.otaDetails = otaDetails;
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
		if (!(obj instanceof BookingStatusOta))
			return false;
		BookingStatusOta other = (BookingStatusOta) obj;
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
		return "BookingStatusOta [id=" + id + ", name=" + name
				+ ", bookingStatusMaster=" + bookingStatusMaster.toString()
				+ ", otaDetails=" + otaDetails.toString() + "]";
	}
}
