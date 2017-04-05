package com.ai.sample.integration.rateshopping.dataapi.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ai.sample.integration.rateshopping.DTO.AuthenticationTokenDetailsDTO;
import com.ai.sample.integration.rateshopping.DTO.PropertyInformationDetailsDTO;
import com.ai.sample.integration.rateshopping.DTO.PropertyInformationRequestDetailsDTO;
import com.ai.sample.integration.rateshopping.exception.RateShoppingDataException;
import com.ai.sample.integration.rateshopping.exception.RateShoppingConnectionExeption;
import com.ai.sample.integration.rateshopping.utils.ConnectionProperties;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class PropertyDetailsThreadCSV extends Thread {
	static final Logger logger = Logger.getLogger(PropertyDetailsThreadCSV.class);
	private Thread t;
	private PropertyInformationRequestDetailsDTO propertyRequestDTO;
	private String processDate;
	private String basePath;
	private ApplicationContext context = null;
	
	private static final String TAB_LINE_SEPARATOR = "\n";
	private static final Object[] FILE_HEADER = { "hotelCode", "hotelName",
			"hotelGroup", "address", "city", "state", "country", "zip", "lat", "lng", "rating", 
			"status","currencycode"};


	private HttpClient client = HttpClientBuilder.create().build();

	public PropertyDetailsThreadCSV(PropertyInformationRequestDetailsDTO propertyRequestDTO, String processDate, ApplicationContext context) {		
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

//			String jsonFile = filePath + File.separator + "propertydetails.json";
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
				List<PropertyInformationDetailsDTO> hotelInformationDetailsDTOList = new Gson()
						.fromJson(hotelRequestResponseData.toString(),
								typeOfList);
				String csvFile = filePath + File.separator + "PropertyDetails_"+ processDate + ".csv";
				FileWriter fileWriter = null;
				CSVPrinter csvFilePrinter = null;
				
				CSVFormat csvFileFormat = CSVFormat.DEFAULT
						.withRecordSeparator(TAB_LINE_SEPARATOR);

				fileWriter = new FileWriter(csvFile);
				
				
					try {
						

						// initialize CSVPrinter object
						csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
						// Create CSV file header
						csvFilePrinter.printRecord(FILE_HEADER);
						for (PropertyInformationDetailsDTO propertyInformationDetailsDTO : hotelInformationDetailsDTOList) {
							List<Object> hotelRateDetailsRecord = new ArrayList<>();
							hotelRateDetailsRecord.add(propertyInformationDetailsDTO
									.getHotelCode());
							hotelRateDetailsRecord
									.add(propertyInformationDetailsDTO.getHotelName());
							hotelRateDetailsRecord.add(propertyInformationDetailsDTO
									.getHotelGroup());
							hotelRateDetailsRecord.add(propertyInformationDetailsDTO
									.getAddress());
							hotelRateDetailsRecord.add(propertyInformationDetailsDTO
									.getCity());
							hotelRateDetailsRecord.add(propertyInformationDetailsDTO
									.getState());
							hotelRateDetailsRecord.add(propertyInformationDetailsDTO
									.getCountry());
							hotelRateDetailsRecord.add(propertyInformationDetailsDTO
									.getZip());
							hotelRateDetailsRecord.add(propertyInformationDetailsDTO
									.getLatitude());
							hotelRateDetailsRecord.add(propertyInformationDetailsDTO
									.getLongitude());
							
							/*hotelRateDetailsRecord.add(propertyInformationDetailsDTO
									.getRating());*/
							hotelRateDetailsRecord.add(propertyInformationDetailsDTO
									.getStatus());
							/*hotelRateDetailsRecord.add(propertyInformationDetailsDTO
									.getCurrenyCode());*/
							csvFilePrinter.printRecord(hotelRateDetailsRecord);
						}
						fileWriter.flush();
						fileWriter.close();
						csvFilePrinter.close();
					} catch (IOException e) {
						System.out
								.println("Error while flushing/closing fileWriter/csvPrinter !!!");
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
