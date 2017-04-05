package com.ai.sample.db.dao.configuration;

import java.util.List;

import org.hibernate.HibernateException;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.configuration.State;

public interface StateDao {

	void saveState(State state) throws ISellDBException;
	
	List<State> findAllStates() throws ISellDBException;
	
	void deleteStateByName(String StateName) throws ISellDBException;
	
	State getOrCreateStateByName(String stateName, String countryName) throws ISellDBException;

	State findStateByName(String State, String countryName)
			throws ISellDBException;

	List<State> findStatesForCountry(String countryName)throws ISellDBException;
}
