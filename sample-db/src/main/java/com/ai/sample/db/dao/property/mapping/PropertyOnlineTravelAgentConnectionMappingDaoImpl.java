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

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.configuration.CityDao;
import com.ai.sample.db.dao.ota.OnlineTravelAgentDetailsDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyOnlineTravelAgentConnectionMapping;

@Repository("propertyOnlineTravelAgentConnectionMappingDao")
public class PropertyOnlineTravelAgentConnectionMappingDaoImpl extends AbstractDao implements
PropertyOnlineTravelAgentConnectionMappingDao {

	@Autowired
	PropertyDetailsDao propertyDetailsDao;
	
	@Autowired
	CityDao cityDao;
	
	@Autowired
	OnlineTravelAgentDetailsDao onlineTravelAngentDetailsDao;
	
	static final Logger LOGGER = Logger
			.getLogger(PropertyOnlineTravelAgentConnectionMappingDaoImpl.class);


	@Override
	public PropertyOnlineTravelAgentConnectionMapping findPropertyOnlineTravelAgentByPropertyAndOta(
			PropertyDetailsDTO propertyDetailsDto, String otaName) throws ISellDBException {
		try
		{
			PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);
			final Criteria criteria = getSession().createCriteria(
					PropertyOnlineTravelAgentConnectionMapping.class);
			criteria.add(Restrictions.eq("propertyDetails",propertyDetails));
			criteria.add(Restrictions.eq("propertyOtaName",otaName).ignoreCase());
			PropertyOnlineTravelAgentConnectionMapping otaPropertyMapping = (PropertyOnlineTravelAgentConnectionMapping) criteria.uniqueResult();
			LOGGER.debug("Exit PropertyOnlineTravelAgentConnectionMappingDaoImpl::findPropertyOnlineTravelAgentByProperty");
			return  otaPropertyMapping;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyOnlineTravelAgentConnectionMappingDaoImpl::findPropertyOnlineTravelAgentByPropertyAndOta : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}

	}


	@Override
	public void saveOrUpdatePropertyOtaMapping(
			PropertyOnlineTravelAgentConnectionMapping propertyOnlineTravelAgentConnectionMapping)
			throws ISellDBException {
		try
		{
			saveOrUpdate(propertyOnlineTravelAgentConnectionMapping);
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("Exception in PropertyOnlineTravelAgentConnectionMappingDaoImpl::saveOrUpdatePropertyOtaMapping : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}


	@Override
	public List<PropertyOnlineTravelAgentConnectionMapping> findPropertyOnlineTravelAgentByPropertyAndOta(
			PropertyDetailsDTO propertyDetailsDto) throws ISellDBException {
		try
		{
			PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);
			final Criteria criteria = getSession().createCriteria(
					PropertyOnlineTravelAgentConnectionMapping.class);
			criteria.add(Restrictions.eq("propertyDetails",propertyDetails));
			
			List<PropertyOnlineTravelAgentConnectionMapping> otaPropertyMappingList =  criteria.list();
			LOGGER.debug("Exit PropertyOnlineTravelAgentConnectionMappingDaoImpl::findPropertyOnlineTravelAgentByProperty");
			return  otaPropertyMappingList;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyOnlineTravelAgentConnectionMappingDaoImpl::findPropertyOnlineTravelAgentByPropertyAndOta : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}


	@Override
	public void deleteConnectionForOta(PropertyDetailsDTO propertyDetailsDto,
			String propertyOtaName) throws ISellDBException {
		try {
			
			PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);
			if( propertyDetails == null)
			{
				LOGGER.error("Exception in PropertyOTAHealthMappingDaoImpl::deletePropertyOTAHealthMappingForOTA : "
						+ " Property does not exist: " + propertyDetailsDto.getName());
				throw new ISellDBException(-1, "Exception in PropertyOTAHealthMappingDaoImpl::deletePropertyOTAHealthMappingForOTA : "
						+ " Property does not exist: " + propertyDetailsDto.getName());
			}
			Query query = getSession().createQuery(
					"delete from PropertyOnlineTravelAgentConnectionMapping where propertydetailsid = :propertydetailsid and propertyOtaName = :propertyOtaName");
			query.setParameter("propertydetailsid", propertyDetails);
			query.setParameter("propertyOtaName", propertyOtaName);
			query.executeUpdate();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyOTAHealthMappingDaoImpl::deletePropertyOTAHealthMappingForOTA : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
		
	}


	@Override
	public void deleteConnectionDetailsForProperty(
			PropertyDetails propertyDetails) throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from PropertyOnlineTravelAgentConnectionMapping where propertyDetails = :propertyDetails");
			query.setParameter("propertyDetails", propertyDetails);
			query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyOnlineTravelAgentConnectionMappingDaoImpl::deleteConnectionDetailsForProperty : "
					+ e.getCause().getMessage());
			throw new ISellDBException(-1, e.getCause().getMessage());
		}
		
	}
	
}
