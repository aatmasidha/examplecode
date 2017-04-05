package com.ai.sample.integration.pms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;

import com.ai.sample.common.dto.ChannelManagerAvailabilityDTO;
import com.ai.sample.integration.pms.request.AvailRequestSegment;
import com.ai.sample.integration.pms.request.AvailabilityByDayRequest;
import com.ai.sample.integration.pms.request.RoomRequest;
import com.ai.sample.integration.pms.response.availability.AvailabilityByDayResponse;
import com.ai.sample.integration.pms.response.availability.DateAvailabilityListResponse;
import com.ai.sample.integration.pms.response.availability.DateAvailabilityResponse;
import com.ai.sample.integration.pms.response.availability.RoomTypeDateAvailabilityListResponse;
import com.ai.sample.integration.pms.response.availability.RoomTypeDateAvailabilityResponse;

public class StaahClientTest {

	public static void main(String args[])
	{
		roomTypeRequest();
		
		requestAvailability();
	}

	private static void requestAvailability() {
		try
		{
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost("URL");
			
			post.setHeader("Content-Type", "text/xml");
			
			Date today = new Date();
			Date tomorrow = DateUtils.addDays(today, 3);
			List<AvailRequestSegment>  availRequestSegmentList =  new ArrayList<>();
			AvailRequestSegment availRequestSegment =  new AvailRequestSegment(today, tomorrow);
			availRequestSegmentList.add(availRequestSegment);
			AvailabilityByDayRequest availRequestSegments =  new AvailabilityByDayRequest("user", "pwd", "5725", "1.0");
			availRequestSegments.setAvailableRequestSegment(availRequestSegmentList);

			
			JAXBContext contextObj = JAXBContext.newInstance(AvailabilityByDayRequest.class);  
			  
		    Marshaller marshallerObj = contextObj.createMarshaller();  
		    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
		    
		    StringWriter sw = new StringWriter();
		    marshallerObj.marshal(availRequestSegments, sw);
		    String xmlString = sw.toString();
			System.out.println(xmlString);
			
			StringEntity entity = new StringEntity(xmlString, StandardCharsets.UTF_8);
	
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
//			StringBuffer xmlStr = new StringBuffer("<?xml version=\"1.0\"?> ");
			StringBuffer xmlStr = new StringBuffer("");
			while ((line = rd.readLine()) != null) {
				  xmlStr = xmlStr.append(line );
			}
			System.out.println( xmlStr );
			
			
			JAXBContext jaxbContext = JAXBContext.newInstance(AvailabilityByDayResponse.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			 
			AvailabilityByDayResponse availabilityByDayResponse = (AvailabilityByDayResponse) unmarshaller.unmarshal(new StreamSource( new StringReader( xmlStr.toString() ) ));
			System.out.println(availabilityByDayResponse);
			
			List<RoomTypeDateAvailabilityListResponse> rooTypeDateAvailabilityList = availabilityByDayResponse.getRoomTypeDateAvailabilityList();
			List<ChannelManagerAvailabilityDTO> channelManagerAvailabilityDTOList =  new ArrayList<ChannelManagerAvailabilityDTO>();
			for(int cnt = 0; cnt < rooTypeDateAvailabilityList.size(); cnt++)
			{
				RoomTypeDateAvailabilityListResponse rateTypeDateAvailability = rooTypeDateAvailabilityList.get(cnt);
				List<RoomTypeDateAvailabilityResponse> roomTypeDateAvailabilityResponseList = rateTypeDateAvailability.getDateAvailabilityList();
				for(int cntInner = 0; cntInner < roomTypeDateAvailabilityResponseList.size(); cntInner ++)
				{
					RoomTypeDateAvailabilityResponse dateData = roomTypeDateAvailabilityResponseList.get(cntInner);
					DateAvailabilityListResponse dateAvailabilityListResponse = dateData.getDateAvailabilityListResponse();
					for( int cntInner1 = 0; cntInner1 < dateAvailabilityListResponse.getDateAvailabilityList().size(); cntInner1++)
					{
						DateAvailabilityResponse roomCountData = dateAvailabilityListResponse.getDateAvailabilityList().get(cntInner1);
						System.out.println("Date for Availability count is: " + roomCountData.getOccupancyDate() );
						System.out.println("Room Availability count is: " + roomCountData.getSoldCount() );
					}
					System.out.println("Room Code is: " + dateData.getRoomTypeCode() );
				}
			}

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void roomTypeRequest() {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost("URL");
		
		post.setHeader("Content-Type", "text/xml");
		
		try {
//			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			System.out.println(post.getRequestLine().toString());
			Header[] headers = post.getAllHeaders();
			for( int i = 0; i < headers.length; i++)
			{
				System.out.println(headers[i].toString());
			}
			
			RoomRequest roomRequest =  new RoomRequest("user", "password", "5725");
			
			JAXBContext contextObj = JAXBContext.newInstance(RoomRequest.class);  
			  
		    Marshaller marshallerObj = contextObj.createMarshaller();  
		    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
		    
		    StringWriter sw = new StringWriter();
		    marshallerObj.marshal(roomRequest, sw);
		    String xmlString = sw.toString();
			System.out.println(xmlString);
			
			StringEntity entity = new StringEntity(xmlString, HTTP.UTF_8);

			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}


