package com.ai.sample.db.test.configuration;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ai.sample.common.dto.configuration.EventConfigurationDTO;
import com.ai.sample.db.service.configuration.EventConfigurationService;
import com.ai.sample.db.test.BaseTestConfiguration;

public class EventsConfigurationTest extends BaseTestConfiguration {

	Logger logger = Logger.getLogger(EventsConfigurationTest.class);

	@Autowired
	EventConfigurationService eventConfigurationService;

	@Rollback(false)
	@Test
	public void testEventTypeConfiguration() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		File csvFile = new File(classLoader.getResource("events.csv").getFile());

		Reader in = new FileReader(csvFile);
		CSVFormat fmt = CSVFormat.EXCEL.withDelimiter(',');
//		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
		
		ArrayList<EventConfigurationDTO> eventConfigurationList = new ArrayList<EventConfigurationDTO>();
		int numRecords = 0;
		for (CSVRecord record : records) {
			if (numRecords == 0) {
				numRecords++;
				continue;
			}
			String values = record.toString();
	    	String[] rowTokenList = values.split(",");
	    	
			System.out.println(rowTokenList.length);
//			String severity = record.get(2);
			String severity = rowTokenList[2];
			if (severity == null || severity.length() <= 0) {
				severity = "low";
			}

//			String eventType = record.get(3);
			String eventType = rowTokenList[3];
			if (eventType == null || eventType.length() <= 0) {
				eventType = "default";
			}

//			String eventCategory = record.get(4);
			String eventCategory = rowTokenList[4];
			if (eventCategory == null || eventCategory.length() <= 0) {
				eventCategory = "hotelwide";
			}
			
//			String dateString = record.get(0);
			String dateString = rowTokenList[0];
			dateString = dateString.replace('[', ' ');
//			headerToken = headerToken.replace(']', ' ');
			dateString = dateString.trim();
			Date eventDate = null;
			if (dateString != null && dateString.length() > 0) {
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				eventDate = format.parse(dateString);
				EventConfigurationDTO eventConfigurationDto = new EventConfigurationDTO(
						rowTokenList[1], eventDate, severity, eventType,
						eventCategory, rowTokenList[5]);

				eventConfigurationList.add(eventConfigurationDto);
				
			} else {
				logger.error("Event can not be added as the date is not defined for event" + record.get(1));
			}
		}
		
		for (EventConfigurationDTO event : eventConfigurationList) {
			eventConfigurationService.saveEventConfiguration(event);
		}

		List<EventConfigurationDTO> eventList = eventConfigurationService.findAllEventsConfigured();
		if(eventList != null && eventList.size() > 0)
		{
			for (EventConfigurationDTO event1 : eventConfigurationList) {
				logger.debug("Event is: " + event1.getRegionName());
			}
		}
	}

}