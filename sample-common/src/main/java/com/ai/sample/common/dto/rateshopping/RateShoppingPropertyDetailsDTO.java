package com.ai.sample.common.dto.rateshopping;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.OnlineTravelAgentDetailsDTO;

public class RateShoppingPropertyDetailsDTO {

	private PropertyDetailsDTO propertyDetails;
	private String rateShoppingPropertyUID;
	private OnlineTravelAgentDetailsDTO otaDetails;
	private String rateShoppingOtaID;
	private String rateShoppinEngineName;

	public RateShoppingPropertyDetailsDTO() {
		super();
	}

	public RateShoppingPropertyDetailsDTO(PropertyDetailsDTO propertyDetails,
			String rateShoppingPropertyUID, OnlineTravelAgentDetailsDTO otaDetails,
			String rateShoppingOtaID, String rateShoppingEngineName) {
		super();
		this.propertyDetails = propertyDetails;
		this.rateShoppingPropertyUID = rateShoppingPropertyUID;
		this.otaDetails = otaDetails;
		this.rateShoppingOtaID = rateShoppingOtaID;
		this.rateShoppinEngineName = rateShoppingEngineName;
	}
	
	public PropertyDetailsDTO getPropertyDetails() {
		return propertyDetails;
	}
	public void setPropertyDetails(PropertyDetailsDTO propertyDetails) {
		this.propertyDetails = propertyDetails;
	}
	public String getRateShoppingPropertyUID() {
		return rateShoppingPropertyUID;
	}
	public void setRateShoppingPropertyUID(String rateShoppingPropertyUID) {
		this.rateShoppingPropertyUID = rateShoppingPropertyUID;
	}
	public OnlineTravelAgentDetailsDTO getOtaDetails() {
		return otaDetails;
	}
	public void setOtaDetails(OnlineTravelAgentDetailsDTO otaDetails) {
		this.otaDetails = otaDetails;
	}
	public String getRateShoppingOtaID() {
		return rateShoppingOtaID;
	}
	public void setRateShoppingOtaID(String rateShoppingOtaID) {
		this.rateShoppingOtaID = rateShoppingOtaID;
	}

	
	public String getRateShoppinEngineName() {
		return rateShoppinEngineName;
	}

	public void setRateShoppinEngineName(String rateShoppinEngineName) {
		this.rateShoppinEngineName = rateShoppinEngineName;
	}

	@Override
	public String toString() {
		return "RateShoppingPropertyDetailsDTO [propertyDetails="
				+ propertyDetails + ", rateShoppingPropertyUID="
				+ rateShoppingPropertyUID + ", otaDetails=" + otaDetails
				+ ", rateShoppingOtaID=" + rateShoppingOtaID
				+ ", rateShoppinEngineName=" + rateShoppinEngineName + "]";
	}
}
