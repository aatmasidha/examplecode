package com.ai.sample.db.service.rateshopping;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.configuration.OnlineTravelAgentDetailsDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.ota.OnlineTravelAgentDetailsDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.mapping.RateShoppingPropertyDetailsMappingDao;
import com.ai.sample.db.model.configuration.State;
import com.ai.sample.db.model.property.configuration.HotelChainDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.RateShoppingPropertyDetailsMapping;

@Service("rateShoppingPropertyDetailsMappingService")
@Transactional
public class RateShoppingPropertyDetailsMappingServiceImpl implements
RateShoppingPropertyDetailsMappingService {

	Logger logger = Logger.getLogger(RateShoppingPropertyDetailsMappingServiceImpl.class);

	@Autowired
	private RateShoppingPropertyDetailsMappingDao rateShoppingPropertyDetailsMappingDao;

	@Autowired
	private PropertyDetailsDao propertyDetailsDao;

	@Autowired
	private OnlineTravelAgentDetailsDao onlineTravelAgentDetailsDao;

	@Override
	public Map<String, RateShoppingPropertyDetailsDTO> getAllRateShoppingDetailsPerProperty(PropertyDetailsDTO propertyDetailsDTO)
			throws ISellDBException {

		PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDTO);
		List<RateShoppingPropertyDetailsMapping> rateShoppingPropDetailsList = rateShoppingPropertyDetailsMappingDao.findAllRateShoppingPropertyDetails(propertyDetails);
		Map<String, RateShoppingPropertyDetailsDTO> rateShoppingPropertyDetailsDTOMap = new HashMap<String,RateShoppingPropertyDetailsDTO >();
		for (RateShoppingPropertyDetailsMapping temp : rateShoppingPropDetailsList) {

			RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO = new RateShoppingPropertyDetailsDTO();
			PropertyDetails propDetails = temp.getPropertyDetails();

			HotelChainDetails hotelChain = propDetails.getHotelChainDetails();
			String chainName = "";
			if(hotelChain != null)
			{
				chainName = hotelChain.getName();
			}
			/*CityDTO cityDTO = new CityDTO( propDetails.getCountry().getName(), propDetails.getState().getName(), 
					propDetails.getCity().getName());*/

			CityDTO cityDTO = new CityDTO( propDetails.getCity().getName(), propDetails.getCity().getState().getName(), 
					propDetails.getCity().getState().getCountry().getName() );

			propertyDetailsDTO = new PropertyDetailsDTO(propDetails.getName(), chainName, propDetails.getAddress(), 
					/*propDetails.getCountry().getName(),propDetails.getState().getName(),
					propDetails.getCity().getName()*/cityDTO, propDetails.getPinCode(), propDetails.getCapcity(),
					propDetails.getPropertyStatus().getName(), propDetails.getHotelCurrency().getName(), new Date(),
					propDetails.getLatitude(), propDetails.getLongitude());
			if(temp.getOtaID() != null )
			{
				OnlineTravelAgentDetailsDTO otaDTO = new OnlineTravelAgentDetailsDTO(temp.getOtaID().getName(), temp.getOtaID().getName());
				rateShoppingPropertyDetailsDTO.setOtaDetails(otaDTO);
			}
			rateShoppingPropertyDetailsDTO.setRateShoppingOtaID(temp.getRateShoppingOtaID());
			rateShoppingPropertyDetailsDTO.setRateShoppingPropertyUID(temp.getRateShoppingPropertyUID());
			rateShoppingPropertyDetailsDTO.setPropertyDetails(propertyDetailsDTO);
			rateShoppingPropertyDetailsDTOMap.put(propDetails.getName(), rateShoppingPropertyDetailsDTO);
		}
		return rateShoppingPropertyDetailsDTOMap;
	}

	@Override
	public void saveOrUpdateRateShoppingPropertyDetailsMapping(
			RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO) {

	}

	@Override
	public void saveOrUpdateRateShoppingPropertyDetailsMappingForProperty(
			PropertyDetailsDTO propertyDetailsDTO,
			RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO) throws ISellDBException  {
		rateShoppingPropertyDetailsMappingDao.updatePropertyDetailsForRateShopping(rateShoppingPropertyDetailsDTO, propertyDetailsDTO);
	}

	@Override
	public RateShoppingPropertyDetailsDTO findRateShoppingDetailsForPropertyByPropertyCode(
			String rateShoppingPropertyUID, String otaName) throws ISellDBException {

		return getRateShoppingDetailsBasedOnCommonInformation(rateShoppingPropertyUID, otaName);
	}


	private RateShoppingPropertyDetailsDTO getRateShoppingDetailsBasedOnCommonInformation(String rateShoppingPropertyID, String rateShoppingOtaID) throws ISellDBException
	{
		RateShoppingPropertyDetailsMapping rateShoppingPropDetails = rateShoppingPropertyDetailsMappingDao.findRateShoppingPropertyDetails(rateShoppingPropertyID, rateShoppingOtaID);


		RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO = new RateShoppingPropertyDetailsDTO();

		if( rateShoppingPropDetails == null )
		{
			logger.error("RateShopping details not configured for property for rateshoppingID: " + rateShoppingPropertyID);
			throw new ISellDBException(-1, "RateShopping details not configured for property for rateshoppingID: " + rateShoppingPropertyID);
		}
		PropertyDetails propDetails = rateShoppingPropDetails.getPropertyDetails();
		if( propDetails == null )
		{
			logger.error("RateShopping property details  not configured for property for rateshoppingID: " + rateShoppingPropertyID);
			throw new ISellDBException(-1, "RateShopping property details not configured for property for rateshoppingID: " + rateShoppingPropertyID);
		}
		HotelChainDetails hotelChain = propDetails.getHotelChainDetails();
		String chainName = "";
		if(hotelChain != null)
		{
			chainName = hotelChain.getName();
		}

		String stateName = "";
		String countryName = "";

		State state = propDetails.getCity().getState();
		if(state != null )
		{
			stateName = state.getName();
			countryName = state.getCountry().getName();
		}

		CityDTO cityDTO = new CityDTO( propDetails.getCity().getName(), stateName, countryName);

		PropertyDetailsDTO propertyDetailsDTO = new PropertyDetailsDTO(propDetails.getName(), chainName, propDetails.getAddress(), 
				/*propDetails.getCountry().getName(),propDetails.getState().getName(),
				propDetails.getCity().getName()*/cityDTO , propDetails.getPinCode(), propDetails.getCapcity(),
				propDetails.getPropertyStatus().getName(), propDetails.getHotelCurrency().getName(), new Date(),
				propDetails.getLatitude(), propDetails.getLongitude());
		if(rateShoppingPropDetails.getOtaID() != null )
		{
			OnlineTravelAgentDetailsDTO otaDTO = new OnlineTravelAgentDetailsDTO(rateShoppingPropDetails.getOtaID().getName(), rateShoppingPropDetails.getOtaID().getName());
			rateShoppingPropertyDetailsDTO.setOtaDetails(otaDTO);
		}
		rateShoppingPropertyDetailsDTO.setRateShoppingOtaID(rateShoppingPropDetails.getRateShoppingOtaID());
		rateShoppingPropertyDetailsDTO.setRateShoppingPropertyUID(rateShoppingPropDetails.getRateShoppingPropertyUID());
		rateShoppingPropertyDetailsDTO.setPropertyDetails(propertyDetailsDTO);

		propertyDetailsDTO.setPropertyCode(rateShoppingPropDetails.getRateShoppingPropertyUID());
		return rateShoppingPropertyDetailsDTO;
	}

	@Override
	public void saveOrUpdateRateShoppingPropertyDetailsMappingForProperty(
			PropertyDetails propertyDetails,
			RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO)
					throws ISellDBException {
		rateShoppingPropertyDetailsMappingDao.updatePropertyDetailsForRateShopping(propertyDetails, rateShoppingPropertyDetailsDTO);

	}

	@Override
	public RateShoppingPropertyDetailsDTO findRateShoppingDetailsForPropertyByPropertyCode(
			PropertyDetailsDTO propertyDetailsDTO, String rateShoppingOtaID)
					throws ISellDBException {
		PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDTO);

		RateShoppingPropertyDetailsMapping rateShoppingPropertyDetails = rateShoppingPropertyDetailsMappingDao.findRateShoppingPropertyDetailsByPropertyIDAndRateShoppingOtaID(propertyDetails, rateShoppingOtaID);
		if( rateShoppingPropertyDetails == null )
		{
			throw new ISellDBException(1, "Rateshopping property details mapping missing for: " + propertyDetailsDTO.getName() + " For Rate Engine rateshopping ID: " + rateShoppingOtaID);
		}
		return getRateShoppingDetailsBasedOnCommonInformation(rateShoppingPropertyDetails.getRateShoppingPropertyUID(),rateShoppingOtaID );
	}

	@Override
	public List<RateShoppingPropertyDetailsDTO> findRateShoppingPropertyMappingForProperty(
			PropertyDetailsDTO propertyDetailsDto) throws ISellDBException {
		PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);

		List<RateShoppingPropertyDetailsDTO> rateShoppingPropertyDetailsDTOList = new ArrayList<RateShoppingPropertyDetailsDTO>();
		List<RateShoppingPropertyDetailsMapping> propertyRateShoppingOTAList =  rateShoppingPropertyDetailsMappingDao.findAllRateShoppingPropertyDetails(propertyDetails);
		for (RateShoppingPropertyDetailsMapping temp : propertyRateShoppingOTAList) {

			RateShoppingPropertyDetailsDTO rateShoppingPropertyDetailsDTO = new RateShoppingPropertyDetailsDTO();
			PropertyDetails propDetails = temp.getPropertyDetails();

			HotelChainDetails hotelChain = propDetails.getHotelChainDetails();
			String chainName = "";
			if(hotelChain != null)
			{
				chainName = hotelChain.getName();
			}
			/*CityDTO cityDTO = new CityDTO( propDetails.getCountry().getName(), propDetails.getState().getName(), 
					propDetails.getCity().getName());*/

			CityDTO cityDTO = new CityDTO( propDetails.getCity().getName(), propDetails.getCity().getState().getName(), 
					propDetails.getCity().getState().getCountry().getName() );

			propertyDetailsDto = new PropertyDetailsDTO(propDetails.getName(), chainName, propDetails.getAddress(), 
					/*propDetails.getCountry().getName(),propDetails.getState().getName(),
					propDetails.getCity().getName()*/cityDTO, propDetails.getPinCode(), propDetails.getCapcity(),
					propDetails.getPropertyStatus().getName(), propDetails.getHotelCurrency().getName(), new Date(),
					propDetails.getLatitude(), propDetails.getLongitude());
			if(temp.getOtaID() != null )
			{
				OnlineTravelAgentDetailsDTO otaDTO = new OnlineTravelAgentDetailsDTO(temp.getOtaID().getName(), temp.getOtaID().getName());
				rateShoppingPropertyDetailsDTO.setOtaDetails(otaDTO);
			}
			rateShoppingPropertyDetailsDTO.setRateShoppingOtaID(temp.getRateShoppingOtaID());
			rateShoppingPropertyDetailsDTO.setRateShoppingPropertyUID(temp.getRateShoppingPropertyUID());
			rateShoppingPropertyDetailsDTO.setPropertyDetails(propertyDetailsDto);
			rateShoppingPropertyDetailsDTOList.add(rateShoppingPropertyDetailsDTO);
		}
		return rateShoppingPropertyDetailsDTOList;
	}



}
