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
import com.ai.sample.db.model.configuration.Country;

@Repository("countryDao")
public class CountryDaoImpl extends AbstractDao implements CountryDao {

	static final Logger LOGGER = Logger.getLogger(CountryDaoImpl.class);

	@Override
	public void saveCountry(Country country) throws ISellDBException {
		try
		{
			saveOrUpdate(country);
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CountryDaoImpl::saveCountry : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Country> findAllCountries() throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(Country.class);
			return (List<Country>) criteria.list();
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CountryDaoImpl::findAllCountries : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public Country findCountryByName(String countryName)
			throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(Country.class);
			criteria.add(Restrictions.eq("name", countryName));
			Country countryModel = (Country) criteria.uniqueResult();
			return countryModel;
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CountryDaoImpl::findCountryByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteCountryByName(String countryName) throws ISellDBException {
		try {
			Query query = getSession().createSQLQuery(
					"delete from Country where name = :name");
			query.setString("name", countryName);
			query.executeUpdate();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CountryDaoImpl::deleteCountryByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public Country getOrCreateCountryByName(String countryName)
			throws ISellDBException {
		try {
			Country country = findCountryByName(countryName);
			if (country == null) {
				country = new Country(countryName);
				saveCountry(country);
			}
			return country;
		} catch (HibernateException e) {
			LOGGER.error("Exception in CountryDaoImpl::getOrCreateCountryByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
