package com.ai.sample.common.dto.ui;

public class ExecutionStatusDTO {

	private int statusCode;
	private String statusDescription;
 	
 	public ExecutionStatusDTO() {
		super();
	}
 
	public ExecutionStatusDTO(int statusCode, String statusDescription) {
		super();
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	@Override
	public String toString() {
		return "ExecutionStatusDTO [statusCode=" + statusCode
				+ ", statusDescription=" + statusDescription + "]";
	}
}
