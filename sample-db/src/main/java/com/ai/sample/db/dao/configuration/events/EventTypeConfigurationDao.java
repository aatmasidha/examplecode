package com.ai.sample.db.dao.configuration.events;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.configuration.events.EventTypeConfiguration;

public interface EventTypeConfigurationDao {

	void saveEventType(EventTypeConfiguration eventType) throws ISellDBException ;
	
	List<EventTypeConfiguration> findAllEventTypeConfiguration() throws ISellDBException;
	
	EventTypeConfiguration findEventTypeByName(String eventTypeName) throws ISellDBException;
	
	void deleteEventTypeConfigurationByName(String eventTypeName) throws ISellDBException;

	EventTypeConfiguration getOrCreateEventTypeByName(String eventTypeName) throws ISellDBException;
}
