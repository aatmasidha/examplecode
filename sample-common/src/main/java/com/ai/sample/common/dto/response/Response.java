package com.ai.sample.common.dto.response;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class Response<K>{
	
	private static final Logger LOGGER = Logger.getLogger(Response.class);
	private static final boolean IS_DEBUGENABLED = LOGGER.isDebugEnabled();
	private ResponseStatus status=null;
	private String message=null;
	private Integer responseCode=null;
	private Object result=null;
	
			
	/** Parameterized constructor accepting <code>ResponseStatus</code>,
	 *  <code>String</code> message & <code>Integer</code> responseCode 
	 *  as parameters.
	 * 
	 * @param status
	 * @param message
	 * @param responseCode
	 */
	public Response(ResponseStatus  status, String message, Integer responseCode,K k) {
		super();
		
		if (IS_DEBUGENABLED) {
			LOGGER.debug(
					"Entering method Response(ResponseStatus  status, String message, Integer responseCode,K k) with parameters" 				
			+ status+ " " + message+ " " + responseCode+ " " + k );
		}
		
		this.status = status;
		this.message = message;
		this.responseCode = responseCode;
		
		if (IS_DEBUGENABLED) {
			LOGGER.debug(
					"Exiting method Response(ResponseStatus  status, String message, Integer responseCode,K k)");
		}		
		result=k;
		
	}

	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Integer getResponseCode() {
		return responseCode;
	}


	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}


	public String getStatus() {
		return status.getStatus();
	}


	public void setStatus(ResponseStatus status) {
		this.status = status;
	}


	public Object getResult() {
		return result;
	}


	public void setResult(Object result) {
		this.result = result;
	}
	

}
