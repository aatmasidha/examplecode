package com.ai.sample.db.dao.ota;

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
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;

@Repository("onlineTravelAgentDetailsDao")
public class OnlineTravelAgentDetailsDaoImpl extends AbstractDao implements OnlineTravelAgentDetailsDao{

	static final Logger LOGGER = Logger.getLogger(OnlineTravelAgentDetailsDaoImpl.class);

	public void saveOnlineTravelAgentDetails(OnlineTravelAgentDetails onlineAgentName)
			throws ISellDBException {
		try
		{
		LOGGER.debug("Inside OnlineTravelAgentDetailsDaoImpl::saveOnlineTravelAgentDetails");
		LOGGER.debug("Object OnlineTravelAgentDetailsDaoImpl::saveOnlineTravelAgentDetails to save is: " + onlineAgentName);
		saveOrUpdate(onlineAgentName);
		LOGGER.debug("Inside OnlineTravelAgentDetailsDaoImpl::saveOnlineTravelAgentDetails");
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in OnlineTravelAgentDetailsDaoImpl::saveOnlineTravelAgentDetails : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<OnlineTravelAgentDetails> findAllOnlineTravelAgent()
			throws ISellDBException {
		try
		{
		LOGGER.debug("Inside OnlineTravelAgentDetailsDaoImpl::findAllOnlineTravelAngent");
		final Criteria criteria = getSession().createCriteria(OnlineTravelAgentDetails.class);
		LOGGER.debug("Exit OnlineTravelAgentDetailsDaoImpl::findAllOnlineTravelAngent");
		return (List<OnlineTravelAgentDetails>) criteria.list();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in OnlineTravelAgentDetailsDaoImpl::findAllOnlineTravelAgent : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	public OnlineTravelAgentDetails findOnlineTravelAngentByName(
			final String onlineAgentName) throws ISellDBException {
		try
		{
		LOGGER.debug("Inside OnlineTravelAgentDetailsDaoImpl::findOnlineTravelAngentByName");
		final Criteria criteria = getSession().createCriteria(OnlineTravelAgentDetails.class);
		criteria.add(Restrictions.eq("name",onlineAgentName).ignoreCase());
		OnlineTravelAgentDetails onlineAgent = (OnlineTravelAgentDetails) criteria.uniqueResult();
		LOGGER.debug("Exit OnlineTravelAgentDetailsDaoImpl::findOnlineTravelAngentByName");
		return  onlineAgent;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in OnlineTravelAgentDetailsDaoImpl::findOnlineTravelAngentByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	public OnlineTravelAgentDetails findOnlineTravelAngentById(
			int id) throws ISellDBException {
		try
		{
		LOGGER.debug("Inside OnlineTravelAgentDetailsDaoImpl::findOnlineTravelAngentById");
		final Criteria criteria = getSession().createCriteria(OnlineTravelAgentDetails.class);
		criteria.add(Restrictions.eq("id",id));
		OnlineTravelAgentDetails onlineAgent = (OnlineTravelAgentDetails) criteria.uniqueResult();
		LOGGER.debug("Exit OnlineTravelAgentDetailsDaoImpl::findOnlineTravelAngentById");
		return  onlineAgent;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in OnlineTravelAgentDetailsDaoImpl::findOnlineTravelAngentById : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
	
	public void deleteOnlineTravelAgentByName(String onlineAgentName)
			throws ISellDBException {
		try
		{
		LOGGER.debug("Inside OnlineTravelAgentDetailsDaoImpl::deleteOnlineTravelAgentByName");
		Query query = getSession().createSQLQuery("delete from OnlineTravelAgentDetails where name = :name");
		query.setString("name", onlineAgentName);
		query.executeUpdate();
		LOGGER.debug("Execute OnlineTravelAgentDetailsDaoImpl::deleteOnlineTravelAgentByName");
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in OnlineTravelAgentDetailsDaoImpl::deleteOnlineTravelAgentByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
