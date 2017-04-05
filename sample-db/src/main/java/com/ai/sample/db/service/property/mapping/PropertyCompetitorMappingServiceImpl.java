package com.ai.sample.db.service.property.mapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.CompetitorDetailsDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.configuration.CityDao;
import com.ai.sample.db.dao.configuration.CountryDao;
import com.ai.sample.db.dao.configuration.PropertyStatusMasterDao;
import com.ai.sample.db.dao.configuration.StateDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.mapping.PropertyCompetitorMappingDao;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyCompetitorMapping;

@Service("propertyCompetitorMappingService")
@Transactional
public class PropertyCompetitorMappingServiceImpl implements PropertyCompetitorMappingService {

	Logger logger = Logger.getLogger(PropertyCompetitorMappingServiceImpl.class);

	@Autowired
	PropertyCompetitorMappingDao propertyCompetitorMappingDao;
	
	@Autowired
	PropertyDetailsDao propertyDetailsDao;

	@Autowired
	CountryDao countryDao;

	@Autowired
	StateDao stateDao;

	@Autowired
	CityDao cityDao;

	@Autowired
	PropertyStatusMasterDao propertyStatusDao;


	@Override
	public void saveOrUpdatePropertyCompetitors(
			CompetitorDetailsDTO competitorDetailsDTO) throws ISellDBException {
		
		PropertyDetailsDTO propertyDetailsDto = competitorDetailsDTO.getPropertyDetailsDto();
		
		PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);
		
		PropertyDetailsDTO competitorsDto = competitorDetailsDTO.getCompetitorPropertyDetailsDto();
		PropertyDetails competitorDetails  = propertyDetailsDao.findPropertyDetails(competitorsDto);
		
		
		PropertyCompetitorMapping propertyCompetitorMapping = new PropertyCompetitorMapping(propertyDetails, competitorDetails, competitorDetailsDTO.getCompetitorSequence(), new Date(), competitorDetailsDTO.isActive());

		propertyCompetitorMappingDao.saveOrUpdatePropertyCompetitorMappingDetails(propertyCompetitorMapping);

	}

	@Override
	public List<CompetitorDetailsDTO> getAllCompetitorsForProperty(
			PropertyDetailsDTO propertyDetailsDTO) throws ISellDBException {
		List<CompetitorDetailsDTO>  competitorDetailsDtoList = new ArrayList<CompetitorDetailsDTO>();
		PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDTO);
		propertyDetailsDTO.setLatitude(propertyDetails.getLatitude());
		propertyDetailsDTO.setLongitude(propertyDetails.getLongitude());
		propertyDetailsDTO.setCapacity(propertyDetails.getCapcity());
		propertyDetailsDTO.setHotelChainName(propertyDetails.getHotelChainDetails().getName());
		propertyDetailsDTO.setHotelCurrency(propertyDetails.getHotelCurrency().getName());
		propertyDetailsDTO.setAddress(propertyDetails.getAddress());
		List<PropertyCompetitorMapping> propertyCompetitorList = propertyCompetitorMappingDao.findCompetitorsForProperty(propertyDetails);
		
		for(PropertyCompetitorMapping propertyCompetitorMapping : propertyCompetitorList)
		{
			PropertyDetails competitorDetails = propertyCompetitorMapping.getCompetitorDetails();

			String hotelChainName = "";
			if(competitorDetails.getHotelChainDetails() != null && competitorDetails.getHotelChainDetails().getName().length() > 0 )
			{
				hotelChainName =competitorDetails.getHotelChainDetails().getName();
			}
			
			CityDTO cityDto = new CityDTO(competitorDetails.getCity().getName(), competitorDetails.getCity().getState().getName(), competitorDetails.getCity().getState().getCountry().getName());
			PropertyDetailsDTO competitorPropertyDetailsDto = new PropertyDetailsDTO(competitorDetails.getName(), hotelChainName, competitorDetails.getAddress(), cityDto, 
					competitorDetails.getPinCode(), competitorDetails.getCapcity(), competitorDetails.getPropertyStatus().getName(), competitorDetails.getHotelCurrency().getName(), 
					competitorDetails.getLastUpdateDate(), competitorDetails.getLatitude(), competitorDetails.getLongitude());
			
			CompetitorDetailsDTO competitorDetailsDTO = new CompetitorDetailsDTO(propertyDetailsDTO, competitorPropertyDetailsDto, propertyCompetitorMapping.getCompetitorSequence(), propertyCompetitorMapping.isIsactive(), propertyCompetitorMapping.getUpdateDate());
			competitorDetailsDtoList.add(competitorDetailsDTO);
		}
		
		return competitorDetailsDtoList;
	}

}