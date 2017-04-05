package com.ai.sample.db.dao.configuration.events;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.configuration.events.EventCategoryConfiguration;

@Repository("eventCategoryConfigurationDao")
public class EventCategoryConfigurationDaoImpl extends AbstractDao implements
		EventCategoryConfigurationDao {

	static final Logger LOGGER = Logger.getLogger(EventCategoryConfigurationDaoImpl.class);
	
	@Override
	public void saveEventCategory(EventCategoryConfiguration eventCategory)
			throws ISellDBException {
		try
		{
			saveOrUpdate(eventCategory);
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in EventCategoryConfigurationDaoImpl::saveEventCategory : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<EventCategoryConfiguration> findAllEventCategoryConfiguration()
			throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(
					EventCategoryConfiguration.class);
			return (List<EventCategoryConfiguration>) criteria.list();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in EventCategoryConfigurationDaoImpl::findAllEventCategoryConfiguration : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}	
	}

	@Override
	public EventCategoryConfiguration findEventCategoryByName(
			String eventCategoryName) throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(
					EventCategoryConfiguration.class);
			criteria.add(Restrictions.eq("name", eventCategoryName));
			EventCategoryConfiguration eventCategory = (EventCategoryConfiguration) criteria
					.uniqueResult();
			return eventCategory;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in EventCategoryConfigurationDaoImpl::findEventCategoryByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}	
	}

	@Override
	public void deleteEventCategoryConfigurationByName(String eventCategoryName)
			throws ISellDBException {
		try
		{
			Query query = getSession().createSQLQuery(
					"delete from EventCategoryConfiguration where name = :name");
			query.setString("name", eventCategoryName);
			query.executeUpdate();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in EventCategoryConfigurationDaoImpl::deleteEventCategoryConfigurationByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}	
	}

	@Override
	public EventCategoryConfiguration getOrCreateEventCategoryByName(
			String eventCategoryName) throws ISellDBException {
		try
		{
			EventCategoryConfiguration eventCategory = findEventCategoryByName(eventCategoryName);
			if (eventCategory == null) {
				eventCategory = new EventCategoryConfiguration(eventCategoryName);
				saveEventCategory(eventCategory);
			}
			return eventCategory;
		}catch ( HibernateException e) {
			LOGGER.error("Exception in EventCategoryConfigurationDaoImpl::getOrCreateEventCategoryByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}	
		
	}
}
