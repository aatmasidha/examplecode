package com.ai.sample.integration.rateshopping.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.service.property.configuration.PropertyDetailsService;
import com.ai.sample.db.service.rateshopping.RateShoppingPropertyDetailsMappingService;
import com.ai.sample.integration.rateshopping.DTO.PropertyRateRequestDTO;
import com.ai.sample.integration.rateshopping.dataapi.test.HotelRateThread;

public class SinglePropertyRateFetch {
	static Logger logger = Logger.getLogger(SinglePropertyRateFetch.class);

	public static void main(String[] args) {

		try {
			SinglePropertyRateFetchOptionsBuilder optionsBuilder = new SinglePropertyRateFetchOptionsBuilder();

			CommandLineParser parser = new GnuParser();
			CommandLine line = parser.parse(optionsBuilder.getOptions(), args);
			SinglePropertyRateFetchParams params = new SinglePropertyRateFetchParams(
					line);

			SinglePropertyRateFetch restFetchTest = new SinglePropertyRateFetch();
			PropertyDetailsDTO propertyDetailsDTO = new PropertyDetailsDTO();
			CityDTO cityDto = new CityDTO(params.getCityName(),
					params.getStateName(), params.getCountryName());

			propertyDetailsDTO.setCityDto(cityDto);
			propertyDetailsDTO.setName(params.getPropertyName());
			// propertyDetailsDto.setName("Hotel Bhooshan");
			
				
			restFetchTest.fetchAllRates(propertyDetailsDTO,
					params.getNumDays(), params.getBusinessDate(),
					params.getOtaName(), params.getCurrency() );
		} catch (org.apache.commons.cli.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void fetchAllRates(PropertyDetailsDTO propertyDetailsDto,
			String numDays, String businessDateString, String otaName, String currency) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"config.xml");
		try {
			SimpleDateFormat checkinDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			
			if(numDays == null || numDays.length() <= 0)
			{
				numDays = 90+"";
			}
			int reqNumDays = Integer.parseInt(numDays);
			String checkInDateString = null;
			checkInDateString = businessDateString;
			if (checkInDateString == null || checkInDateString.length() <= 0) {
				checkInDateString = checkinDateFormat.format(new Date());
			}
			String processDate = checkInDateString;
			Thread threadArray[] = new Thread[reqNumDays];
			PropertyDetailsService propertyDetailsService = context
					.getBean(PropertyDetailsService.class);

			propertyDetailsDto = propertyDetailsService
					.findPropertyDetailsByPropertyInformation(propertyDetailsDto);

			RateShoppingPropertyDetailsMappingService rateShoppingPropertyDetailsMappingService = context
					.getBean(RateShoppingPropertyDetailsMappingService.class);
		/*	RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO = rateShoppingPropertyDetailsMappingService
					.findRateShoppingDetailsForPropertyByPropertyCode(
							"96338"  "97395" , "2");*/
			RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO = rateShoppingPropertyDetailsMappingService
					.findRateShoppingDetailsForPropertyByPropertyCode(
							propertyDetailsDto , otaName);
			if (rateShoppingPropertyDetailsDTO == null) {
				throw new ISellDBException(-1,
						"No data for rate shopping is defined. RateShoppping data like OTA and "
								+ "hotel code is must to execute the API....");
			}
			propertyDetailsDto = rateShoppingPropertyDetailsDTO
					.getPropertyDetails();
			logger.info("Fetching rates for property: "
					+ propertyDetailsDto.getPropertyCode() + " : " 
					+ propertyDetailsDto.getName());

			int activeThreads = Thread.activeCount();
			 
			for (int days = 0; days < reqNumDays; days++) {
				PropertyRateRequestDTO hotelRequestDTO = new PropertyRateRequestDTO();
				// TODO - Get hotel code from the data PropertyDetails does not
				// have hotel
				// code as it is associated with the rate shopping engine.

				// RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO
				// = rateShoppingMap.get(propertyDetailsDTO.getName());
				hotelRequestDTO.setHotelCode(rateShoppingPropertyDetailsDTO
						.getRateShoppingPropertyUID());
				hotelRequestDTO.setOtaName(rateShoppingPropertyDetailsDTO
						.getRateShoppingOtaID());
				if( currency != null )
				{
					propertyDetailsDto.setHotelCurrency(currency);
				}
				hotelRequestDTO.setCurrency(propertyDetailsDto.getHotelCurrency());
				hotelRequestDTO.setCheckInDate(checkInDateString);
				Date checkInDate = checkinDateFormat.parse(hotelRequestDTO
						.getCheckInDate());
				Date checkoutDate = new Date(checkInDate.getTime()
						+ (1000 * 60 * 60 * 24));
				hotelRequestDTO.setCheckOutDate(checkinDateFormat
						.format(checkoutDate));

				HotelRateThread hoteRateThread = new HotelRateThread(
						hotelRequestDTO, processDate, context);
				threadArray[days] = hoteRateThread;
				threadArray[days].start();
				threadArray[days].join();
				checkInDateString = hotelRequestDTO.getCheckOutDate();
			}

			while (Thread.activeCount() > activeThreads) {
				Thread.sleep(500);
			}

			// TODO check where to close the context this has to be
			// changed as child thread are executing but needs to be
			// close after all child threads are terminated
			// ((ClassPathXmlApplicationContext) context).close();
		} catch (InterruptedException e) {
			logger.error("InterruptedException in RateFetchTest: "
					+ e.getMessage());
		} catch (ISellDBException e) {
			logger.error("ISellDBException while fetching the rates for property is: "
					+ e.getMessage());
		} catch (ParseException e) {
			logger.error("ParseException in RateFetchTest: " + e.getMessage());
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
	}

}
