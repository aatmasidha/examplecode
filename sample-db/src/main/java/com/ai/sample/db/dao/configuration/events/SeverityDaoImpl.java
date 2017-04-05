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
import com.ai.sample.db.model.configuration.events.Severity;

@Repository("severityDao")
public class SeverityDaoImpl extends AbstractDao implements SeverityDao{

	static final Logger LOGGER = Logger.getLogger(SeverityDaoImpl.class);
	
	@Override
	public void saveSeverity(Severity severity) throws ISellDBException {
		try
		{
			saveOrUpdate(severity);
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in SeverityDaoImpl::saveSeverity : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<Severity> findAllSeverity() throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(Severity.class);
			return (List<Severity>) criteria.list();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in SeverityDaoImpl::findAllSeverity : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public Severity findSeverityByName(String severityName)
			throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(Severity.class);
			criteria.add(Restrictions.eq("name",severityName));
			Severity severity = (Severity) criteria.uniqueResult();
			return  severity;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in SeverityDaoImpl::findSeverityByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteSeverityByName(String severityName)
			throws ISellDBException {
		try
		{
			Query query = getSession().createSQLQuery("delete from Severity where name = :name");
			query.setString("name", severityName);
			query.executeUpdate();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in SeverityDaoImpl::deleteSeverityByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public Severity getOrCreateSeverityByName(String severityName)
			throws ISellDBException {
		try
		{
			Severity severity = findSeverityByName(severityName);
			if(severity == null)
			{
				severity = new Severity(severityName);
				saveSeverity(severity);
			}
			return severity;
		}catch ( HibernateException e) {
			LOGGER.error("Exception in SeverityDaoImpl::getOrCreateSeverityByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
