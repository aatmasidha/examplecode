package com.ai.sample.db.dao.property.mapping;

import java.util.Date;
import java.util.List;

import com.ai.sample.common.dto.isell.ISellEventDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyEventMapping;

public interface PropertyEventMappingDao {
	public void saveOrUpdatePropertyCompetitorMappingDetails(PropertyEventMapping propertyEventMapping) throws ISellDBException;
	public List<PropertyEventMapping> findEventsForProperty(PropertyDetails propertyDetails) throws ISellDBException;
	
	List<ISellEventDetailsDTO>  findEventsFromToDate(PropertyDetails propertyDetails,
			Date businessDate, int numReportDays)  throws ISellDBException;
	
	void deletePropertyEvents(PropertyDetails propertyDetails) throws ISellDBException;
}

