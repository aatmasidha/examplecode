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

@Repository("recommendedPriceAllByDayDao")
public class RecommendedPriceAllByDayDaoImpl extends AbstractDao implements RecommendedPriceAllByDayDao{

	static final Logger LOGGER = Logger.getLogger(RecommendedPriceAllByDayDaoImpl.class);

	@Autowired
	private PropertyDetailsDao propertyDetailsDao;

	@Override
	public void deleteRecommendedPriceAllByDayForProperty(
			PropertyDetails propertyDetails) throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from RecommendedPriceAllByDay where propertyDetails = :propertydetailsid");
			query.setParameter("propertydetailsid", propertyDetails);
			query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RecommendedPriceByDayDaoImpl::deleteRoomTypesForProperty : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

}
