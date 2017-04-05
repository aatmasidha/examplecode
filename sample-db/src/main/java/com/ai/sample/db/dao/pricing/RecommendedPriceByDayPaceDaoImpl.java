package com.ai.sample.db.dao.pricing;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Repository("recommendedPriceByDayPaceDao")
public class RecommendedPriceByDayPaceDaoImpl extends AbstractDao implements RecommendedPriceByDayPaceDao{

	static final Logger LOGGER = Logger.getLogger(RecommendedPriceByDayPaceDaoImpl.class);

	@Autowired
	private PropertyDetailsDao propertyDetailsDao;

	@Override
	public void deleteRecommendedPriceByDayPaceForProperty(
			PropertyDetails propertyDetails) throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from RecommendedPriceByDayPace where propertyDetails = :propertydetailsid");
			query.setParameter("propertydetailsid", propertyDetails);
			query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RecommendedPriceByDayPaceDaoImpl::deleteRecommendedPriceByDayPaceForProperty : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}



}
