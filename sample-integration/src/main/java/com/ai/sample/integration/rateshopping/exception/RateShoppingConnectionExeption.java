package com.ai.sample.integration.rateshopping.exception;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;

public class RateShoppingConnectionExeption extends Exception {

	public RateShoppingConnectionExeption(UnsupportedEncodingException e) {
		super(e.getMessage());
	}

	public RateShoppingConnectionExeption(ClientProtocolException e) {
		super(e.getMessage());
	}

	public RateShoppingConnectionExeption(IOException e) {
		super(e.getMessage());
	}

	public RateShoppingConnectionExeption(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5781191982447029328L;

}
