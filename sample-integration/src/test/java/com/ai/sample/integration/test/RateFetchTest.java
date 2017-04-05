package com.ai.sample.integration.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.service.property.configuration.PropertyDetailsService;
import com.ai.sample.db.service.rateshopping.RateShoppingPropertyDetailsMappingService;
import com.ai.sample.integration.rateshopping.DTO.PropertyRateRequestDTO;
import com.ai.sample.integration.rateshopping.dataapi.test.HotelRateThread;

public class RateFetchTest {
	static Logger logger = Logger.getLogger(RateFetchTest.class);
	
	public static void main(String[] args) {
		RateFetchTest restFetchTest = new RateFetchTest();
		restFetchTest.fetchAllRates(90);
	}
	
	public void fetchAllRates(int reqNumDays)
	{
		ApplicationContext context = 
	            new ClassPathXmlApplicationContext("config.xml");
		try {
			SimpleDateFormat checkinDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			String checkInDateString = checkinDateFormat.format(new Date());
			String processDate = checkInDateString;
			Thread threadArray[] = new Thread[90];
			PropertyDetailsService propertyDetailsService = context.getBean(PropertyDetailsService.class);
			 List<PropertyDetailsDTO> propertyDetailsDTOList = propertyDetailsService.findAllHotelPropertyDetails();
			 
			 RateShoppingPropertyDetailsMappingService rateShoppingPropertyDetailsMappingService = context.getBean(RateShoppingPropertyDetailsMappingService.class);
			
			Map<String, RateShoppingPropertyDetailsDTO> rateShoppingMap =  rateShoppingPropertyDetailsMappingService.getAllRateShoppingDetailsPerProperty(propertyDetailsDTOList.get(0));
			
			if(rateShoppingMap == null || rateShoppingMap.size() <= 0 )
			{
				throw new ISellDBException(-1 , "No data for rate shopping is defined. RateShoppping data like OTA and "
						+ "hotel code is must to execute the API....");
			}
			for(int cnt = 0; cnt < propertyDetailsDTOList.size(); cnt++)
			{
				PropertyDetailsDTO propertyDetailsDTO = propertyDetailsDTOList.get(cnt);
				logger.info("Fetching rates for property: " + propertyDetailsDTO.getPropertyCode() + propertyDetailsDTO.getName());
//				String hotelCode = "";
				for (int numDays = 0; numDays < reqNumDays; numDays++) {
					PropertyRateRequestDTO hotelRequestDTO = new PropertyRateRequestDTO();
//					TODO - Get hotel code from the data PropertyDetails does not have hotel
//					code as it is associated with the rate shopping engine.
					
						RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO = rateShoppingMap.get(propertyDetailsDTO.getName());
						hotelRequestDTO.setHotelCode(rateShoppingPropertyDetailsDTO.getRateShoppingPropertyUID());
						hotelRequestDTO.setOtaName(rateShoppingPropertyDetailsDTO.getRateShoppingOtaID());
						hotelRequestDTO.setCurrency("INR");
						hotelRequestDTO.setCheckInDate(checkInDateString);
						Date checkInDate = checkinDateFormat.parse(hotelRequestDTO
								.getCheckInDate());	
					Date checkoutDate = new Date(checkInDate.getTime()
							+ (1000 * 60 * 60 * 24));
					hotelRequestDTO.setCheckOutDate(checkinDateFormat
							.format(checkoutDate));
	
					HotelRateThread hoteRateThread = new HotelRateThread(
							hotelRequestDTO, processDate, context);
					threadArray[numDays] = hoteRateThread;
					threadArray[numDays].start();
					threadArray[numDays].join();
					checkInDateString = hotelRequestDTO.getCheckOutDate();
					Thread.sleep(500);
				}
				while( Thread.activeCount() > 1)
				{
					Thread.sleep(500);
				}
			
			}
			
			//TODO check where to close the context this has to be
			//changed as child thread are executing but needs to be
			//close after all child threads are terminated
		//	((ClassPathXmlApplicationContext) context).close();
		} catch (InterruptedException  e) {
			logger.error("InterruptedException in RateFetchTest: " + e.getMessage());
		}catch(ISellDBException e)
		{
			logger.error("ISellDBException while fetching the rates for property is: " + e.getMessage());
		} catch (ParseException e) {
			logger.error("ParseException in RateFetchTest: " + e.getMessage());
		}
		finally
		{
			((ClassPathXmlApplicationContext) context).close();
		}
	}
	

}
