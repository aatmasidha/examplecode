package com.ai.sample.db.service.configuration;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;

public interface EventCategoryConfigurationService {

	public void saveEventCategory(String eventCategory) throws ISellDBException;
	
	public List<String> findAllEventCategory() throws ISellDBException;
	
	public String findEventCategoryByName(String eventCategoryName) throws ISellDBException;
	
	public void deleteEventCategoryByName(String eventCategoryName) throws ISellDBException;
}
