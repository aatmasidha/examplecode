package com.ai.sample.db.test.configuration;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ai.sample.common.dto.configuration.OnlineTravelAgentDetailsDTO;
import com.ai.sample.db.service.configuration.OnlineTravelAgentService;
import com.ai.sample.db.test.BaseTestConfiguration;


public class PopulateOnlineTravelAgentListTest  extends BaseTestConfiguration {

	Logger logger = Logger.getLogger(PopulateOnlineTravelAgentListTest.class);
	
	@Autowired
	OnlineTravelAgentService onlineTravelAgentService;

	@Rollback(false)
	@Test
	public void testPopulateOtaList() throws Exception{
			ClassLoader classLoader = getClass().getClassLoader();
			File csvFile = new File(classLoader.getResource("otalist.csv").getFile());

			Reader in = new FileReader(csvFile);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			
				int numRecords = 0;
		    for (CSVRecord record : records ) {
		    	if(numRecords == 0)
		    	{
		    		numRecords++;
		    		continue;
		    	}
		    	
		    	OnlineTravelAgentDetailsDTO onlineTravelAgentDetailsDTO = new OnlineTravelAgentDetailsDTO(record.get(0), record.get(1));
		    	
		    	onlineTravelAgentService.saveOnlineTravelAgent(onlineTravelAgentDetailsDTO);
		    }
	}
	
}
