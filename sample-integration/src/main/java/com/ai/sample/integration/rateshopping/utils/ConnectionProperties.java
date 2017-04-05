package com.ai.sample.integration.rateshopping.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;



public class ConnectionProperties {

	static final Logger logger = Logger.getLogger(ConnectionProperties.class);

	private static ConnectionProperties instance = null;
	private static Properties properties = new Properties();
	
	private ConnectionProperties() {
	}

	private static void getInstance() {
		if (instance == null) {
			instance = new ConnectionProperties();
			try {
				properties.load(ConnectionProperties.class.getClassLoader()
						.getResourceAsStream("connectiondetails.properties"));
			} catch (FileNotFoundException e) {
				logger.error("FileNotFoundException in ConnectionProperties"
						+ e.getMessage());
			} catch (IOException e) {
				logger.error("IOException in ConnectionProperties"
						+ e.getMessage());
			} 
		}
	}
	
	public static Properties getProperties() {
		getInstance();
		return properties;
	}
}
