package com.ai.sample.db.dao.configuration;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.rateshopping.RateShoppingPropertyDetailsDaoImpl;
import com.ai.sample.db.model.configuration.City;
import com.ai.sample.db.model.configuration.Country;
import com.ai.sample.db.model.configuration.State;

@Repository("cityDao")
public class CityDaoImpl extends AbstractDao implements CityDao {

	static final Logger LOGGER = Logger
			.getLogger(CityDaoImpl.class);

	@Autowired
	StateDao stateDao;

	@Override
	public void saveCity(City city) throws ISellDBException {
		try {
			saveOrUpdate(city);
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CityDaoImpl::saveCity : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<City> findAllCities() throws ISellDBException {
		try {
			final Criteria criteria = getSession().createCriteria(City.class);

			return (List<City>) criteria.list();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CityDaoImpl::findAllCities : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public City findCityByName(CityDTO cityDto) throws ISellDBException {

		try {
			final Criteria countryCriteria = getSession().createCriteria(
					Country.class);
			countryCriteria.add(Restrictions.eq("name",
					cityDto.getCountryName()));
			countryCriteria.setCacheable(true);
			Country countryModel = (Country) countryCriteria.uniqueResult();

			final Criteria stateCriteria = getSession().createCriteria(
					State.class);
			stateCriteria.add(Restrictions.eq("name", cityDto.getStateName()));
			stateCriteria.add(Restrictions.eq("country", countryModel));
			stateCriteria.setCacheable(true);
			State stateModel = (State) stateCriteria.uniqueResult();

			final Criteria criteria = getSession().createCriteria(City.class);
			criteria.add(Restrictions.eq("name", cityDto.getCityName()));
			criteria.add(Restrictions.eq("state", stateModel));
			criteria.setCacheable(true);
			City cityModel = (City) criteria.uniqueResult();
			return cityModel;
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CityDaoImpl::findCityByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteCityByName(String city) throws ISellDBException {
		try {
			Query query = getSession().createSQLQuery(
					"delete from City where name = :name");
			query.setString("name", city);
			query.executeUpdate();
		}

		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CityDaoImpl::deleteCityByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public City findCityByStateAndName(String cityName, String stateName,
			String countryName) throws ISellDBException {
		try
		{
			State state = stateDao.getOrCreateStateByName(stateName, countryName);
			final Criteria criteria = getSession().createCriteria(City.class);
			criteria.add(Restrictions.eq("name", cityName));
			criteria.add(Restrictions.eq("state", state));
			City city = (City) criteria.uniqueResult();
			if (city == null) {
				city = new City(cityName, state);
				saveOrUpdate(city);
			}
			return city;
		}

		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CityDaoImpl::findCityByStateAndName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public City findOrCreateCityByStateAndName(String cityName,
			String stateName, String countryName) throws ISellDBException {
		try
		{
			return findCityByStateAndName(cityName, stateName, countryName);
		}

		catch ( HibernateException e) {
			LOGGER.error("Exception in CityDaoImpl::findOrCreateCityByStateAndName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<City> findCitiesForState(State stateObj)
			throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(City.class);
			criteria.add(Restrictions.eq("state", stateObj));
			return criteria.list();
		}

		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in CityDaoImpl::findCitiesForState : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
