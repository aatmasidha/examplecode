package com.ai.sample.db.service.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.configuration.events.EventCategoryConfigurationDao;
import com.ai.sample.db.model.configuration.events.EventCategoryConfiguration;

@Service("eventCategoryConfigurationService")
@Transactional
public  class EventCategoryConfigurationServiceImpl implements EventCategoryConfigurationService{

	@Autowired
	EventCategoryConfigurationDao eventCategoryConfigurationDao;
	
	@Override
	public void saveEventCategory(String eventCategoryName) throws ISellDBException {
		EventCategoryConfiguration eventCategoryConfiguration = eventCategoryConfigurationDao.findEventCategoryByName(eventCategoryName);
		if(eventCategoryConfiguration == null)
		{
			eventCategoryConfiguration =  new EventCategoryConfiguration(eventCategoryName);
		}
		eventCategoryConfigurationDao.saveEventCategory(eventCategoryConfiguration);
	}

	@Override
	public List<String> findAllEventCategory() throws ISellDBException {
		List<EventCategoryConfiguration> eventCategoryList = eventCategoryConfigurationDao.findAllEventCategoryConfiguration();
		List<String> eventCategoryNameList = new ArrayList<String>();
		for(EventCategoryConfiguration eventCategory : eventCategoryList)
		{
			eventCategoryNameList.add(eventCategory.getName());
		}
		return eventCategoryNameList;	
	}

	@Override
	public String findEventCategoryByName(String eventCategoryName)
			throws ISellDBException {
		EventCategoryConfiguration eventCategory = eventCategoryConfigurationDao.findEventCategoryByName(eventCategoryName);
		
		if(eventCategory == null)
		{
			throw new ISellDBException(-1, "Event Category Name not found: " + eventCategoryName);
		}
		return eventCategory.getName();
	}

	@Override
	public void deleteEventCategoryByName(String eventCategoryName)
			throws ISellDBException {
		eventCategoryConfigurationDao.deleteEventCategoryConfigurationByName(eventCategoryName);		
	}

}
