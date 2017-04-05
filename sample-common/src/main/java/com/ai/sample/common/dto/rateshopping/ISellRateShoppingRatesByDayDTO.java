package com.ai.sample.common.dto.rateshopping;


public class ISellRateShoppingRatesByDayDTO {
	int propertyDetailsID;
	String propertyName;
	float rate;
	boolean closed;

	public ISellRateShoppingRatesByDayDTO() {
		super();
	}

	public ISellRateShoppingRatesByDayDTO(int propertyDetailsID,
			String propertyName, float rate, boolean closed) {
		super();
		this.propertyDetailsID = propertyDetailsID;
		this.propertyName = propertyName;
		this.rate = rate;
		this.closed = closed;
	}

	public int getPropertyDetailsID() {
		return propertyDetailsID;
	}

	public void setPropertyDetailsID(int propertyDetailsID) {
		this.propertyDetailsID = propertyDetailsID;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	@Override
	public String toString() {
		return "ISellRateShoppingRatesByDayDTO [propertyDetailsID="
				+ propertyDetailsID + ", propertyName=" + propertyName
				+ ", rate=" + rate + ", closed=" + closed + "]";
	}
}
