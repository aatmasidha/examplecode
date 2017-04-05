package com.ai.sample.db.dao.configuration.events;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.configuration.events.EventCategoryConfiguration;

public interface EventCategoryConfigurationDao {

	void saveEventCategory(EventCategoryConfiguration eventCategory) throws ISellDBException ;
	
	List<EventCategoryConfiguration> findAllEventCategoryConfiguration() throws ISellDBException;
	
	EventCategoryConfiguration findEventCategoryByName(String eventCategoryName) throws ISellDBException;
	
	void deleteEventCategoryConfigurationByName(String eventCategoryName) throws ISellDBException;

	EventCategoryConfiguration getOrCreateEventCategoryByName(String eventCategoryName) throws ISellDBException;
}
