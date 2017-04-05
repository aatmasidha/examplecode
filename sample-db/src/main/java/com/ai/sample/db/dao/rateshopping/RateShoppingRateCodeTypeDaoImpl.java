package com.ai.sample.db.dao.rateshopping;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.rateshopping.RateShoppingRateCodeType;
import com.ai.sample.db.model.rateshopping.RateShoppingEngineData;

@Repository("rateShoppingRateCodeTypeDao")
public class RateShoppingRateCodeTypeDaoImpl extends AbstractDao implements RateShoppingRateCodeTypeDao{

	static final Logger LOGGER = Logger.getLogger(RateShoppingRateCodeTypeDaoImpl.class);

	@Override
	public RateShoppingRateCodeType findRateCodeForType(String rateCode,
			String rateShoppingEngine) throws ISellDBException {
		try
		{
		final Criteria criteriaEngine = getSession().createCriteria(RateShoppingEngineData.class);
		criteriaEngine.add(Restrictions.eq("name",rateShoppingEngine));
		RateShoppingEngineData rateShoppingEngineData = (RateShoppingEngineData) criteriaEngine.uniqueResult();
		
		if( rateShoppingEngineData == null )
		{
			LOGGER.error("Rate Engine not defined in the database: " + rateShoppingEngine);
			throw new ISellDBException(1, "Rate Engine not defined in the database: " + rateShoppingEngine);
		}
		if(rateCode == null || rateCode.length() <= 0)
		{
			 rateCode = "0";
		}
		Criteria criteria = getSession().createCriteria(RateShoppingRateCodeType.class);
		criteria.add(Restrictions.eq("rateCode",rateCode));
		RateShoppingRateCodeType rateCodeType = (RateShoppingRateCodeType) criteria.uniqueResult();
		if(rateCodeType == null)
		{
			rateCode = "-1";
			criteria = getSession().createCriteria(RateShoppingRateCodeType.class);
			criteria.add(Restrictions.eq("rateCode",rateCode));
			rateCodeType = (RateShoppingRateCodeType) criteria.uniqueResult();
			if(rateCodeType == null)
			{
				LOGGER.error("Rate Code not defined in the database: " + rateCode + " For Engine: " + rateShoppingEngine);
				throw new ISellDBException(1, "Rate Code not defined in the database: " + rateCode + " For Engine: " + rateShoppingEngine);
			}
		}
		return  rateCodeType;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RateShoppingRateCodeTypeDaoImpl::findRateCodeForType : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}


/*	@Override
	public void saveRateShoppingEngineData(
			RateShoppingEngineData rateShoppingEngineData)
			throws JDBCException, SQLException {
		saveOrUpdate(rateShoppingEngineData);
	}

	@Override
	public RateShoppingEngineData findRateShoppingEngineDataByName(String enginName)
			throws HibernateException {
		final Criteria criteria = getSession().createCriteria(RateShoppingEngineData.class);
		criteria.add(Restrictions.eq("name",enginName));
		RateShoppingEngineData rateShoppingEngineData = (RateShoppingEngineData) criteria.uniqueResult();
		return  rateShoppingEngineData;
	}*/
}
