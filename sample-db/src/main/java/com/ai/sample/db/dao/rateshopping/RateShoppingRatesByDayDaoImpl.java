package com.ai.sample.db.dao.rateshopping;

import java.util.Date;

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
import com.ai.sample.db.dao.property.mapping.PropertyRoomTypeMappingDao;
import com.ai.sample.db.dao.property.mapping.RateShoppingPropertyDetailsMappingDao;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;
import com.ai.sample.db.model.property.mapping.RateShoppingPropertyDetailsMapping;
import com.ai.sample.db.model.rateshopping.RateShoppingRatesByDay;

@Repository("rateShoppingRatesByDayDao")
public class RateShoppingRatesByDayDaoImpl extends AbstractDao implements RateShoppingRatesByDayDao{

	static final Logger LOGGER = Logger.getLogger(RateShoppingRatesByDayDaoImpl.class);

	@Autowired
	RateShoppingPropertyDetailsMappingDao rateShoppingPropertyDetailsMappingDao;

	@Autowired
	PropertyRoomTypeMappingDao propertyRoomTypeMappingDao;

	@Override
	public void saveRateShoppingRatesByDay(RateShoppingRatesByDay rateData)
			throws ISellDBException {
		try
		{
			saveOrUpdate(rateData);
		}
		catch(HibernateException | PSQLException e)
		{
			LOGGER.error("Exception in RateShoppingRatesByDayDaoImpl::saveRateShoppingRatesByDay: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void findMinimumRateForPropertyForDay(
			PropertyDetails propertyDetails, Date date)
					throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(RateShoppingPropertyDetailsMapping.class);
			criteria.add(Restrictions.eq("propertyDetails",propertyDetails));
		}
		catch(HibernateException | PSQLException e)
		{
			LOGGER.error("Exception in RateShoppingRatesByDayDaoImpl::findMinimumRateForPropertyForDay: " + e.getMessage());	
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public RateShoppingRatesByDay findRateShoppingRateByCheckinDayRoomTypeDiscount(
			RateShoppingPropertyDetailsMapping rateShoppingPropertyDetails,
			Date checkinDate, String roomType, double netRate) throws ISellDBException {
		try
		{
			PropertyRoomTypeMapping propertyRoomTypeMapping = propertyRoomTypeMappingDao.findRoomTypeByNameForProperty(roomType, rateShoppingPropertyDetails.getPropertyDetails());

			final Criteria criteria = getSession().createCriteria(RateShoppingRatesByDay.class);
			criteria.add(Restrictions.eq("rateShoppingPropertyDetailsID",rateShoppingPropertyDetails));
			criteria.add(Restrictions.eq("checkinDate",checkinDate));
			criteria.add(Restrictions.eq("roomTypeDetails",propertyRoomTypeMapping));
			criteria.add(Restrictions.eq("netRate", netRate));
			return (RateShoppingRatesByDay) criteria.uniqueResult();
		}
		catch(HibernateException | PSQLException e)
		{
			LOGGER.error("Exception in RateShoppingRatesByDayDaoImpl::findRateShoppingRateByCheckinDayRoomTypeDiscount: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteRatesByPropertyRoomTypeMapping(
			PropertyRoomTypeMapping propertyRoomTypeMapping)
			throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from RateShoppingRatesByDay where roomtypedetailsid = :roomTypeDetails");
			query.setParameter("roomTypeDetails", propertyRoomTypeMapping);
			query.executeUpdate();
		}
		catch(HibernateException | PSQLException e)
		{
			LOGGER.error("Exception in RateShoppingRatesByDayDaoImpl::deleteRatesByPropertyRoomTypeMapping: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteRatesForProperty(PropertyDetails propertyDetails)
			throws ISellDBException {
		try
		{
			Query query = getSession().createSQLQuery(
					"delete from rate_shopping_rates_by_day where  roomtypedetailsid in "
					+ "( select id from property_room_type_mapping where propertydetailsid = :propertyDetails) ");
			query.setInteger("propertyDetails", propertyDetails.getId());
			query.executeUpdate();
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("Exception in RateShoppingRatesByDayDaoImpl::deleteRatesForProperty: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}


}
