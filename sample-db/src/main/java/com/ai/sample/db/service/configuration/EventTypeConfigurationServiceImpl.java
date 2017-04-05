package com.ai.sample.db.service.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.configuration.events.EventTypeConfigurationDao;
import com.ai.sample.db.model.configuration.events.EventTypeConfiguration;

@Service("eventTypeService")
@Transactional
public class EventTypeConfigurationServiceImpl implements EventTypeConfigurationService{


	@Autowired
	EventTypeConfigurationDao eventTypeConfigurationDao;
	
	@Override
	public void saveEventType(String eventTypeName) throws ISellDBException {
		EventTypeConfiguration eventTypeConfiguration = eventTypeConfigurationDao.findEventTypeByName(eventTypeName);
		if(eventTypeConfiguration == null)
		{
			eventTypeConfiguration =  new EventTypeConfiguration(eventTypeName);
		}
		eventTypeConfigurationDao.saveEventType(eventTypeConfiguration);
	}

	@Override
	public List<String> findAllEventTypes() throws ISellDBException {
		List<EventTypeConfiguration> eventTypeList = eventTypeConfigurationDao.findAllEventTypeConfiguration();
		List<String> eventTypeNameList = new ArrayList<String>();
		for(EventTypeConfiguration eventType : eventTypeList)
		{
			eventTypeNameList.add(eventType.getName());
		}
		return eventTypeNameList;
	}

	@Override
	public String findEventTypeByName(String eventTypeName)
			throws ISellDBException {
		
		EventTypeConfiguration eventTypeConfiguration = eventTypeConfigurationDao.findEventTypeByName(eventTypeName);
		
		if(eventTypeConfiguration == null)
		{
			throw new ISellDBException(-1, "Event Type Name not found: " + eventTypeName);
		}
		return eventTypeConfiguration.getName();
	}

	@Override
	public void deleteEventTypeByName(String eventTypeName)
			throws ISellDBException {
		eventTypeConfigurationDao.deleteEventTypeConfigurationByName(eventTypeName);
	}

	

}
