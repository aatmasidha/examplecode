package com.ai.sample.db.dao.pricing;

import java.util.List;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.algo.SeasonDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

public interface SeasonDetailsByDayDao {

	void saveSeasonDetailsByDay(SeasonDetails seasonDetails) throws ISellDBException;
	
	List<SeasonDetails> findSeasonDetails() throws ISellDBException;
	
	SeasonDetails findSeasonDetailsFromWeekNumberForProperty(PropertyDetailsDTO  propertyDto, int weekNumber) throws ISellDBException;
	
	void deleteSeasonDetailsForProperty(PropertyDetailsDTO propertyDetailsDto) throws ISellDBException;
	
	void deleteSeasonDetailsForProperty(PropertyDetails propertyDetails) throws ISellDBException;
	
	void commit() throws ISellDBException;
}
