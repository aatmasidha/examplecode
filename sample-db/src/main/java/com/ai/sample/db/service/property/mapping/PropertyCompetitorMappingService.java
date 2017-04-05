package com.ai.sample.db.service.property.mapping;

import java.util.List;

import com.ai.sample.common.dto.CompetitorDetailsDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;

public interface PropertyCompetitorMappingService {

	public void saveOrUpdatePropertyCompetitors( CompetitorDetailsDTO competitorDetailsDTO) throws ISellDBException ;
	
	public List<CompetitorDetailsDTO> getAllCompetitorsForProperty(PropertyDetailsDTO propertyDetailsDTO) throws ISellDBException;
}
