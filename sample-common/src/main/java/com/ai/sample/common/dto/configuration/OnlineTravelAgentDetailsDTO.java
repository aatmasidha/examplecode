package com.ai.sample.common.dto.configuration;


public class OnlineTravelAgentDetailsDTO {
	private int id;
	private String otaName;
	private String otaDisplayName;

	public OnlineTravelAgentDetailsDTO() {
		super();
	}
	
	public OnlineTravelAgentDetailsDTO(String otaName, String otaDispalyName) {
		super();
		this.otaName = otaName;
		this.otaDisplayName = otaDispalyName;
	}

	public OnlineTravelAgentDetailsDTO(int id, String otaName,
			String otaDisplayName) {
		super();
		this.id = id;
		this.otaName = otaName;
		this.otaDisplayName = otaDisplayName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOtaName() {
		return otaName;
	}
	public void setOtaName(String otaName) {
		this.otaName = otaName;
	}
	
	
	public String getOtaDisplayName() {
		return otaDisplayName;
	}

	public void setOtaDisplayName(String otaDisplayName) {
		this.otaDisplayName = otaDisplayName;
	}

	@Override
	public String toString() {
		return "OnlineTravelAgentDetailsDTO [id=" + id + ", otaName=" + otaName
				+ ", otaDisplayName=" + otaDisplayName + "]";
	}
}
