package com.ai.sample.integration.rateshopping.dataapi.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.ai.sample.integration.rateshopping.DTO.AuthenticationTokenDetailsDTO;
import com.ai.sample.integration.rateshopping.exception.RateShoppingConnectionExeption;
import com.ai.sample.integration.rateshopping.utils.ConnectionProperties;
import com.google.gson.Gson;

public class RateShoppingAuthenticationAPI {
	static final Logger logger = Logger.getLogger(RateShoppingAuthenticationAPI.class);

	private HttpClient client = HttpClientBuilder.create().build();


	public AuthenticationTokenDetailsDTO getAuthenticationTokenDetails()
	 throws RateShoppingConnectionExeption {
		try {
			
			Properties properties = ConnectionProperties.getProperties();
			String authUrl = properties.getProperty("authurl","url");
			

			List<NameValuePair> postParams = new ArrayList<NameValuePair>();

			postParams.add(new BasicNameValuePair("grant_type", properties.getProperty("grant_type")));
			postParams.add(new BasicNameValuePair("username", properties.getProperty("username")));
			postParams.add(new BasicNameValuePair("password", properties.getProperty("password")));

			HttpPost post = new HttpPost(authUrl);

			// add header

			post.setHeader("Content-Type", "application/x-www-form-urlencoded");
			
			post.setEntity(new UrlEncodedFormEntity(postParams));

			HttpResponse response = client.execute(post);

			int responseCode = response.getStatusLine().getStatusCode();

			logger.debug("Sending 'POST' request to URL : " + authUrl);
			logger.debug("Post parameters : " + postParams);
			logger.debug("Response Code : " + responseCode);

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			AuthenticationTokenDetailsDTO tokenDetails = new Gson().fromJson(result.toString(), AuthenticationTokenDetailsDTO.class);
			logger.debug("Result for connection request is : " + tokenDetails.toString());
			
			return tokenDetails;
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException Exception in class RateShoppingAuthenticateAPI:  " + e.getMessage());
			throw new RateShoppingConnectionExeption(e);
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException Exception in class RateShoppingAuthenticateAPI:  " + e.getMessage());
			throw new RateShoppingConnectionExeption(e);
		} catch (IOException e) {
			logger.error("IOException Exception in class RateShoppingAuthenticateAPI:  " + e.getMessage());
			throw new RateShoppingConnectionExeption(e);
		}catch(Exception e){
			logger.error("Exception Exception in class RateShoppingAuthenticateAPI:  " + e.getMessage());
			throw new RateShoppingConnectionExeption(e.getMessage());
		}
	}
}
