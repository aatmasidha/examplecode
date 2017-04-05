package com.ai.sample.integration.rateshopping.dataapi.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ai.sample.common.dto.rateshopping.RateShoppingRatesByDayDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.service.RateShoppingRatesByDayService;
import com.ai.sample.integration.rateshopping.DTO.AuthenticationTokenDetailsDTO;
import com.ai.sample.integration.rateshopping.DTO.PropertyRateDetailsDTO;
import com.ai.sample.integration.rateshopping.DTO.PropertyRateRequestDTO;
import com.ai.sample.integration.rateshopping.exception.RateShoppingDataException;
import com.ai.sample.integration.rateshopping.exception.RateShoppingConnectionExeption;
import com.ai.sample.integration.rateshopping.utils.ConnectionProperties;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.json.impl.reader.JsonFormatException;

@Component
public class HotelRateThread extends Thread {
	static final Logger logger = Logger.getLogger(HotelRateThread.class);
	private Thread t;
	private PropertyRateRequestDTO hotelRequestDTO;
	private String processDate;
	private String basePath;
	private ApplicationContext context;

	private HttpClient client = HttpClientBuilder.create().build();

	public HotelRateThread(PropertyRateRequestDTO hotelRequestDTO, String processDate, ApplicationContext context) {		
		super(hotelRequestDTO.getCheckInDate());
		this.hotelRequestDTO = hotelRequestDTO;
		logger.info("Creating thread for" + hotelRequestDTO.toString());
		this.basePath = ConnectionProperties.getProperties().getProperty(
				"rateshoppingbasefolder");
		this.processDate = processDate;
		this.context = context;
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

			String jsonFile = filePath + hotelRequestDTO.getCheckInDate() + "_"
					+ hotelRequestDTO.getHotelCode() + ".json";
			Gson gson = new Gson();
			Type typeOfList = new TypeToken<List<PropertyRateDetailsDTO>>() {
			}.getType();

			String hotelRateRequestJson = gson.toJson(hotelRequestDTO);

			HttpResponse response = callRateShoppingHotelRateAPI(hotelRateRequestJson);
			int responseCode = response.getStatusLine().getStatusCode();
			boolean successCode = true;
			int retryTime = 3;
//			TODO here it should be while loop
//			since there is problem with retry temporarily changed to if
//			condition
			if(responseCode != 200 && retryTime < 0) {
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
				retryTime--;
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
					FileWriter fileWriter = new FileWriter(jsonFile);
					gson.toJson(hotelRequestResponseData.toString(), fileWriter);
					fileWriter.close();
	
					for (int cnt = 0; cnt < hotelRatesDetailsDTOList.size(); cnt++) {
						PropertyRateDetailsDTO hotelRateDetails = hotelRatesDetailsDTOList
								.get(cnt);
						if( hotelRateDetails.getRoomType() == null ||hotelRateDetails.getRoomType().length() <= 0)
						{
							hotelRateDetails.setRoomType("Hotel");
						}
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						boolean isPromo = false;
						boolean isClosed = false;
						float onSiteRate = Float.parseFloat(hotelRateDetails.getOnSiteRate());
						float netRate = Float.parseFloat(hotelRateDetails.getNetRate());
						
						if(netRate == 0)
						{
							netRate = onSiteRate;
						}
								if( hotelRateDetails.getIsPromotional().equals("Y") )
								{
									isPromo = true;
								}
								
								if( hotelRateDetails.getClosed().equals("Y") )
								{
									isClosed = true;
								}
								RateShoppingRatesByDayDTO rateShoppingRatesByDayDTO = new RateShoppingRatesByDayDTO(
//								hotelRateDetails.getHotelCode(), "2",
								hotelRateDetails.getHotelCode(), hotelRequestDTO.getOtaName(),
								hotelRateDetails.getRateReceivedDate(),
								hotelRateDetails.getRateDefinedFor(),
								hotelRateDetails.getLengthOfStay(),
								hotelRateDetails.getNumGuests(),
								hotelRateDetails.getRoomType(),
								Double.parseDouble(hotelRateDetails.getOnSiteRate()),
								formatter.parse(hotelRateDetails.getCheckinDate()),
								formatter.parse(hotelRateDetails.getCheckoutDate()),
								Double.parseDouble(hotelRateDetails.getNetRate()),
								isPromo,
								isClosed,
								hotelRateDetails.getDiscount(),
								hotelRateDetails.getRateTypeCode(),
								hotelRateDetails.getRateTypeDescription());
								RateShoppingRatesByDayService rateShopping = context.getBean(RateShoppingRatesByDayService.class);
						rateShopping.saveRateShoppingRatesByDay(rateShoppingRatesByDayDTO);
						
					}
				}
			}
		} catch (IOException | RateShoppingDataException| JsonFormatException | JsonSyntaxException |IllegalStateException e) {
			logger.error("Thread " + hotelRequestDTO.toString()
					+ " interrupted.");
		} catch (ISellDBException | ParseException  e) {
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
