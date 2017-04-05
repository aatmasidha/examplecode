package com.ai.sample.db.dao.configuration;

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
import com.ai.sample.db.model.configuration.PropertyStatusMaster;

@Repository("propertyStatusMasterDao")
public class PropertyStatusMasterDaoImpl extends AbstractDao implements PropertyStatusMasterDao{

	Logger logger = Logger.getLogger(BookingStatusMasterDaoImpl.class);
	

	@Override
	public void savePropertyStatus(PropertyStatusMaster propertyStatus) throws ISellDBException{
		try
		{
			saveOrUpdate(propertyStatus);
		}
	catch (PSQLException | HibernateException e) {
		logger.error("Exception in PropertyStatusMasterDaoImpl::deleteMasterRoomTypeByName : "
				+ e.getMessage());
		throw new ISellDBException(-1, e.getMessage());
	}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PropertyStatusMaster> findAllPropertyStatus() throws ISellDBException{
	try
	{
		final Criteria criteria = getSession().createCriteria(PropertyStatusMaster.class);
		return (List<PropertyStatusMaster>) criteria.list();
	}
	catch (PSQLException | HibernateException e) {
		logger.error("Exception in PropertyStatusMasterDaoImpl::findAllPropertyStatus : "
				+ e.getMessage());
		throw new ISellDBException(-1, e.getMessage());
	}
	}

	@Override
	public PropertyStatusMaster findPropertyStatusByName(String status) throws ISellDBException{
		try
		{
		final Criteria criteria = getSession().createCriteria(PropertyStatusMaster.class);
		criteria.add(Restrictions.eq("name",status));
		PropertyStatusMaster propertyStatusModel = (PropertyStatusMaster) criteria.uniqueResult();
		return  propertyStatusModel;
		}
		catch (PSQLException | HibernateException e) {
			logger.error("Exception in PropertyStatusMasterDaoImpl::findPropertyStatusByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deletePropertyStatusByName(String status) throws ISellDBException{
		try
		{
		Query query = getSession().createSQLQuery("delete from PropertyStatusMaster where name = :name");
		query.setString("name", status);
		query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			logger.error("Exception in PropertyStatusMasterDaoImpl::deletePropertyStatusByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void flush() throws ISellDBException{
		try
		{
			getSession().flush();
		}
		catch (PSQLException | HibernateException e) {
			logger.error("Exception in PropertyStatusMasterDaoImpl::flush : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void clear() throws ISellDBException{
		try
		{
			getSession().flush();
		}
		catch (PSQLException | HibernateException e) {
			logger.error("Exception in PropertyStatusMasterDaoImpl::clear : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

}
