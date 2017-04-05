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

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.configuration.Country;
import com.ai.sample.db.model.configuration.State;

@Repository("stateDao")
public class StateDaoImpl extends AbstractDao implements StateDao {

	static final Logger LOGGER = Logger.getLogger(StateDaoImpl.class);

	@Autowired
	CountryDao countryDao;

	@Override
	public void saveState(State state) throws ISellDBException {
		try {
			saveOrUpdate(state);
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in StateDaoImpl::saveState : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<State> findAllStates() throws ISellDBException {
		try {
			final Criteria criteria = getSession().createCriteria(State.class);
			return (List<State>) criteria.list();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in StateDaoImpl::findAllStates : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public State findStateByName(String State, String countryName)
			throws ISellDBException {
		try {
			final Criteria criteria = getSession().createCriteria(State.class);
			criteria.add(Restrictions.eq("name", State));
			Country country = countryDao.findCountryByName(countryName);
			criteria.add(Restrictions.eq("country", country));
			State stateModel = (State) criteria.uniqueResult();
			return stateModel;
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in StateDaoImpl::findStateByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteStateByName(String state) throws ISellDBException {
		try {
			Query query = getSession().createSQLQuery(
					"delete from State where name = :name");
			query.setString("name", state);
			query.executeUpdate();
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in StateDaoImpl::deleteStateByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public State getOrCreateStateByName(String stateName, String countryName)
			throws ISellDBException {
		try {
			State stateModel = findStateByName(stateName, countryName);
			if (stateModel == null) {
				Country country = countryDao
						.getOrCreateCountryByName(countryName);
				stateModel = new State(stateName, country);
				saveOrUpdate(stateModel);
			}
			return stateModel;
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in StateDaoImpl::getOrCreateStateByName : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<State> findStatesForCountry(String countryName)
			throws ISellDBException {
		try {
			final Criteria criteria = getSession().createCriteria(State.class);
			Country country = countryDao.findCountryByName(countryName);
			criteria.add(Restrictions.eq("country", country));
			List<State> stateList = criteria.list();
			return stateList;
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in StateDaoImpl::findStatesForCountry : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
