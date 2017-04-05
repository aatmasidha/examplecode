package com.ai.sample.db.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.rateshopping.RateShoppingPropertyDetailsDao;
import com.ai.sample.db.model.rateshopping.RateShoppingPropertyDetails;

@Service("rateShoppingPropertyDetailsService")
@Transactional
public class RateShoppingPropertyDetailsServiceImpl implements
RateShoppingPropertyDetailsService {
	Logger logger = Logger.getLogger(RateShoppingPropertyDetailsServiceImpl.class);
	@Autowired
	RateShoppingPropertyDetailsDao rateShoppingPropertyDetailsDao;
	
	@Override
	public void saveOrUpdateRateShoppingRateShoppingPropertyDetails(
			RateShoppingPropertyDTO propertyDetailsDTO) throws ISellDBException {
		
		logger.debug("Update /  Save Rateshopping property record for property" + propertyDetailsDTO.toString());
		try
		{
			RateShoppingPropertyDetails  rateShoppingPropertyDetails = null;
			String propertyCode = propertyDetailsDTO.getPropertyCode();
			rateShoppingPropertyDetails = rateShoppingPropertyDetailsDao.findRateShoppingPropertyDetailsPropertyCode(propertyCode);
			if(rateShoppingPropertyDetails == null )
			{
				rateShoppingPropertyDetails 
				= new RateShoppingPropertyDetails(propertyDetailsDTO.getName(), propertyCode, 
						propertyDetailsDTO.getHotelChainName(), propertyDetailsDTO.getAddress(), 
						propertyDetailsDTO.getCountry(), propertyDetailsDTO.getState(), propertyDetailsDTO.getCity(), 
						propertyDetailsDTO.getPinCode(), propertyDetailsDTO.getCapcity(), propertyDetailsDTO.getRanking(), 
						propertyDetailsDTO.getPropertyStatus(), propertyDetailsDTO.getHotelCurrency(), propertyDetailsDTO.getLongitude(), 
						propertyDetailsDTO.getLatitude(), propertyDetailsDTO.getCreationDate(), propertyDetailsDTO.getLastUpdateDate());
			}	
			rateShoppingPropertyDetailsDao.saveRateShoppingPropertyDetails(rateShoppingPropertyDetails);
		}
		catch(RuntimeException e)
		{
			e.printStackTrace();
			logger.error("RuntimeException in RateShoppingPropertyDetailsServiceImpl : " + e.getCause());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<RateShoppingPropertyDTO> findAllHotelRateShoppingPropertyDetails()
			throws ISellDBException {
		List<RateShoppingPropertyDetails> rateShoppingPropertyList = rateShoppingPropertyDetailsDao.findAllRateShoppingProperties();
		List<RateShoppingPropertyDTO> rateShoppingPropertyDTOList = new ArrayList<RateShoppingPropertyDTO>();

		for(RateShoppingPropertyDetails rateShoppingProperty : rateShoppingPropertyList)
		{
			RateShoppingPropertyDTO rateShoppingPropertyDTO = new RateShoppingPropertyDTO(rateShoppingProperty.getName(), rateShoppingProperty.getPropertyCode(), rateShoppingProperty.getHotelChainName(), 
					rateShoppingProperty.getAddress(), rateShoppingProperty.getCountry(), rateShoppingProperty.getState(), rateShoppingProperty.getCity(), 
					rateShoppingProperty.getPinCode(), rateShoppingProperty.getCapcity(), /*rateShoppingProperty.getRanking(),*/ rateShoppingProperty.getPropertyStatus(), 
					/*rateShoppingProperty.getHotelCurrency(),*/ rateShoppingProperty.getLongitude(), rateShoppingProperty.getLatitude(), null, rateShoppingProperty.getCreationDate(), rateShoppingProperty.getLastUpdateDate());
			
			rateShoppingPropertyDTOList.add(rateShoppingPropertyDTO);
		}
		return rateShoppingPropertyDTOList;
	}

	@Override
	public List<RateShoppingPropertyDTO> findRateShoppingPropertyDetailsByPropertyName(
			String propertyName) throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RateShoppingPropertyDTO> findRateShoppingPropertyDetailsByPropertyCode(
			String propertyCode) throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RateShoppingPropertyDTO> findRateShoppingUnmappedProperties()
			throws ISellDBException {
		List<RateShoppingPropertyDetails> rateShoppingPropertyDetailsList = rateShoppingPropertyDetailsDao.findListOfUnmappedRateShoppingProperties();	
		List<RateShoppingPropertyDTO> rateShoppingUnmappedPropertyDetailsDTOList = new ArrayList<RateShoppingPropertyDTO>();
		for(RateShoppingPropertyDetails rateShoppingPropertyDetails : rateShoppingPropertyDetailsList)
		{
			CityDTO cityDto = new CityDTO(rateShoppingPropertyDetails.getCity(), 
					rateShoppingPropertyDetails.getState(), rateShoppingPropertyDetails.getCountry());

			RateShoppingPropertyDTO propertyDetailsDto =  new RateShoppingPropertyDTO(rateShoppingPropertyDetails.getId(), rateShoppingPropertyDetails.getName(), rateShoppingPropertyDetails.getPropertyCode(), 
					rateShoppingPropertyDetails.getHotelChainName(), rateShoppingPropertyDetails.getAddress(), 
					rateShoppingPropertyDetails.getCountry(), rateShoppingPropertyDetails.getState(), rateShoppingPropertyDetails.getCity(), 
					rateShoppingPropertyDetails.getPinCode(), rateShoppingPropertyDetails.getCapcity(), rateShoppingPropertyDetails.getPropertyStatus(), 
					rateShoppingPropertyDetails.getLongitude(), rateShoppingPropertyDetails.getLatitude(), null, 
					rateShoppingPropertyDetails.getCreationDate(), new Date());
			propertyDetailsDto.setRanking(rateShoppingPropertyDetails.getRanking());
			rateShoppingUnmappedPropertyDetailsDTOList.add(propertyDetailsDto);
		}
		 
		return rateShoppingUnmappedPropertyDetailsDTOList;
	}
}
