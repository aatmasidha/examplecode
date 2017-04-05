package com.ai.sample.db.service.configuration;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;

public interface EventTypeConfigurationService {

	public void saveEventType(String eventType) throws ISellDBException;
	
	public List<String> findAllEventTypes() throws ISellDBException;
	
	public String findEventTypeByName(String eventTypeName) throws ISellDBException;
	
	public void deleteEventTypeByName(String eventTypeName) throws ISellDBException;
}
