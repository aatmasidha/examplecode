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
import com.ai.sample.integration.rateshopping.DTO.PropertyRateDetailsDTO;
import com.ai.sample.integration.rateshopping.DTO.PropertyRateRequestDTO;
import com.ai.sample.integration.rateshopping.exception.RateShoppingDataException;
import com.ai.sample.integration.rateshopping.exception.RateShoppingConnectionExeption;
import com.ai.sample.integration.rateshopping.utils.ConnectionProperties;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class HotelRateThreadCSV extends Thread {
	static final Logger logger = Logger.getLogger(HotelRateThreadCSV.class);
	private Thread t;
	private PropertyRateRequestDTO hotelRequestDTO;
	private String hotelName;
	private String processDate;
	private String basePath;

	private static final String TAB_LINE_SEPARATOR = "\n";
	private static final Object[] FILE_HEADER = { "hotelname","hotelcode", "otacode",
			"dtcollected", "ratedate", "los", "checkindate", "checkoutdate", 
			"netrate","onsiterate","roomtype","ispromotional","ratetype", "discount","closed","currency" };
	private ApplicationContext context = null;

	private HttpClient client = HttpClientBuilder.create().build();

	public HotelRateThreadCSV(PropertyRateRequestDTO hotelRequestDTO, String hotelName, String processDate, ApplicationContext context) {		
		super(hotelRequestDTO.getCheckInDate());
		this.hotelRequestDTO = hotelRequestDTO;
		logger.info("Creating thread for" + hotelRequestDTO.toString());
		this.basePath = ConnectionProperties.getProperties().getProperty(
				"rateshoppingbasefolder");
		this.processDate = processDate;
		this.context = context;
		this.hotelName = hotelName;
	}
	
	public void run() {
		try {
			String filePath = basePath + File.separator
					+ hotelRequestDTO.getHotelCode() + File.separator
					+ processDate + File.separator;
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.mkdirs()) {
					logger.info(filePath + " : Directory is created!");
				} else {
					logger.error(filePath + "Failed to create directory!");
				}
			}

		/*	String jsonFile = filePath + hotelRequestDTO.getCheckInDate() + "_"
					+ hotelRequestDTO.getHotelCode() + ".json";*/
			Gson gson = new Gson();
			Type typeOfList = new TypeToken<List<PropertyRateDetailsDTO>>() {
			}.getType();

			String hotelRateRequestJson = gson.toJson(hotelRequestDTO);

			HttpResponse response = callRateShoppingHotelRateAPI(hotelRateRequestJson);
			int responseCode = response.getStatusLine().getStatusCode();
			boolean successCode = true;
			int retryTime = 0;
//			TODO here it should be while loop
//			since there is problem with retry temporarily changed to if
//			condition
			if(responseCode != 200 && retryTime < 3) {
				String errorDescription = response.getStatusLine()
						.getReasonPhrase();
				logger.error("Error occured while calling rates API is: "
						+ errorDescription);
				logger.error("Error processing rates API for date: "
						+ hotelRequestDTO.getCheckInDate());
				response = callRateShoppingHotelRateAPI(hotelRateRequestJson);
				responseCode = response.getStatusLine().getStatusCode();
				if(responseCode != 200)
					successCode =false;
				retryTime++;
			}
			if(successCode)
			{
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
	
				StringBuffer hotelRequestResponseData = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					hotelRequestResponseData.append(line);
				}
	
				if (hotelRequestResponseData != null) {
					List<PropertyRateDetailsDTO> hotelRatesDetailsDTOList = new Gson()
							.fromJson(hotelRequestResponseData.toString(),
									typeOfList);
				/*	FileWriter fileWriter = new FileWriter(jsonFile);
					gson.toJson(hotelRequestResponseData.toString(), fileWriter);
					fileWriter.close();				*/
					try {
						String csvFile = filePath + File.separator + "RateDetails_"+ hotelRequestDTO.getCheckInDate()  + ".csv";
//						FileWriter fileWriter = null;
						CSVPrinter csvFilePrinter = null;
						CSVFormat csvFileFormat = CSVFormat.DEFAULT
								.withRecordSeparator(TAB_LINE_SEPARATOR);

						FileWriter  fileWriter = new FileWriter(csvFile);
						// initialize CSVPrinter object
						csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
						// Create CSV file header
						csvFilePrinter.printRecord(FILE_HEADER);
						for (PropertyRateDetailsDTO hotelRateDetailsDTO : hotelRatesDetailsDTOList) {
							List<Object> hotelRateDetailsRecord = new ArrayList<>();
							hotelRateDetailsRecord.add(hotelName);
							hotelRateDetailsRecord.add(hotelRateDetailsDTO
									.getHotelCode());
							hotelRateDetailsRecord
									.add(hotelRateDetailsDTO.getOtaCode());
							hotelRateDetailsRecord.add(hotelRateDetailsDTO
									.getRateReceivedDate());
							hotelRateDetailsRecord.add(hotelRateDetailsDTO
									.getRateReceivedDate());
							hotelRateDetailsRecord.add(hotelRateDetailsDTO
									.getLengthOfStay());
							hotelRateDetailsRecord.add(hotelRateDetailsDTO
									.getCheckinDate());
							hotelRateDetailsRecord.add(hotelRateDetailsDTO
									.getCheckoutDate());
							hotelRateDetailsRecord.add(hotelRateDetailsDTO
									.getNetRate());
							hotelRateDetailsRecord.add(hotelRateDetailsDTO
									.getOnSiteRate());
							hotelRateDetailsRecord.add(hotelRateDetailsDTO.getRoomType());
							hotelRateDetailsRecord.add(hotelRateDetailsDTO.getIsPromotional());
							hotelRateDetailsRecord.add(hotelRateDetailsDTO.getRateTypeCode());
							hotelRateDetailsRecord.add(hotelRateDetailsDTO.getDiscount());
							hotelRateDetailsRecord.add(hotelRateDetailsDTO.getClosed());
							hotelRateDetailsRecord.add(hotelRateDetailsDTO.getCurrency());
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
			}
		} catch (IOException | RateShoppingDataException e) {
			logger.error("Thread " + hotelRequestDTO.toString()
					+ " interrupted.");
		} catch (Exception e) {
			logger.error("Exception is: " + e.getMessage());

		}
	}

	private HttpResponse callRateShoppingHotelRateAPI(String hotelRateRequestJson)
			throws RateShoppingDataException {
		logger.info("Running " + hotelRequestDTO);
		try {
			try {

				RateShoppingAuthenticationAPI rateAPI1 = new RateShoppingAuthenticationAPI();
				AuthenticationTokenDetailsDTO authenticationDetails = rateAPI1
						.getAuthenticationTokenDetails();

				Properties properties = ConnectionProperties.getProperties();

				String hotelURL = properties.getProperty("hotelrateurl",
						"defaultURL");

				HttpPost post = new HttpPost(hotelURL);
				post.setHeader("Content-Type", "application/json");
				post.setHeader("Authorization",
						authenticationDetails.getTokenType() + " "
								+ authenticationDetails.getToken());
				post.setEntity(new StringEntity(hotelRateRequestJson));

				HttpResponse response = client.execute(post);

				logger.debug("Thread " + hotelRequestDTO.toString()
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
		logger.debug("Starting " + hotelRequestDTO);
		if (t == null) {
			t = new Thread(this, hotelRequestDTO.getCheckInDate());
			t.start();
		}
	}

}
