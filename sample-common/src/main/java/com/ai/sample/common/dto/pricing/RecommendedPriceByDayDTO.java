package com.ai.sample.common.dto.pricing;

import java.util.Date;

public class RecommendedPriceByDayDTO {
	private Date checkindate;
	private float price;
	
	public RecommendedPriceByDayDTO() {
		super();
	}
	
	public RecommendedPriceByDayDTO(Date checkindate, float price) {
		super();
		this.checkindate = checkindate;
		this.price = price;
	}
	public Date getCheckindate() {
		return checkindate;
	}
	public void setCheckindate(Date checkindate) {
		this.checkindate = checkindate;
	}

	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "RecommendedPriceByDayDTO [checkindate=" + checkindate
				+ ", price=" + price + "]";
	}
}
