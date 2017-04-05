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
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.ota.OnlineTravelAgentDetailsDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.rateshopping.RateShoppingEngineDataDao;
import com.ai.sample.db.dao.rateshopping.RateShoppingPropertyDetailsDao;
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.RateShoppingPropertyDetailsMapping;
import com.ai.sample.db.model.rateshopping.RateShoppingEngineData;

@Repository("rateShoppingPropertyDetailsMappingDao")
public class RateShoppingPropertyDetailsMappingDaoImpl extends AbstractDao implements RateShoppingPropertyDetailsMappingDao{

	static final Logger LOGGER = Logger.getLogger(RateShoppingPropertyDetailsMappingDaoImpl.class);

	@Autowired
	RateShoppingEngineDataDao rateShoppingEngineDataDao;
	
	@Autowired
	OnlineTravelAgentDetailsDao onlineTravelAgentDao;
	
	@Autowired
	PropertyDetailsDao propertyDetailsDao;
	
	@Autowired
	RateShoppingPropertyDetailsDao rateShoppingPropertyDetailsDao;
	
	@Override
	public RateShoppingPropertyDetailsMapping findRateShoppingPropertyDetails(
			String rateShoppingPropertyUID, String otaName) throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(RateShoppingPropertyDetailsMapping.class);
			criteria.add(Restrictions.eq("rateShoppingPropertyUID",rateShoppingPropertyUID));
			criteria.add(Restrictions.eq("rateShoppingOtaID",otaName));
			RateShoppingPropertyDetailsMapping rateShoppingPropertyDetailsMapping = (RateShoppingPropertyDetailsMapping) criteria.uniqueResult();
			return  rateShoppingPropertyDetailsMapping;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RateShoppingPropertyDetailsMappingDaoImpl::findRateShoppingPropertyDetails : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateShoppingPropertyDetailsMapping> findAllRateShoppingPropertyDetails(PropertyDetails propertyDetails)  throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(RateShoppingPropertyDetailsMapping.class);
			criteria.add(Restrictions.eq("propertyDetails", propertyDetails));
			return ( List<RateShoppingPropertyDetailsMapping>) criteria.list();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RateShoppingPropertyDetailsMappingDaoImpl::findAllRateShoppingPropertyDetails : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void saveOrUpdateRateShoppingPropertyDetails(
			RateShoppingPropertyDetailsMapping rateShoppingPropertyDetailsMapping)
			throws ISellDBException {
		try
		{
			saveOrUpdate(rateShoppingPropertyDetailsMapping);
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RateShoppingPropertyDetailsMappingDaoImpl::saveOrUpdateRateShoppingPropertyDetails : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void updatePropertyDetailsForRateShopping(
			RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO,
			PropertyDetailsDTO propDetailsDTO) throws ISellDBException {
		try
		{
			PropertyDetails propDetails = propertyDetailsDao.findPropertyDetails(propDetailsDTO);
			updatePropertyDetailsForRateShopping(propDetails, rateShoppingPropertyDetailsDTO);
		}catch ( HibernateException e) {
			LOGGER.error("Exception in RateShoppingPropertyDetailsMappingDaoImpl::updatePropertyDetailsForRateShopping : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void updatePropertyDetailsForRateShopping(
			PropertyDetails propDetails,
			RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO) throws ISellDBException{
		
		try
		{
				RateShoppingEngineData rateShoppingEngineData = rateShoppingEngineDataDao.findRateShoppingEngineDataByName(rateShoppingPropertyDetailsDTO.getRateShoppinEngineName());
				OnlineTravelAgentDetails otaDetails = null;
				if(rateShoppingPropertyDetailsDTO.getOtaDetails() != null )
				{
					otaDetails = onlineTravelAgentDao.findOnlineTravelAngentByName(rateShoppingPropertyDetailsDTO.getOtaDetails().getOtaName());
				}
				Criteria criteria = getSession().createCriteria(RateShoppingPropertyDetailsMapping.class);
				criteria.add(Restrictions.eq("rateShoppingEngineData",rateShoppingEngineData));
				if(otaDetails != null )
				{			
					criteria.add(Restrictions.eq("otaID",otaDetails));
				}
				criteria.add(Restrictions.eq("propertyDetails",propDetails));
				RateShoppingPropertyDetailsMapping rateShoppingPropertyDetailsMapping = 
						(RateShoppingPropertyDetailsMapping)criteria.uniqueResult();
				if( rateShoppingPropertyDetailsMapping == null )
				{
					rateShoppingPropertyDetailsMapping = new RateShoppingPropertyDetailsMapping(rateShoppingEngineData, propDetails, rateShoppingPropertyDetailsDTO.getRateShoppingPropertyUID(), 
							otaDetails, rateShoppingPropertyDetailsDTO.getRateShoppingOtaID());
					saveOrUpdate(rateShoppingPropertyDetailsMapping);
				}
				else
				{
					rateShoppingPropertyDetailsMapping.setRateShoppingOtaID(rateShoppingPropertyDetailsDTO.getRateShoppingOtaID());
					rateShoppingPropertyDetailsMapping.setRateShoppingPropertyUID(rateShoppingPropertyDetailsDTO.getRateShoppingPropertyUID());
					rateShoppingPropertyDetailsMapping.setPropertyDetails(propDetails);
					saveOrUpdate(rateShoppingPropertyDetailsMapping);
				}
		}
		catch(HibernateException e )
		{
			LOGGER.error("Exception in RateShoppingPropertyDetailsMappingDaoImpl::updatePropertyDetailsForRateShopping" + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
		catch(Exception e)
		{
			LOGGER.error("Exception in RateShoppingPropertyDetailsMappingDaoImpl::updatePropertyDetailsForRateShopping" + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public RateShoppingPropertyDetailsMapping findRateShoppingPropertyDetailsByPropertyIDAndRateShoppingOtaID(
			PropertyDetails propertyDetails, String rateShoppingOtaID)
			throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(RateShoppingPropertyDetailsMapping.class);
			criteria.add(Restrictions.eq("propertyDetails",propertyDetails));
			criteria.add(Restrictions.eq("rateShoppingOtaID",rateShoppingOtaID));
			RateShoppingPropertyDetailsMapping rateShoppingPropertyDetailsMapping = (RateShoppingPropertyDetailsMapping) criteria.uniqueResult();
			return  rateShoppingPropertyDetailsMapping;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RateShoppingPropertyDetailsMappingDaoImpl::findRateShoppingPropertyDetailsByPropertyIDAndRateShoppingOtaID : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteRateShoppingPropertyDetailsByProperty(
			PropertyDetails propertyDetails) throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from RateShoppingPropertyDetailsMapping where propertyDetails = :propertyDetails");
			query.setParameter("propertyDetails", propertyDetails);
			query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RateShoppingPropertyDetailsMappingDaoImpl::deleteRateShoppingPropertyDetailsByProperty : "
					+ e.getCause().getMessage());
			throw new ISellDBException(-1, e.getCause().getMessage());
		}
	}
}
