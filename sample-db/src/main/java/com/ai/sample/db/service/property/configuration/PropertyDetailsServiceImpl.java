package com.ai.sample.db.service.property.configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.constants.EnumConstants.ExecutionStatusCodeConstant;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.dto.rateshopping.RateShoppingPropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.configuration.CityDao;
import com.ai.sample.db.dao.configuration.CountryDao;
import com.ai.sample.db.dao.configuration.CurrencyDao;
import com.ai.sample.db.dao.configuration.PropertyStatusMasterDao;
import com.ai.sample.db.dao.configuration.StateDao;
import com.ai.sample.db.dao.property.configuration.HotelChainDetailsDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.mapping.PropertyRoomTypeMappingDao;
import com.ai.sample.db.model.configuration.City;
import com.ai.sample.db.model.configuration.Country;
import com.ai.sample.db.model.configuration.Currency;
import com.ai.sample.db.model.configuration.PropertyStatusMaster;
import com.ai.sample.db.model.configuration.State;
import com.ai.sample.db.model.property.configuration.HotelChainDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.service.rateshopping.RateShoppingPropertyDetailsMappingService;

@Service("propertyDetailsService")
@Transactional
public class PropertyDetailsServiceImpl implements PropertyDetailsService {

	Logger logger = Logger.getLogger(PropertyDetailsServiceImpl.class);

	@Autowired
	PropertyDetailsDao propertyDetailsDao;

	@Autowired
	HotelChainDetailsDao hotelChainDetailsDao;

	@Autowired
	CurrencyDao currencyDao;

	@Autowired
	CountryDao countryDao;

	@Autowired
	StateDao stateDao;

	@Autowired
	CityDao cityDao;

	@Autowired
	PropertyStatusMasterDao propertyStatusDao;

	@Autowired
	RateShoppingPropertyDetailsMappingService rateShoppingPropertyDetailsMappingService;

	@Autowired
	PropertyRoomTypeMappingDao propertyRoomTypeMappingDao;
	
	@Override
	public PropertyDetails saveOrUpdatePropertyDetails(
			PropertyDetailsDTO propertyDetailsDTO) throws ISellDBException {
		try {
			PropertyDetails propertyDetails = propertyDetailsDao
					.findPropertyDetails(propertyDetailsDTO);
			if (propertyDetails != null) {
				editPropertyDetails(propertyDetails, propertyDetailsDTO);
				logger.info("Property data already exists in the database so updating the data"
						+ propertyDetailsDTO.toString());
				return propertyDetails;
			} else {
				propertyDetails = savePropertyDetailsData(propertyDetailsDTO);
				return propertyDetails;
			}
		} catch (HibernateException e) {
			logger.error("Exception in PropertyDetailsServiceImpl is: "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());

		}
	}

	private PropertyDetails savePropertyDetailsData(
			PropertyDetailsDTO propertyDetailsDTO) throws ISellDBException {

		PropertyDetails propertyDetails = null;
		HotelChainDetails hotelChain = hotelChainDetailsDao
				.findHotelChainByName(propertyDetailsDTO.getHotelChainName());
		String hashCode = (propertyDetailsDTO.getName() + propertyDetailsDTO
				.getCityDto()).hashCode() + "";

		// If currency does not exists in the database it will be added
		// to database
		String currencyName = propertyDetailsDTO.getHotelCurrency();
		Currency currency = null;
		if (currencyName != null && currencyName.length() > 0) {
			currency = currencyDao.getOrCreateCurrencyByName(currencyName);
		}

		// First country needs to checked and later added as State is
		// associated to country
		// which will also get automatically added
		CityDTO cityDTO = propertyDetailsDTO.getCityDto();
		String countryName = cityDTO.getCountryName();
		Country country = countryDao.getOrCreateCountryByName(countryName);
		if (country == null) {
			logger.error("Country not defined in the database: " + countryName);
			throw new ISellDBException(-1,
					"Country not defined in the database: " + countryName);
		}
		String stateName = cityDTO.getStateName();
		State state = stateDao.getOrCreateStateByName(stateName, countryName);
		if (state == null) {
			logger.error("State not defined in the database: " + stateName);
			throw new ISellDBException(-1,
					"State not defined in the database: " + stateName);
		}

		String cityName = cityDTO.getCityName();
		City city = cityDao.findCityByStateAndName(cityName, stateName,
				countryName);
		if (city == null) {
			logger.error("City not found in the database: " + cityName
					+ " For state: " + stateName);
			throw new ISellDBException(-1, "City not found in the database: "
					+ cityName + " For state: " + stateName);
		}

		PropertyStatusMaster propertyStatus = propertyStatusDao
				.findPropertyStatusByName(propertyDetailsDTO
						.getPropertyStatus());
		if (propertyStatus == null) {
			logger.error("Property Status not defined in the database: "
					+ propertyDetailsDTO.getPropertyStatus());
			throw new ISellDBException(-1,
					"Property Status not defined in the database: "
							+ propertyDetailsDTO.getPropertyStatus());
		}

		propertyDetails = new PropertyDetails(propertyDetailsDTO.getName(),
				hashCode, hotelChain, propertyDetailsDTO.getAddress(), country,
				state, city, propertyDetailsDTO.getPinCode(),
				propertyDetailsDTO.getCapacity(), propertyStatus, currency,
				new Date(), propertyDetailsDTO.getLongitude(),
				propertyDetailsDTO.getLatitude());

		propertyDetailsDao.savePropertyDetails(propertyDetails);

		return propertyDetails;
	}

	private void editPropertyDetails(PropertyDetails propertyDetails,
			PropertyDetailsDTO propertyDetailsDTO) throws HibernateException,
			ISellDBException {
		// TODO set all other values
		propertyDetails.setAddress(propertyDetailsDTO.getAddress());
		propertyDetails.setLatitude(propertyDetailsDTO.getLatitude());
	}

	@Override
	public List<PropertyDetailsDTO> findAllHotelPropertyDetails()
			throws ISellDBException {
		List<PropertyDetails> propertyDetailList = propertyDetailsDao
				.findAllPropertyDetails();
		List<PropertyDetailsDTO> propertyDetailsDTOList = new ArrayList<PropertyDetailsDTO>();
		for (PropertyDetails propertyDetails : propertyDetailList) {
			String channelName = "";
			HotelChainDetails hotelChainDetails = propertyDetails
					.getHotelChainDetails();
			if (hotelChainDetails != null) {
				channelName = hotelChainDetails.getName();
			}
			CityDTO cityDTO = new CityDTO(propertyDetails.getCity().getName(),
					propertyDetails.getCity().getState().getName(),
					propertyDetails.getCity().getState().getCountry().getName());

			// In this function we do not get the property code
			PropertyDetailsDTO propertyDetailsDTO = new PropertyDetailsDTO(
					propertyDetails.getId(), propertyDetails.getName(), "",
					channelName, propertyDetails.getAddress(), cityDTO,
					propertyDetails.getPinCode(), propertyDetails.getCapcity(),
					propertyDetails.getPropertyStatus().getName(),
					propertyDetails.getHotelCurrency().getName(),
					propertyDetails.getLatitude(),
					propertyDetails.getLongitude(), new Date());

			propertyDetailsDTOList.add(propertyDetailsDTO);
		}
		return propertyDetailsDTOList;
	}

	@Override
	public List<PropertyDetailsDTO> findPropertyDetailsByPropertyName(
			String propertyName) throws ISellDBException {

		List<PropertyDetailsDTO> propertyDetailsDTOList = new ArrayList<PropertyDetailsDTO>();

		List<PropertyDetails> propertyDetailList = propertyDetailsDao
				.findPropertyDetailsByName(propertyName);
		for (PropertyDetails propertyDetails : propertyDetailList) {
			String channelName = "";
			HotelChainDetails hotelChainDetails = propertyDetails
					.getHotelChainDetails();
			if (hotelChainDetails != null) {
				channelName = hotelChainDetails.getName();
			}

			CityDTO cityDTO = new CityDTO(propertyDetails.getCity().getName(),
					propertyDetails.getCity().getState().getName(),
					propertyDetails.getCity().getState().getCountry().getName());

			PropertyDetailsDTO propertyDetailsDTO = new PropertyDetailsDTO(
					propertyDetails.getName(), channelName,
					propertyDetails.getAddress(), /*
					 * propertyDetails.getCountry().
					 * getName(),
					 * propertyDetails.getState
					 * ().getName(),
					 * propertyDetails.
					 * getCity().getName(),
					 */cityDTO,
					 propertyDetails.getPinCode(), propertyDetails.getCapcity(),
					 propertyDetails.getPropertyStatus().getName(),
					 propertyDetails.getHotelCurrency().getName(), new Date(),
					 propertyDetails.getLatitude(),
					 propertyDetails.getLongitude());

			propertyDetailsDTOList.add(propertyDetailsDTO);
		}
		return propertyDetailsDTOList;
	}

	@Override
	public PropertyDetailsDTO findPropertyDetailsByPropertyInformation(
			PropertyDetailsDTO propertyDetailsDto) throws ISellDBException {
		PropertyDetails propertyDetails = propertyDetailsDao
				.findPropertyDetails(propertyDetailsDto);
		String hotelChain = "";
		if (propertyDetails == null) {
			throw new ISellDBException(1,
					"Property information is not present in the database for property: "
							+ propertyDetailsDto.getName());
		}
		if (propertyDetails.getHotelChainDetails() != null) {
			hotelChain = propertyDetails.getHotelChainDetails().getName();
		}

		propertyDetailsDto = new PropertyDetailsDTO(propertyDetails.getName(),
				hotelChain, propertyDetails.getAddress(),
				propertyDetailsDto.getCityDto(), propertyDetails.getPinCode(),
				propertyDetails.getCapcity(), propertyDetails
				.getPropertyStatus().getName(), propertyDetails
				.getHotelCurrency().getName(), new Date(),
				propertyDetails.getLatitude(), propertyDetails.getLongitude());

		return propertyDetailsDto;
	}

	@Override
	public void saveOrUpdatePropertyDetails(
			RateShoppingPropertyDetailsDTO rateShoppingPropertyDTO)
					throws ISellDBException {
		try {
			PropertyDetailsDTO propertyDetailsDto = rateShoppingPropertyDTO
					.getPropertyDetails();
			PropertyDetails propDetails = saveOrUpdatePropertyDetails(propertyDetailsDto);
			if (propDetails == null) {
				logger.error("PropertyDetails information could not be created for: "
						+ rateShoppingPropertyDTO.getPropertyDetails()
						.toString());
				throw new ISellDBException(-1, "Property not found for details"
						+ rateShoppingPropertyDTO.getPropertyDetails()
						.toString());
			}
			rateShoppingPropertyDetailsMappingService
			.saveOrUpdateRateShoppingPropertyDetailsMappingForProperty(
					propDetails, rateShoppingPropertyDTO);
		} catch (HibernateException | ISellDBException e) {
			logger.error("Exception while storing rate metrics data."
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void saveOrUpdatePropertyDetails(
			PropertyDetailsDTO propertyDetailsDto,
			PropertyDetailsDTO oldPropertyDetails,
			PropertyDetailsDTO newPropertyDetails, boolean isEdit)
					throws ISellDBException {
		if (isEdit) {
			PropertyDetails propertyDetails = propertyDetailsDao
					.findPropertyDetails(oldPropertyDetails);
			if (propertyDetails == null) {
				logger.error("PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Property data to map to roomtype missing: "
						+ propertyDetailsDto);
				throw new ISellDBException(
						ExecutionStatusCodeConstant.DATAERROR.getCode(),
						"PropertyRoomTypeMappingServiceImpl::savePropertyRoomTypeMapping Property data to map to roomtype missing: "
								+ propertyDetailsDto);
			}

			String propertyDetailsOld = oldPropertyDetails.getName();

			String propertyRoomTypeNew = newPropertyDetails.getName();

			PropertyDetails propertyRoomTypeDataNew = propertyDetailsDao
					.findPropertyDetails(newPropertyDetails);
			if ((propertyRoomTypeDataNew != null)
					&& (propertyRoomTypeDataNew.getId() != propertyDetails
					.getId())) {
				logger.error("Property " + "Details already exists: "
						+ propertyRoomTypeNew
						+ " Can not rename Property Details: "
						+ propertyDetailsOld);
				throw new ISellDBException(
						ExecutionStatusCodeConstant.DATAERROR.getCode(),
						"Property Details already exists: "
								+ propertyRoomTypeNew + " Can not property: "
								+ propertyDetailsOld);
			}

			propertyDetails.setAddress(newPropertyDetails.getAddress());
			propertyDetails.setCapcity(newPropertyDetails.getCapacity());
			HotelChainDetails hotelChainDetails = hotelChainDetailsDao
					.findHotelChainByName(newPropertyDetails
							.getHotelChainName());

			String currencyName = newPropertyDetails.getHotelCurrency();
			Currency currency = null;
			if (currencyName != null && currencyName.length() > 0) {
				currency = currencyDao.getOrCreateCurrencyByName(currencyName);
			}
			propertyDetails.setHotelCurrency(currency);
			propertyDetails.setLastUpdateDate(new Date());
			propertyDetails.setLatitude(newPropertyDetails.getLatitude());
			propertyDetails.setLongitude(newPropertyDetails.getLongitude());
			propertyDetails.setPinCode(newPropertyDetails.getPinCode());

			PropertyStatusMaster propertyStatus = propertyStatusDao
					.findPropertyStatusByName(newPropertyDetails
							.getPropertyStatus());
			if (propertyStatus == null) {
				logger.error("Property Status not defined in the database: "
						+ newPropertyDetails.getPropertyStatus());
				throw new ISellDBException(-1,
						"Property Status not defined in the database: "
								+ newPropertyDetails.getPropertyStatus());
			}
			propertyDetails.setPropertyStatus(propertyStatus);

			CityDTO cityDTO = newPropertyDetails.getCityDto();
			String countryName = cityDTO.getCountryName();
			Country country = countryDao.getOrCreateCountryByName(countryName);
			if (country == null) {
				logger.error("Country not defined in the database: "
						+ countryName);
				throw new ISellDBException(-1,
						"Country not defined in the database: " + countryName);
			}
			String stateName = cityDTO.getStateName();
			State state = stateDao.getOrCreateStateByName(stateName,
					countryName);
			if (state == null) {
				logger.error("State not defined in the database: " + stateName);
				throw new ISellDBException(-1,
						"State not defined in the database: " + stateName);
			}

			String cityName = cityDTO.getCityName();
			City city = cityDao.findCityByStateAndName(cityName, stateName,
					countryName);
			if (city == null) {
				logger.error("City not found in the database: " + cityName
						+ " For state: " + stateName);
				throw new ISellDBException(-1,
						"City not found in the database: " + cityName
						+ " For state: " + stateName);
			}
			propertyDetails.setCity(city);
			String propertyUID = (newPropertyDetails.getName() + newPropertyDetails
					.getCityDto()).hashCode() + "";

			propertyDetails.setPropertyUID(propertyUID);
			propertyDetailsDao.savePropertyDetails(propertyDetails);
		} else {
			savePropertyDetailsData(newPropertyDetails);
		}
	}

	@Override
	public void deletePropertyDetails(PropertyDetailsDTO propertyDetailsDto)
			throws ISellDBException {
		try
		{
			PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);
			
			propertyDetailsDao.deletePropertyDetails(propertyDetails);
		}
		catch (HibernateException | ISellDBException e) {
			logger.error("Exception while storing rate metrics data."
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

}