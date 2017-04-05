package com.ai.sample.db.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBConfigurationProperties {

	static final Logger logger = Logger.getLogger(DBConfigurationProperties.class);

	private static DBConfigurationProperties instance = null;
	private static Properties properties = new Properties();
	
	private DBConfigurationProperties() {
	}

	private static void getInstance() {
		if (instance == null) {
			instance = new DBConfigurationProperties();
			try {
				properties.load(DBConfigurationProperties.class.getClassLoader()
						.getResourceAsStream("configuration.properties"));
			} catch (FileNotFoundException e) {
				logger.error("FileNotFoundException in DBConfigurationProperties"
						+ e.getMessage());
			} catch (IOException e) {
				logger.error("IOException in DBConfigurationProperties"
						+ e.getMessage());
			} 
		}
	}
	
	public static Properties getProperties() {
		getInstance();
		return properties;
	}
}
