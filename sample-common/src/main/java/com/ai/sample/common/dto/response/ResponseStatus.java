package com.ai.sample.common.dto.response;

public enum ResponseStatus {
	FAILURE("FAILURE"),SUCCESS("SUCCESS"),ERROR("ERROR");
	
	private String status;
	
	private ResponseStatus(String responseStatus){
		status=responseStatus;
	}
	
	public String getStatus(){
		return status;
				
	}
}
