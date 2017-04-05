package com.ai.sample.db.dao.property.transaction;

import java.util.Date;
import java.util.List;

import com.ai.sample.common.dto.ChannelManagerISellDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.transaction.ChannelManagerAvailabilityDetails;

public interface ChannelManagerAvailabilityDetailsDao {

	void saveChannelManagerAvailabilityDetails(ChannelManagerAvailabilityDetails channelManagerAvailabilityDetails) throws ISellDBException ;
	
	ChannelManagerAvailabilityDetails findChannelManagerAvailabilityByDate(PropertyDetailsDTO propertyDetailsDto, Date occupancyDate, String roomType) throws ISellDBException;
	
	List<ChannelManagerISellDTO> findChannelManagerAvailabilityDetailsForISell(PropertyDetails propertyDetails, Date businessDate,
			int numReportDays) throws ISellDBException;
	
	void deleteChannelManagerDataForFuture(PropertyDetails propertyDetails, Date businessDate) throws ISellDBException;
	
	void deleteChannelManagerDataForFuture(PropertyDetails propertyDetails) throws ISellDBException;
	
	void flush() throws ISellDBException;

	void clear() throws ISellDBException;

}
