package com.ai.sample.db.test.configuration;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ai.sample.db.service.configuration.EventTypeConfigurationService;
import com.ai.sample.db.test.BaseTestConfiguration;


public class EventTypeConfigurationTest  extends BaseTestConfiguration {

	Logger logger = Logger.getLogger(EventTypeConfigurationTest.class);

	@Autowired
	EventTypeConfigurationService eventTypeConfigurationService;
	
	@Rollback(false)
	@Test
	public void testEventTypeConfiguration() throws Exception{
			ClassLoader classLoader = getClass().getClassLoader();
			File csvFile = new File(classLoader.getResource("eventtype.csv").getFile());

			Reader in = new FileReader(csvFile);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			
			ArrayList<String> eventTypeList = new ArrayList<String>();
			int numRecords = 0;
		    for (CSVRecord record : records ) {
		    	if(numRecords == 0)
		    	{
		    		numRecords++;
		    		continue;
		    	}
		    	
		    	String eventTypeName = record.get(0);
		    	eventTypeList.add(eventTypeName);
		    }
		    
		    for (String eventType : eventTypeList ) {
		    	eventTypeConfigurationService.saveEventType(eventType);
		    }
	}
	
}
