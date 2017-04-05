package com.ai.sample.integration.rateshopping.dataapi.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.sample.integration.rateshopping.DTO.PropertyInformationRequestDetailsDTO;

public class TestWriteJSON {
	static final Logger logger = Logger.getLogger(HotelRateThread.class);
	public static void main(String args[]) {
		
		ApplicationContext context = 
	            new ClassPathXmlApplicationContext("config.xml");
		try {
			SimpleDateFormat checkinDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");

			String checkInDateString = checkinDateFormat.format(new Date());
			String processDate = checkInDateString;
//			String hotelCode = "96338";
			
			PropertyInformationRequestDetailsDTO propertyDetailsRequestDTO = new PropertyInformationRequestDetailsDTO();
			propertyDetailsRequestDTO.setCountry("India");
			propertyDetailsRequestDTO.setHotelName("Hotel Beniwal");
			propertyDetailsRequestDTO.setCity("Jodhpur");
			PropertyDetailsThread propertyDetailsThread = new PropertyDetailsThread(
					propertyDetailsRequestDTO, processDate, context);
			propertyDetailsThread.start();
			while( Thread.activeCount() > 1)
			{
				Thread.sleep(500);
			}
			((ClassPathXmlApplicationContext) context).close();
			//TODO check where to close the context this has to be
			//changed as child thread are executing but needs to be
			//close after all child threads are terminated
		//	((ClassPathXmlApplicationContext) context).close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			((ClassPathXmlApplicationContext) context).close();
		}
	}
}
