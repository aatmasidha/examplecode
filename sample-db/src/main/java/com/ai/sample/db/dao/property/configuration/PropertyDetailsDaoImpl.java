package com.ai.sample.db.dao.property.configuration;

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
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.configuration.CityDao;
import com.ai.sample.db.dao.configuration.PropertyStatusMasterDao;
import com.ai.sample.db.dao.isell.InterceptPointDetailsDao;
import com.ai.sample.db.dao.isell.OtaPerformanceSummaryDetailsByDayDao;
import com.ai.sample.db.dao.isell.SlopePointDetailsDao;
import com.ai.sample.db.dao.pricing.PropertyDayOfWeekDefinitionDao;
import com.ai.sample.db.dao.pricing.RecommendedPriceAllByDayDao;
import com.ai.sample.db.dao.pricing.RecommendedPriceByDayDao;
import com.ai.sample.db.dao.pricing.RecommendedPriceByDayPaceDao;
import com.ai.sample.db.dao.pricing.SeasonDetailsByDayDao;
import com.ai.sample.db.dao.property.mapping.PropertyCompetitorMappingDao;
import com.ai.sample.db.dao.property.mapping.PropertyEventMappingDao;
import com.ai.sample.db.dao.property.mapping.PropertyOnlineTravelAgentConnectionMappingDao;
import com.ai.sample.db.dao.property.mapping.PropertyRoomTypeMappingDao;
import com.ai.sample.db.dao.property.mapping.RateShoppingPropertyDetailsMappingDao;
import com.ai.sample.db.dao.property.transaction.ChannelManagerAvailabilityDetailsDao;
import com.ai.sample.db.dao.property.transaction.PropertyOccupancyDetailsDao;
import com.ai.sample.db.dao.property.transaction.TransactionalDetailsDao;
import com.ai.sample.db.dao.rateshopping.RateShoppingRatesByDayDao;
import com.ai.sample.db.dao.rateshopping.RateShoppingRatesSummaryByDayDao;
import com.ai.sample.db.model.configuration.City;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

/**
 * @author aparna
 *
 */
@Repository("propertyDetailsDao")
public class PropertyDetailsDaoImpl extends AbstractDao implements PropertyDetailsDao{

	static final Logger LOGGER = Logger.getLogger(PropertyDetailsDaoImpl.class);

	@Autowired 
	CityDao cityDao;

	@Autowired  
	PropertyStatusMasterDao propertyStatusMasterDao;
	
	@Autowired
	PropertyRoomTypeMappingDao propertyRoomTypeMappingDao;
	
	@Autowired
	ChannelManagerAvailabilityDetailsDao channelManagerAvailabilityDetailsDao;
	
	@Autowired
	InterceptPointDetailsDao interceptPointDetailsDao;
	
	@Autowired
	PropertyCompetitorMappingDao propertyCompetitorMappingDao;
	
	@Autowired
	PropertyDayOfWeekDefinitionDao propertyDayOfWeekDefinitionDao;
	
	@Autowired
	PropertyEventMappingDao propertyEventMappingDao;
	
	@Autowired
	PropertyOnlineTravelAgentConnectionMappingDao propertyOnlineTravelAgentConnectionMappingDao;

	@Autowired
	RateShoppingPropertyDetailsMappingDao rateShoppingPropertyDetailsMappingDao;
	
	@Autowired
	SlopePointDetailsDao slopePointDetailsDao;
	
	@Autowired
	SeasonDetailsByDayDao seasonDetailsByDayDao;
	
	@Autowired
	PropertyOccupancyDetailsDao propertyOccupancyDetailsDao;
	
	@Autowired
	OtaPerformanceSummaryDetailsByDayDao otaPerformanceSummaryDetailsByDayDao;
	
	@Autowired
	TransactionalDetailsDao transactionalDetailsDao;

	@Autowired
	RecommendedPriceByDayDao recommendedPriceByDayDao;
	
	@Autowired
	RecommendedPriceAllByDayDao recommendedPriceAllByDayDao;
	
	@Autowired
	RecommendedPriceByDayPaceDao recommendedPriceByDayPaceDao;
	
	@Autowired
	RateShoppingRatesByDayDao rateShoppingRatesByDayDao;
	
	@Autowired
	RateShoppingRatesSummaryByDayDao rateShoppingRatesSummaryByDayDao;
	
	public void savePropertyDetails(PropertyDetails propertyDetails) throws ISellDBException {
		try
		{
			saveOrUpdate(propertyDetails);
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyDetailsDaoImpl::savePropertyDetails : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<PropertyDetails> findAllPropertyDetails() throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(PropertyDetails.class);
			return (List<PropertyDetails>) criteria.list();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyDetailsDaoImpl::findAllPropertyDetails : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	/**
	 * It may happen that Property with same name may exists at
	 * multiple locations so we are returning array
	 * But the property name and city combination must be unique
	 */
	@SuppressWarnings("unchecked")
	public List<PropertyDetails> findPropertyDetailsByName(String name) throws ISellDBException{
		try
		{
			final Criteria criteria = getSession().createCriteria(PropertyDetails.class);
			criteria.add(Restrictions.eq("name",name));
			//		PropertyDetails onlineAgent = (PropertyDetails) criteria.uniqueResult();
			return (List<PropertyDetails>) criteria.list();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyDetailsDaoImpl::findPropertyDetailsByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
		//		return  onlineAgent;
	}

	public void deletePropertyDetailsByName(String name) throws ISellDBException{
		try
		{
			Query query = getSession().createSQLQuery("delete from PropertyDetails where name = :name");
			query.setString("name", name);
			query.executeUpdate();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyDetailsDaoImpl::deletePropertyDetailsByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}


	/**
	 * It is assumed that a property name and city combination is unique
	 * so we will get unique property details 
	 */
	@Override
	public PropertyDetails findPropertyDetails(/*String name, CityDTO cityDto*/PropertyDetailsDTO propertyDetailsDTO)
			throws ISellDBException{
		try
		{
			CityDTO cityDto = propertyDetailsDTO.getCityDto();
			String name = propertyDetailsDTO.getName();
			City cityModel = cityDao.findCityByName(cityDto);
			final Criteria criteria = getSession().createCriteria(PropertyDetails.class);
			criteria.add(Restrictions.eq("name",name));
			criteria.add(Restrictions.eq("city",cityModel));

			if(propertyDetailsDTO.getLatitude() != 0 )
			{
				criteria.add(Restrictions.eq("latitude",propertyDetailsDTO.getLatitude()));
			}

			String address = propertyDetailsDTO.getAddress();
			if(address != null)
			{
				criteria.add(Restrictions.eq("address",propertyDetailsDTO.getAddress()));
			}

			List<PropertyDetails> propertyDetails =  criteria.list();

			if(propertyDetails != null && propertyDetails.size() > 1)
			{
				throw new HibernateException( "Multiple properties with Same name and City exist can not return value..."  + propertyDetailsDTO.toString());		
			}
			if(propertyDetails == null || propertyDetails.size() == 0 )
			{
				return null;
			}
			return  propertyDetails.get(0);
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyDetailsDaoImpl::findPropertyDetails : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public PropertyDetails findPropertyDetailsByUID(String propertyUID) throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(PropertyDetails.class);
			criteria.add(Restrictions.eq("propertyUID",propertyUID));
			PropertyDetails propertyDetails = (PropertyDetails) criteria.uniqueResult();
			return  propertyDetails;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyDetailsDaoImpl::findPropertyDetailsByUID : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<PropertyDetails> findPropertyDetailsByCity(CityDTO cityDto) throws ISellDBException {
		try
		{
			City cityModel = cityDao.findCityByName(cityDto);

			final Criteria criteria = getSession().createCriteria(PropertyDetails.class);
			criteria.add(Restrictions.eq("city",cityModel));
			return(List<PropertyDetails>) criteria.list();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyDetailsDaoImpl::findPropertyDetailsByCity : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void flush() throws ISellDBException{
		try
		{
			getSession().flush();
			propertyStatusMasterDao.flush();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyDetailsDaoImpl::flush : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void clear() throws ISellDBException{
		try
		{
			getSession().clear();
			propertyStatusMasterDao.clear();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyDetailsDaoImpl::clear : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deletePropertyDetails(PropertyDetails propertyDetails)
			throws ISellDBException {
		try
		{
			if(propertyDetails != null)
			{
				rateShoppingRatesByDayDao.deleteRatesForProperty(propertyDetails);
				
				propertyRoomTypeMappingDao.deleteRoomTypesForProperty(propertyDetails);
				
				channelManagerAvailabilityDetailsDao.deleteChannelManagerDataForFuture(propertyDetails);
				
				interceptPointDetailsDao.deleteInterceptPointDetailForProperty(propertyDetails);
	
				propertyCompetitorMappingDao.deletePropertyCompetitorsForProperty(propertyDetails);
				
				propertyDayOfWeekDefinitionDao.deletePropertyDayOfWeekDefinitionForProperty(propertyDetails);
				
				propertyEventMappingDao.deletePropertyEvents(propertyDetails);
				
				propertyOnlineTravelAgentConnectionMappingDao.deleteConnectionDetailsForProperty(propertyDetails);
				
				rateShoppingPropertyDetailsMappingDao.deleteRateShoppingPropertyDetailsByProperty(propertyDetails);
				
				seasonDetailsByDayDao.deleteSeasonDetailsForProperty(propertyDetails);
	
				slopePointDetailsDao.deleteSlopeDetailsForProperty(propertyDetails);
				
				propertyOccupancyDetailsDao.deleteOccupancyDetailsForProperty(propertyDetails);
				
				otaPerformanceSummaryDetailsByDayDao.deleteOTAPerformanceForProperty(propertyDetails);
				
				transactionalDetailsDao.deleteTransactionsForProperty(propertyDetails);
				
				recommendedPriceByDayDao.deleteRecommendedPriceByDayForProperty(propertyDetails);
				
				recommendedPriceAllByDayDao.deleteRecommendedPriceAllByDayForProperty(propertyDetails);
				
				recommendedPriceByDayPaceDao.deleteRecommendedPriceByDayPaceForProperty(propertyDetails);
				
				rateShoppingRatesSummaryByDayDao.deleteRatesSummaryForProperty(propertyDetails);
				
				Query query = getSession().createQuery(
						"delete from PropertyDetails where id = :id");
				query.setInteger("id", propertyDetails.getId());
				query.executeUpdate();
			}
			else
			{
				LOGGER.error("Property to delete does not exists");
				throw new ISellDBException(-1, "Property to delete does not exists");
			}
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyDetails::deletePropertyDetails : "
					+ e.getCause().getMessage());
			throw new ISellDBException(-1, e.getCause().getMessage());
		}
		
	}
}
