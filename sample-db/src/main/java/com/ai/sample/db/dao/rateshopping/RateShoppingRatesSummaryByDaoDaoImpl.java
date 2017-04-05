package com.ai.sample.db.dao.rateshopping;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

/**
 * @author aparna
 *
 */
@Repository("rateShoppingRatesSummaryByDayDao")
public class RateShoppingRatesSummaryByDaoDaoImpl extends AbstractDao implements RateShoppingRatesSummaryByDayDao{

	static final Logger LOGGER = Logger.getLogger(RateShoppingRatesSummaryByDaoDaoImpl.class);

	
	@Override
	public void deleteRatesSummaryForProperty(PropertyDetails propertyDetails)
			throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from RateShoppingRatesSummaryByDay where  propertyDetails = :propertyDetails");
			query.setInteger("propertyDetails", propertyDetails.getId());
			query.executeUpdate();
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("Exception in RateShoppingRatesSummaryByDaoDaoImpl::deleteRatesSummaryForProperty: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
		
	}
}
