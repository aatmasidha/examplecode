package com.ai.sample.db.dao.ota;

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
import com.ai.sample.db.model.ota.OnlineTravelAgentConnectionDetails;
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;

@Repository("OnlineTravelAgentConnectionDetailsDao")
public class OnlineTravelAgentConnectionDetailsDaoImpl extends AbstractDao
implements OnlineTravelAgentConnectionDetailsDao {

	static final Logger LOGGER = Logger
			.getLogger(OnlineTravelAgentConnectionDetailsDaoImpl.class);

	@Autowired
	OnlineTravelAgentDetailsDao otaDetailsDao;

	public void saveOnlineTravelAgent(
			OnlineTravelAgentConnectionDetails onlineAgentConnectionDetails)
					throws ISellDBException {
		try {
			LOGGER.debug("Inside OnlineTravelAgentConnectionDetailsDaoImpl::saveOnlineTravelAgent");
			saveOrUpdate(onlineAgentConnectionDetails);
			LOGGER.debug("Exit OnlineTravelAgentConnectionDetailsDaoImpl::saveOnlineTravelAgent");
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in OnlineTravelAgentConnectionDetailsDaoImpl::saveOnlineTravelAgent : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<OnlineTravelAgentConnectionDetails> findAllOnlineTravelAngent()
			throws ISellDBException {
		try {
			LOGGER.debug("Inside OnlineTravelAgentConnectionDetailsDaoImpl::findAllOnlineTravelAngent");
			final Criteria criteria = getSession().createCriteria(
					OnlineTravelAgentConnectionDetails.class);
			LOGGER.debug("Exit OnlineTravelAgentConnectionDetailsDaoImpl::findAllOnlineTravelAngent");
			return (List<OnlineTravelAgentConnectionDetails>) criteria.list();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in OnlineTravelAgentConnectionDetailsDaoImpl::findAllOnlineTravelAngent : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	public OnlineTravelAgentConnectionDetails findOnlineTravelAngentByName(
			String onlineAgentConnectionName, String otaName)
					throws ISellDBException {
		try {
			LOGGER.debug("Inside OnlineTravelAgentConnectionDetailsDaoImpl::findOnlineTravelAngentByName");
			OnlineTravelAgentDetails otaDetails = otaDetailsDao
					.findOnlineTravelAngentByName(otaName);
			final Criteria criteria = getSession().createCriteria(
					OnlineTravelAgentConnectionDetails.class);
			criteria.add(Restrictions.eq("name", onlineAgentConnectionName)
					.ignoreCase());
			criteria.add(Restrictions.eq("otaDetails", otaDetails));
			OnlineTravelAgentConnectionDetails onlineAgent = (OnlineTravelAgentConnectionDetails) criteria
					.uniqueResult();
			LOGGER.debug("Exit OnlineTravelAgentConnectionDetailsDaoImpl::findOnlineTravelAngentByName");
			return onlineAgent;
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in OnlineTravelAgentConnectionDetailsDaoImpl::findOnlineTravelAngentByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	public void deleteOnlineTravelAgentByName(String onlineAgentConnectionName,
			String otaName) throws ISellDBException {
		try {
			LOGGER.debug("Inside OnlineTravelAgentConnectionDetailsDaoImpl::deleteOnlineTravelAgentByName");
			OnlineTravelAgentDetails otaDetails = otaDetailsDao
					.findOnlineTravelAngentByName(otaName);
			Query query = getSession()
					.createSQLQuery(
							"delete from OnlineTravelAgentConnectionDetails where name = :name");
			query.setString("name", onlineAgentConnectionName);
			query.setParameter("otaDetails", otaDetails);
			query.executeUpdate();
			LOGGER.debug("Inside OnlineTravelAgentConnectionDetailsDaoImpl::deleteOnlineTravelAgentByName");
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in OnlineTravelAgentConnectionDetailsDaoImpl::deleteOnlineTravelAgentByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
