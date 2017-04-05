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
import com.ai.sample.db.model.configuration.events.EventTypeConfiguration;

@Repository("eventTypeConfigurationDao")
public class EventTypeConfigurationDaoImpl extends AbstractDao implements
		EventTypeConfigurationDao {

	static final Logger LOGGER = Logger
			.getLogger(EventTypeConfigurationDaoImpl.class);

	@Override
	public void saveEventType(EventTypeConfiguration eventType)
			throws ISellDBException {
		try {
			saveOrUpdate(eventType);
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in EventTypeConfigurationDaoImpl::saveEventType : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<EventTypeConfiguration> findAllEventTypeConfiguration()
			throws ISellDBException {
		try {
			final Criteria criteria = getSession().createCriteria(
					EventTypeConfiguration.class);
			return (List<EventTypeConfiguration>) criteria.list();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in EventTypeConfigurationDaoImpl::findAllEventTypeConfiguration : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public EventTypeConfiguration findEventTypeByName(String eventTypeName)
			throws ISellDBException {
		try {
			final Criteria criteria = getSession().createCriteria(
					EventTypeConfiguration.class);
			criteria.add(Restrictions.eq("name", eventTypeName));
			EventTypeConfiguration eventType = (EventTypeConfiguration) criteria
					.uniqueResult();
			return eventType;
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in EventTypeConfigurationDaoImpl::findEventTypeByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteEventTypeConfigurationByName(String eventTypeName)
			throws ISellDBException {
		try {
			Query query = getSession().createSQLQuery(
					"delete from EventTypeConfiguration where name = :name");
			query.setString("name", eventTypeName);
			query.executeUpdate();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in EventTypeConfigurationDaoImpl::deleteEventTypeConfigurationByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public EventTypeConfiguration getOrCreateEventTypeByName(
			String eventTypeName) throws ISellDBException {
		EventTypeConfiguration eventType = findEventTypeByName(eventTypeName);
		if (eventType == null) {
			eventType = new EventTypeConfiguration(eventTypeName);
			saveEventType(eventType);
		}
		return eventType;
	}
}
