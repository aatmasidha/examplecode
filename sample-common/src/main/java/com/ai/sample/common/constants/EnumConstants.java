package com.ai.sample.common.constants;

public class EnumConstants {

	public enum StateConstant
	{
		ACTIVE(true), INACTIVE(false);

		private boolean state;

		private StateConstant(boolean state) {
	        this.state = state;
	    }
		
		public boolean getsState() {
			return state;
		}

	}
	
	public enum HotelStateConstant
	{
		LIVE("live"), INACTIVE("InActive");

		private String state;

		private HotelStateConstant(String state) {
	        this.state = state;
	    }
		
		public String getsState() {
			return state;
		}

	}
	
	public enum ExecutionStatusDescConstant
	{
		SUCCESS("SUCCESS"), FAILURE("FAILURE");

		private String status;

		private ExecutionStatusDescConstant(String status) {
	        this.status = status;
	    }
		
		public String getStatus() {
			return status;
		}
	}
	
	public enum ExecutionStatusCodeConstant
	{
		SUCCESS(0), EXCEPTION(-1), DATAERROR(1);

		private int code;

		private ExecutionStatusCodeConstant(int code) {
	        this.code = code;
	    }
		
		public int getCode() {
			return code;
		}
	}
}
