package com.ai.sample.db.dao.rateshopping;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.rateshopping.RateShoppingEngineData;

@Repository("rateShoppingEngineDataDao")
public class RateShoppingEngineDataDaoImpl extends AbstractDao implements RateShoppingEngineDataDao{

	static final Logger LOGGER = Logger.getLogger(RateShoppingEngineDataDaoImpl.class);


	@Override
	public void saveRateShoppingEngineData(
			RateShoppingEngineData rateShoppingEngineData)
					throws ISellDBException {
		try
		{
			saveOrUpdate(rateShoppingEngineData);
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RateShoppingEngineDataDaoImpl::saveRateShoppingEngineData : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public RateShoppingEngineData findRateShoppingEngineDataByName(String enginName)
			throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(RateShoppingEngineData.class);
			criteria.add(Restrictions.eq("name",enginName));
			RateShoppingEngineData rateShoppingEngineData = (RateShoppingEngineData) criteria.uniqueResult();
			return  rateShoppingEngineData;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RateShoppingEngineDataDaoImpl::saveRateShoppingEngineData : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
