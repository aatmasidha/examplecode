package com.ai.sample.db.dao.property.mapping;

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
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyCompetitorMapping;

@Repository("propertyCompetitorMappingDao")
public class PropertyCompetitorMappingDaoImpl extends AbstractDao implements
		PropertyCompetitorMappingDao {

	static final Logger LOGGER = Logger
			.getLogger(PropertyCompetitorMappingDaoImpl.class);

	@Autowired
	PropertyDetailsDao propertyDetailsDao;

	@Override
	public void saveOrUpdatePropertyCompetitorMappingDetails(
			PropertyCompetitorMapping propertyCompetitorMapping)
			throws ISellDBException {
		try {
			saveOrUpdate(propertyCompetitorMapping);
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyCompetitorMappingDaoImpl::saveOrUpdatePropertyCompetitorMappingDetails : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<PropertyCompetitorMapping> findCompetitorsForProperty(
			PropertyDetails propertyDetails) throws ISellDBException {
		try {
			final Criteria criteria = getSession().createCriteria(
					PropertyCompetitorMapping.class);
			criteria.add(Restrictions.eq("propertyDetails", propertyDetails));
			List<PropertyCompetitorMapping> propertyCompetitorMappingList = (List<PropertyCompetitorMapping>) criteria
					.list();
			return propertyCompetitorMappingList;
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyCompetitorMappingDaoImpl::findCompetitorsForProperty : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deletePropertyCompetitorsForProperty(
			PropertyDetails propertyDetails) throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from PropertyCompetitorMapping where propertyDetails = :propertyDetails");
			query.setParameter("propertyDetails", propertyDetails);
			query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyCompetitorMappingDaoImpl::deletePropertyCompetitorsForProperty : "
					+ e.getCause().getMessage());
			throw new ISellDBException(-1, e.getCause().getMessage());
		}
		
	}
}
