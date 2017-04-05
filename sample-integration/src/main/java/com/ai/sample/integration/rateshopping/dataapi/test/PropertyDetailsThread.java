package com.ai.sample.integration.rateshopping.dataapi.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ai.sample.common.constants.EnumConstants.HotelStateConstant;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.service.RateShoppingPropertyDetailsService;
import com.ai.sample.integration.rateshopping.DTO.AuthenticationTokenDetailsDTO;
import com.ai.sample.integration.rateshopping.DTO.PropertyInformationDetailsDTO;
import com.ai.sample.integration.rateshopping.DTO.PropertyInformationRequestDetailsDTO;
import com.ai.sample.integration.rateshopping.exception.RateShoppingDataException;
import com.ai.sample.integration.rateshopping.exception.RateShoppingConnectionExeption;
import com.ai.sample.integration.rateshopping.utils.ConnectionProperties;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class PropertyDetailsThread extends Thread {
	static final Logger logger = Logger.getLogger(PropertyDetailsThread.class);
	private Thread t;
	private PropertyInformationRequestDetailsDTO propertyRequestDTO;
	private String processDate;
	private String basePath;
	private ApplicationContext context;

	private HttpClient client = HttpClientBuilder.create().build();

	public PropertyDetailsThread(PropertyInformationRequestDetailsDTO propertyRequestDTO, String processDate, ApplicationContext context) {		
		this.propertyRequestDTO = propertyRequestDTO;
		logger.info("Creating thread for" + propertyRequestDTO.toString());
		this.basePath = ConnectionProperties.getProperties().getProperty(
				"rateshoppingbasefolder");
		this.processDate = processDate;
		this.context = context;
	}

	public void run() {
		try {
			String filePath = basePath + File.separator
					+ "PropertyDetails" + File.separator + processDate;
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.mkdirs()) {
					logger.info(filePath + " : Directory is created!");
				} else {
					logger.error(filePath + "Failed to create directory!");
				}
			}

			String jsonFile = filePath + File.separator + "propertydetails.json";
			Gson gson = new Gson();
			Type typeOfList = new TypeToken<List<PropertyInformationDetailsDTO>>() {
			}.getType();

			String hotelRateRequestJson = gson.toJson(propertyRequestDTO);

			HttpResponse response = callRateShoppingPropertyDetailsAPI(hotelRateRequestJson);
			int responseCode = response.getStatusLine().getStatusCode();
			int retryTime = 0;
			if (responseCode != 200 && retryTime < 3) {
				String errorDescription = response.getStatusLine()
						.getReasonPhrase();
				logger.error("Error occured while calling rates API is: "
						+ errorDescription);
				logger.error("Error processing property details request for filters: "
						+ propertyRequestDTO.toString());
				response = callRateShoppingPropertyDetailsAPI(hotelRateRequestJson);
				responseCode = response.getStatusLine().getStatusCode();
				retryTime++;
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer hotelRequestResponseData = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				hotelRequestResponseData.append(line);
			}

			if (hotelRequestResponseData != null) {
				List<PropertyInformationDetailsDTO> hotelRatesDetailsDTOList = new Gson()
						.fromJson(hotelRequestResponseData.toString(),
								typeOfList);
				FileWriter fileWriter = new FileWriter(jsonFile);
				gson.toJson(hotelRequestResponseData.toString(), fileWriter);
				fileWriter.close();

				for (int cnt = 0; cnt < hotelRatesDetailsDTOList.size(); cnt++) {
					PropertyInformationDetailsDTO propertyInformationDetails = hotelRatesDetailsDTOList
							.get(cnt);
				
					logger.debug("PropertyDetails: " +propertyInformationDetails.toString());

					try
					{
						Date creationDate = new Date();
						double latitude = 0;
						double longitude = 0;
						if(propertyInformationDetails.getLatitude()!= null && propertyInformationDetails.getLatitude().length() > 0)
							latitude = Double.parseDouble(propertyInformationDetails.getLatitude());
						
						if(propertyInformationDetails.getLongitude()!= null && propertyInformationDetails.getLongitude().length() > 0)
							longitude = Double.parseDouble(propertyInformationDetails.getLongitude());
						
						String hotelStatus = propertyInformationDetails.getStatus();
						if(hotelStatus == null || hotelStatus.length() <= 0)
						{
							propertyInformationDetails.setStatus(HotelStateConstant.LIVE.getsState());
						}
	//					PropertyDetailsService propertyDetailsService = context.getBean(PropertyDetailsService.class);
						RateShoppingPropertyDetailsService rateShoppingPropertyDetailsMappingService = context.getBean(RateShoppingPropertyDetailsService.class);
						RateShoppingPropertyDTO rateShoppingPropertyDTO = new RateShoppingPropertyDTO(propertyInformationDetails.getHotelName(), propertyInformationDetails.getHotelCode(), propertyInformationDetails.getHotelGroup(), 
								propertyInformationDetails.getAddress(), propertyInformationDetails.getCountry(), propertyInformationDetails.getState(), propertyInformationDetails.getCity(), propertyInformationDetails.getZip(),0,
								/*propertyInformationDetails.getRating(),*/ hotelStatus, 
								/*propertyInformationDetails.getCurrenyCode(),*/ longitude, latitude, null,creationDate,creationDate);
						
						rateShoppingPropertyDetailsMappingService.saveOrUpdateRateShoppingRateShoppingPropertyDetails(rateShoppingPropertyDTO);
					}
					catch(HibernateException e )
					{
						logger.error("Hibernate Exception in PropertyDetailsThread " + e.getMessage()) ;
						throw new ISellDBException(-1, e.getMessage());
					}
					catch(RuntimeException e )
					{
						logger.error("RuntimeException Exception in PropertyDetailsThread " + e.getMessage()) ;
						throw new ISellDBException(-1, e.getMessage());
					}
				}
		}
		} catch (IOException | RateShoppingDataException e) {
			logger.error("Thread " + propertyRequestDTO.toString()
					+ " interrupted.");
		} catch (Exception e) {
			logger.error("Exception is: " + e.getMessage());
		}
	}

	private HttpResponse callRateShoppingPropertyDetailsAPI(String propertyInformationRequestJson)
			throws RateShoppingDataException {
		logger.info("Running " + propertyRequestDTO);
		try {
			try {

				RateShoppingAuthenticationAPI rateAPI1 = new RateShoppingAuthenticationAPI();
				AuthenticationTokenDetailsDTO authenticationDetails = rateAPI1
						.getAuthenticationTokenDetails();

				Properties properties = ConnectionProperties.getProperties();

				String hotelURL = properties.getProperty("hotelinfourl",
						"defaultURL");

				HttpPost post = new HttpPost(hotelURL);
				post.setHeader("Content-Type", "application/json");
				post.setHeader("Authorization",
						authenticationDetails.getTokenType() + " "
								+ authenticationDetails.getToken());
				post.setEntity(new StringEntity(propertyInformationRequestJson));

				HttpResponse response = client.execute(post);

				logger.debug("Thread " + propertyRequestDTO.toString()
						+ " exiting.");
				return response;
			} catch (UnsupportedEncodingException e) {
				logger.error("UnsupportedEncodingException Exception in class HotelInformationDataRequest:  "
						+ e.getMessage());
				throw new RateShoppingDataException(e);
			} catch (ClientProtocolException e) {
				logger.error("ClientProtocolException Exception in class HotelInformationDataRequest:  "
						+ e.getMessage());
				throw new RateShoppingDataException(e);
			} catch (IOException e) {
				logger.error("IOException Exception in class HotelInformationDataRequest:  "
						+ e.getMessage());
				throw new RateShoppingDataException(e);
			}
			catch (RateShoppingConnectionExeption e) {
				logger.error("RateShoppingConnectionExeption Exception in class HotelInformationDataRequest:  "
						+ e.getMessage());
				throw new RateShoppingDataException(e.getMessage());
			}

		} catch (RateShoppingDataException e) {
			throw new RateShoppingDataException(e.getMessage());
		}
	}

	public void start() {
		logger.debug("Starting " + propertyRequestDTO);
		if (t == null) {
			t = new Thread(this, "PropertyDetails");
			t.start();
		}
	}

}
