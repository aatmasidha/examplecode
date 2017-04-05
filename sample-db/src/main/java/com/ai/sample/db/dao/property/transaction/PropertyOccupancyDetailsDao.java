package com.ai.sample.db.dao.property.transaction;

import java.util.Date;
import java.util.List;

import com.ai.sample.common.dto.OccupancyDetailsDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.transaction.PropertyOccupancyDetails;

public interface PropertyOccupancyDetailsDao {

	void savePropertyOccupancyDetails(PropertyOccupancyDetails propertyOccupancyDetails) throws ISellDBException ;
	
	PropertyOccupancyDetails findPropertyOccupancyByDate(PropertyDetailsDTO propertyDetailsDto, Date occupancyDate, String roomType) throws ISellDBException;
	
	List<OccupancyDetailsDTO> findOccupancyDetailsForISell(PropertyDetails propertyDetails, Date businessDate,
			int numReportDays) throws ISellDBException;
	
	void flush();

	void clear();

	public void deleteOccupancyDetailsForProperty(PropertyDetails propertyDetails) throws ISellDBException;
}
