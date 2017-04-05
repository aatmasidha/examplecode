package com.ai.sample.db.dao.configuration.events;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.configuration.events.EventCategoryConfiguration;
import com.ai.sample.db.model.configuration.events.EventConfiguration;
import com.ai.sample.db.model.configuration.events.EventTypeConfiguration;
import com.ai.sample.db.model.configuration.events.Severity;

@Repository("eventConfigurationDao")
public class EventConfigurationDaoImpl extends AbstractDao implements
		EventConfigurationDao {

	static final Logger LOGGER = Logger.getLogger(EventConfigurationDaoImpl.class);
	
	@Autowired
	EventCategoryConfigurationDao eventCategoryConfigurationDao;
	
	@Autowired
	EventTypeConfigurationDao eventTypeConfigurationDao;
	
	@Autowired
	SeverityDao severityDao;
	
	@Override
	public void saveEventConfiguration(EventConfiguration eventConfiguration)
			throws ISellDBException {
		try
		{
			saveOrUpdate(eventConfiguration);
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("EventConfigurationDaoImpl::saveEventConfiguration" + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<EventConfiguration> findAllEventConfiguration()
			throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(
					EventConfiguration.class);
			return (List<EventConfiguration>) criteria.list();
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("EventConfigurationDaoImpl::findAllEventConfiguration" + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public EventConfiguration findEventConfigurationByName(
			String eventConfigurationName, Date eventDate, String category, String type,  String severityName)
			throws ISellDBException {
		try
		{
		
		EventCategoryConfiguration eventCategoryConfiguration = eventCategoryConfigurationDao.findEventCategoryByName(category);
		
		EventTypeConfiguration eventTypeConfiguration = eventTypeConfigurationDao.findEventTypeByName(type);
		
		Severity severity = severityDao.findSeverityByName(severityName);
		final Criteria criteria = getSession().createCriteria(
				EventConfiguration.class);
		criteria.add(Restrictions.eq("name", eventConfigurationName));
		criteria.add(Restrictions.eq("eventCategory", eventCategoryConfiguration));
		criteria.add(Restrictions.eq("eventType", eventTypeConfiguration));
		criteria.add(Restrictions.eq("severity", severity));
		criteria.add(Restrictions.eq("eventDate", eventDate));
		EventConfiguration eventType = (EventConfiguration) criteria
				.uniqueResult();
		return eventType;
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("EventConfigurationDaoImpl::findEventConfigurationByName" + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteEventConfigurationByName(String eventConfigurationName, String category, String type,  String severityName)
			throws ISellDBException {
	try
	{
		EventCategoryConfiguration eventCategoryConfiguration = eventCategoryConfigurationDao.findEventCategoryByName(category);
		
		EventTypeConfiguration eventTypeConfiguration = eventTypeConfigurationDao.findEventTypeByName(type);

		Severity severity = severityDao.findSeverityByName(severityName);
		
		Query query = getSession().createSQLQuery(
				"delete from EventTypeConfiguration where name = :name");
		query.setParameter("name", eventConfigurationName);
		query.setParameter("eventCategory", eventCategoryConfiguration);
		query.setParameter("eventType", eventTypeConfiguration);
		query.setParameter("severity", severity);
		query.executeUpdate();
	}
	catch(PSQLException | HibernateException e)
	{
		LOGGER.error("EventConfigurationDaoImpl::deleteEventConfigurationByName" + e.getMessage());
		throw new ISellDBException(-1, e.getMessage());
	}
	}

	@Override
	public EventConfiguration getOrCreateEventConfiguration(
			String eventName, Date eventDate, String category, String type, String severityName)
			throws ISellDBException {
		EventConfiguration eventType = findEventConfigurationByName(eventName, eventDate, category, type, severityName);
		if (eventType == null) {
			EventCategoryConfiguration eventCategoryConfiguration = eventCategoryConfigurationDao.findEventCategoryByName(category);
			
			EventTypeConfiguration eventTypeConfiguration = eventTypeConfigurationDao.findEventTypeByName(type);

			Severity severity = severityDao.findSeverityByName(severityName);
			
			eventType = new EventConfiguration();
			eventType.setEventCategory(eventCategoryConfiguration);
			eventType.setEventType( eventTypeConfiguration);
			eventType.setSeverity(severity);
			saveEventConfiguration(eventType);
		}
		return eventType;
	}
}
