package com.ai.sample.db.service.configuration;

import java.util.List;

import com.ai.sample.common.dto.configuration.EventConfigurationDTO;
import com.ai.sample.common.exception.ISellDBException;

public interface EventConfigurationService {

	public void saveEventConfiguration(EventConfigurationDTO eventConfigurationDto) throws ISellDBException;
	
	public List<EventConfigurationDTO> findAllEventsConfigured() throws ISellDBException;
	
	public EventConfigurationDTO findEventByName(String eventName, String eventCategory, String eventType) throws ISellDBException;
	
	public void deleteEventByName(String eventName, String eventCategory, String eventType) throws ISellDBException;
}
