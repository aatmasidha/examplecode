package com.ai.sample.common.exception;

public class ISellProcessingException  extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3764173648789192029L;
	
	int errorCode = -1;
	String cause = "";
	
	public ISellProcessingException(int errorCode, String cause) {
		super(cause);
		this.errorCode = errorCode;
		this.cause = cause;
	}
}
