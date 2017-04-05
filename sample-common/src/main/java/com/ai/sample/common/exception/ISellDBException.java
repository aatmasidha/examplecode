package com.ai.sample.common.exception;

public class ISellDBException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -132745959413644860L;
	
	int errorCode = -1;
	String cause = "";
	
	public ISellDBException(int errorCode, String cause) {
		super(cause);
		this.errorCode = errorCode;
		this.cause = cause;
	}

	public int getErrorCode() {
		return errorCode;
	}
	
}
