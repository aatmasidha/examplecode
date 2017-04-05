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

import com.ai.sample.db.service.configuration.SeverityConfigurationService;
import com.ai.sample.db.test.BaseTestConfiguration;

public class SeverityConfigurationTest extends BaseTestConfiguration {

	Logger logger = Logger.getLogger(SeverityConfigurationTest.class);

	@Autowired
	SeverityConfigurationService severityConfigurationService;

	@Rollback(false)
	@Test
	public void testSeverityConfiguration() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		File csvFile = new File(classLoader.getResource("severity.csv")
				.getFile());

		Reader in = new FileReader(csvFile);
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);

		ArrayList<String> severityList = new ArrayList<String>();
		int numRecords = 0;
		for (CSVRecord record : records) {
			if (numRecords == 0) {
				numRecords++;
				continue;
			}

			String eventTypeName = record.get(0);
			severityList.add(eventTypeName);
		}

		for (String severityName : severityList) {
			severityConfigurationService
					.saveSeverityConfiguration(severityName);
		}
	}

}
