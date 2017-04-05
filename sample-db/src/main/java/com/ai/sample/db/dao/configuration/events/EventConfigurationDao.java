package com.ai.sample.db.dao.configuration.events;

import java.util.Date;
import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.configuration.events.EventConfiguration;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface EventConfigurationDao {

	void saveEventConfiguration(EventConfiguration eventConfiguration) throws ISellDBException ;
	
	List<EventConfiguration> findAllEventConfiguration() throws ISellDBException;
	
	EventConfiguration findEventConfigurationByName(String eventConfigurationName, Date eventDate, String category, String type, String severity) throws ISellDBException;
	
	void deleteEventConfigurationByName(String eventConfigurationName, String category, String type, String severity) throws ISellDBException;

	EventConfiguration getOrCreateEventConfiguration(String eventConfigurationName, Date eventDate, String category, String type,  String severity) throws ISellDBException;
}
