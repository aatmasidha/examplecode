package com.ai.sample.integration.rateshopping.exception;

import java.io.IOException;
import java.text.ParseException;

import org.apache.http.client.ClientProtocolException;

public class RateShoppingDataException extends Exception{

	public RateShoppingDataException(String string) {
		super(string);
	}

	public RateShoppingDataException(ClientProtocolException e) {
		super(e.getMessage());
	}

	public RateShoppingDataException(IOException e) {
		super(e.getMessage());
	}

	public RateShoppingDataException(ParseException e) {
		super(e.getMessage());
	}

	public RateShoppingDataException(InterruptedException e) {
		super(e.getMessage());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3125945822779140766L;

}
