package com.ai.sample.db.dao.property.mapping;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyCompetitorMapping;

public interface PropertyCompetitorMappingDao {
	public void saveOrUpdatePropertyCompetitorMappingDetails(PropertyCompetitorMapping propertyCompetitorMapping) throws ISellDBException;
	public List<PropertyCompetitorMapping> findCompetitorsForProperty(PropertyDetails propertyDetails) throws ISellDBException;
	
	void deletePropertyCompetitorsForProperty(PropertyDetails propertyDetails) throws ISellDBException;
}
