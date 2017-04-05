package com.ai.sample.integration.rateshopping.DTO;

import com.google.gson.annotations.SerializedName;

public class AuthenticationTokenDetailsDTO {
	@SerializedName("access_token")
	private String token;
	
	@SerializedName("token_type")
	private String tokenType;
	
	@SerializedName("expires_in")
	private long timeToExpire;
	
	
	public AuthenticationTokenDetailsDTO(String token, String tokenType,
			long timeToExpire) {
		super();
		this.token = token;
		this.tokenType = tokenType;
		this.timeToExpire = timeToExpire;
	}
	
	public AuthenticationTokenDetailsDTO() {
		super();
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public long getTimeToExpire() {
		return timeToExpire;
	}
	public void setTimeToExpire(long timeToExpire) {
		this.timeToExpire = timeToExpire;
	}

	@Override
	public String toString() {
		return "AuthenticationTokenDetailsDTO [token=" + token + ", tokenType="
				+ tokenType + ", timeToExpire=" + timeToExpire + "]";
	}
}
