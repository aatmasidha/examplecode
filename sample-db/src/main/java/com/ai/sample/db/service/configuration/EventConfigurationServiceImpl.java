package com.ai.sample.db.service.configuration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.AdditionalInformationDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.configuration.EventConfigurationDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.configuration.CityDao;
import com.ai.sample.db.dao.configuration.events.EventCategoryConfigurationDao;
import com.ai.sample.db.dao.configuration.events.EventConfigurationDao;
import com.ai.sample.db.dao.configuration.events.EventTypeConfigurationDao;
import com.ai.sample.db.dao.configuration.events.SeverityDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.mapping.PropertyEventMappingDao;
import com.ai.sample.db.model.configuration.City;
import com.ai.sample.db.model.configuration.events.EventCategoryConfiguration;
import com.ai.sample.db.model.configuration.events.EventConfiguration;
import com.ai.sample.db.model.configuration.events.EventTypeConfiguration;
import com.ai.sample.db.model.configuration.events.Severity;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyEventMapping;
import com.ai.sample.db.type.MyJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service("eventConfigurationService")
@Transactional
public class EventConfigurationServiceImpl implements EventConfigurationService{

	@Autowired
	EventConfigurationDao eventConfigurationDao;
	
	@Autowired 
	EventCategoryConfigurationDao eventCategoryConfigurationDao;
	
	@Autowired
	EventTypeConfigurationDao eventTypeConfigurationDao;
	
	@Autowired
	SeverityDao severityDao;
	
	
	@Autowired 
	PropertyDetailsDao propertyDetailsDao;
	
	@Autowired
	PropertyEventMappingDao propertyEventMappingDao;
	
	@Override
	public void saveEventConfiguration(EventConfigurationDTO eventConfigurationDto)
			throws ISellDBException {
		try
		{
			String eventName = eventConfigurationDto.getEventName();
			eventName = eventName.trim();
			
			String eventCategoryName = eventConfigurationDto.getEventCategory();
			eventCategoryName = eventCategoryName.trim();
			
			String eventTypeName = eventConfigurationDto.getEventType();
			eventTypeName = eventTypeName.trim();
			
			String severityName = eventConfigurationDto.getSeverity();
			severityName = severityName.trim();
			
			EventConfiguration eventConfiguration = eventConfigurationDao.findEventConfigurationByName(eventName,eventConfigurationDto.getEventDate(), eventCategoryName, 
					eventTypeName, severityName);
			
			List<AdditionalInformationDTO> eventRegionInfoList = new ArrayList<AdditionalInformationDTO>();
			Gson gson = new Gson();
			
			Type listType = new TypeToken<List<AdditionalInformationDTO>>() {
			}.getType();
			AdditionalInformationDTO regionInformation = new AdditionalInformationDTO(eventCategoryName, eventConfigurationDto.getRegionName());
			eventRegionInfoList.add(regionInformation);
			String json = gson.toJson(eventRegionInfoList, 	listType);
			MyJson myJsonObject = new MyJson();
			myJsonObject.setStringProp(json);
			if(eventConfiguration == null)
			{
				eventCategoryName = eventCategoryName.trim();
				EventCategoryConfiguration eventCategory = eventCategoryConfigurationDao.findEventCategoryByName(eventCategoryName);
				if(eventCategory == null)
				{
					eventCategory = eventCategoryConfigurationDao.getOrCreateEventCategoryByName(eventCategoryName);
				}
				
				eventTypeName = eventTypeName.trim();
				EventTypeConfiguration eventType = eventTypeConfigurationDao.findEventTypeByName(eventTypeName);
				if(eventType == null)
				{
					eventType = eventTypeConfigurationDao.getOrCreateEventTypeByName(eventTypeName);
				}
				
				severityName = severityName.trim();
				Severity severity = severityDao.findSeverityByName(severityName);
				if(severity == null)
				{
					severity = severityDao.getOrCreateSeverityByName(severityName);
				}
				eventConfiguration = new EventConfiguration(eventName, eventConfigurationDto.getEventDate(), eventCategory, eventType, severity, eventConfigurationDto.isRecurring());
				eventConfiguration.setRegion(myJsonObject);
			}
			else
			{
				eventConfiguration.setRegion(myJsonObject);
			}
			eventConfigurationDao.saveEventConfiguration(eventConfiguration);
			
			if(eventCategoryName.equalsIgnoreCase("citywide"))
			{
				String regionName = eventConfigurationDto.getRegionName();
				if(regionName != null && regionName.length() > 0 )
				{
					 StringTokenizer strings = new StringTokenizer(regionName, "-");
					 CityDTO cityDto = null;
					 while (strings.hasMoreElements()) {
				            String cityName = strings.nextToken();
				            String stateName = strings.nextToken();
				            String countryName = strings.nextToken();
				            cityDto = new CityDTO(cityName, stateName, countryName);
				        }
					 if(cityDto != null)
					 {
						 List<PropertyDetails> propertyDetailsList = propertyDetailsDao.findPropertyDetailsByCity(cityDto);
						 for(PropertyDetails propertyDetails :  propertyDetailsList)
						 {
							 PropertyEventMapping propertyEventMapping = new PropertyEventMapping(propertyDetails, eventConfiguration);
							 
							 propertyEventMappingDao.saveOrUpdatePropertyCompetitorMappingDetails(propertyEventMapping);
						 }
					 }
				}
			}
			else if(eventCategoryName.equalsIgnoreCase("statewide"))
			{
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<EventConfigurationDTO> findAllEventsConfigured()
			throws ISellDBException {
		List<EventConfiguration> eventConfigurationList = eventConfigurationDao.findAllEventConfiguration();
		List<EventConfigurationDTO> eventConfigurationDtoList = new ArrayList<EventConfigurationDTO>();
		for(EventConfiguration eventConfiguration : eventConfigurationList)
		{
//			TODO check if we are getting correct value for event configuration region
			EventConfigurationDTO eventConfDto = new EventConfigurationDTO(eventConfiguration.getName(), eventConfiguration.getEventDate(), 
					eventConfiguration.getSeverity().getName(), eventConfiguration.getEventType().getName(), 
					eventConfiguration.getEventCategory().getName(), eventConfiguration.getRegion().toString());
			eventConfigurationDtoList.add(eventConfDto);
		}
		return eventConfigurationDtoList;
	}

	@Override
	public EventConfigurationDTO findEventByName(String eventName,
			String eventCategory, String eventType) throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEventByName(String eventName, String eventCategory,
			String eventType) throws ISellDBException {
		// TODO Auto-generated method stub
		
	}
}
