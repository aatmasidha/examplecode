package com.ai.sample.db.dao.rateshopping;

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
import com.ai.sample.db.model.rateshopping.RateShoppingPropertyDetails;

/**
 * @author aparna
 *
 */
@Repository("rateShoppingPropertyDetailsDao")
public class RateShoppingPropertyDetailsDaoImpl extends AbstractDao implements RateShoppingPropertyDetailsDao{

	static final Logger LOGGER = Logger.getLogger(RateShoppingPropertyDetailsDaoImpl.class);

	@Override
	public void saveRateShoppingPropertyDetails(
			RateShoppingPropertyDetails propertyDetails)
			throws ISellDBException {
		try
		{
			saveOrUpdate(propertyDetails);
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("Exception in RateShoppingPropertyDetailsDaoImpl::saveRateShoppingPropertyDetails : " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateShoppingPropertyDetails> findAllRateShoppingProperties()
			throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(RateShoppingPropertyDetails.class);
			return (List<RateShoppingPropertyDetails>) criteria.list();
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("Exception in RateShoppingPropertyDetailsDaoImpl::findAllRateShoppingProperties : " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public RateShoppingPropertyDetails findRateShoppingPropertyDetailsPropertyCode(
			String propertyCode) throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(RateShoppingPropertyDetails.class);
			criteria.add(Restrictions.eq("propertyCode",propertyCode));
			return  (RateShoppingPropertyDetails) criteria.uniqueResult();
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("Exception in RateShoppingPropertyDetailsDaoImpl::findRateShoppingPropertyDetailsPropertyCode : " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public RateShoppingPropertyDetails findRateShoppingPropertyDetails(
			String name, String city) throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(RateShoppingPropertyDetails.class);
			criteria.add(Restrictions.eq("name",name));
			criteria.add(Restrictions.eq("city",city));
			RateShoppingPropertyDetails propertyDetails = (RateShoppingPropertyDetails) criteria.uniqueResult();
			return  propertyDetails;
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("Exception in RateShoppingPropertyDetailsDaoImpl::findRateShoppingPropertyDetails : " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteRateShoppingPropertyDetailsByPropertyCode(String propertyCode)
			throws ISellDBException {
		try
		{
			Query query = getSession().createSQLQuery("delete from RateShoppingPropertyDetails where propertyCode = :name");
			query.setString("propertyCode", propertyCode);
			query.executeUpdate();
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("Exception in RateShoppingPropertyDetailsDaoImpl::deleteRateShoppingPropertyDetailsByPropertyCode : " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<RateShoppingPropertyDetails> findListOfUnmappedRateShoppingProperties()
			throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					 " from RateShoppingPropertyDetails  where propertycode not in ( select rateShoppingPropertyUID from "
					 + "RateShoppingPropertyDetailsMapping "
					 + ")");
			List<RateShoppingPropertyDetails> rateShoppingPropertyDetailsList = query.list();
			return  rateShoppingPropertyDetailsList;
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("Exception in RateShoppingPropertyDetailsDaoImpl::findListOfUnmappedRateShoppingProperties : " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
