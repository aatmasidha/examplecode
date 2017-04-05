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

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.db.service.configuration.CityService;
import com.ai.sample.db.test.BaseTestConfiguration;


public class PopulateCityStateCountryTest  extends BaseTestConfiguration {

	Logger logger = Logger.getLogger(PopulateCityStateCountryTest.class);
	
	@Autowired
	CityService cityService;

	@Rollback(false)
	@Test
	public void testPoulateCityData() throws Exception{
			ClassLoader classLoader = getClass().getClassLoader();
			File csvFile = new File(classLoader.getResource("citylist.csv").getFile());

			Reader in = new FileReader(csvFile);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			
			ArrayList<CityDTO> cityDTOList = new ArrayList<CityDTO>();
			int numRecords = 0;
		    for (CSVRecord record : records ) {
		    	if(numRecords == 0)
		    	{
		    		numRecords++;
		    		continue;
		    	}
		    	
		    	CityDTO cityDTO = new CityDTO(record.get(0), record.get(1), record.get(2));
		    	cityDTOList.add(cityDTO);
		    	cityService.saveCity(cityDTO);
		    	
		    	
		    }
	}
	
}
