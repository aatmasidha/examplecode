package com.ai.sample.db.dao.configuration;

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
import com.ai.sample.db.model.configuration.Currency;

@Repository("currencyDao")
public class CurrencyDaoImpl extends AbstractDao implements CurrencyDao {
	
	static final Logger LOGGER = Logger.getLogger(CurrencyDaoImpl.class);
	
	public void saveCurrency(Currency currency) throws ISellDBException{
		try
		{
			getSession().flush();
			saveOrUpdate(currency);
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CurrencyDaoImpl::saveCurrency : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Currency> findAllCurrencies() throws ISellDBException{
		try
		{
			final Criteria criteria = getSession().createCriteria(Currency.class);
			return (List<Currency>) criteria.list();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CurrencyDaoImpl::findAllCurrencies : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	public Currency findCurrencyByName(String currency) throws ISellDBException{
		try
		{		
			final Criteria criteria = getSession().createCriteria(Currency.class);
			criteria.add(Restrictions.eq("name", currency));
	//		criteria.setCacheable(true);
			Currency currencyModel = (Currency) criteria.uniqueResult();
			return currencyModel;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CurrencyDaoImpl::findCurrencyByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	public void deleteCurrencyByName(String currency) throws ISellDBException{
		try
		{
			Query query = getSession().createSQLQuery(
					"delete from Currency where name = :name");
			query.setString("name", currency);
			query.executeUpdate();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CurrencyDaoImpl::deleteCurrencyByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public Currency getOrCreateCurrencyByName(String currencyName)
			throws ISellDBException{
		try
		{
			Currency currency = findCurrencyByName(currencyName);
			if (currency == null) {
				currency = new Currency(currencyName);
				saveCurrency(currency);
			}
			return currency;
		}catch (HibernateException e) {
				LOGGER.error("Exception in CurrencyDaoImpl::deleteCurrencyByName : "
						+ e.getMessage());
				throw new ISellDBException(-1, e.getMessage());
			}
	}

	

}
