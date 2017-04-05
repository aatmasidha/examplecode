package com.ai.sample.db.service.property.transaction;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.AdditionalInformationDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.TransactionDetailsDTO;
import com.ai.sample.common.dto.TransactionalOtherDetailsDTO;
import com.ai.sample.common.dto.configuration.CityDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.configuration.CountryDao;
import com.ai.sample.db.dao.configuration.CurrencyDao;
import com.ai.sample.db.dao.ota.BookingStatusOtaDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.mapping.PropertyOnlineTravelAgentConnectionMappingDao;
import com.ai.sample.db.dao.property.mapping.PropertyRoomTypeMappingDao;
import com.ai.sample.db.dao.property.transaction.OtherTransactionalDetailsDao;
import com.ai.sample.db.dao.property.transaction.TransactionalDetailsDao;
import com.ai.sample.db.model.configuration.Country;
import com.ai.sample.db.model.configuration.Currency;
import com.ai.sample.db.model.ota.BookingStatusOta;
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;
import com.ai.sample.db.model.property.configuration.HotelChainDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyOnlineTravelAgentConnectionMapping;
import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;
import com.ai.sample.db.model.property.transaction.OtherTransactionalDetails;
import com.ai.sample.db.model.property.transaction.TransactionalDetails;
import com.ai.sample.db.type.MyJson;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


@Service("transactionalDetailsService")
@Transactional
public class TransactionalDetailsServiceImpl implements
TransactionalDetailsService {

	Logger logger = Logger.getLogger(TransactionalDetailsServiceImpl.class);

	@Autowired
	private TransactionalDetailsDao dao;

	@Autowired
	private PropertyOnlineTravelAgentConnectionMappingDao onlineTravelAgentDao;

	@Autowired
	private BookingStatusOtaDao bookingStatusDao;

	@Autowired
	private CurrencyDao currencyDao;
	
	@Autowired
	private CountryDao countryDao;

	@Autowired
	private PropertyDetailsDao propertyDetailsDao;

	@Autowired
	private PropertyOnlineTravelAgentConnectionMappingDao onlineTravelAgentByExtractDetailsDao;
	
	@Autowired
	private OtherTransactionalDetailsDao otherTransactionalDetailsDao;
	
	@Autowired
	private PropertyRoomTypeMappingDao propertyRoomTypeDao;

	public void saveTransactionDetails(TransactionDetailsDTO transaction)
			throws ISellDBException {
		try {
			String otaName = transaction.getOtaName().toLowerCase();
			otaName = otaName.trim();
			if (otaName.equalsIgnoreCase("Goibibo")) {
				logger.debug("Reached Expedia");
			}
			PropertyDetailsDTO propertyDetailsDTO = transaction
					.getPropertyDetailsDto();
			PropertyOnlineTravelAgentConnectionMapping onlineTravelAgentByExtractDetailsExtract = onlineTravelAgentByExtractDetailsDao
					.findPropertyOnlineTravelAgentByPropertyAndOta(
							propertyDetailsDTO, otaName);
			if (onlineTravelAgentByExtractDetailsExtract == null) {
				logger.error("Online Travel Agent Details  missing in database for: "
						+ otaName);
				throw new ISellDBException(1, "Missing OTA Configuration for :"
						+ otaName);
			}
			OnlineTravelAgentDetails onlineTravelAgentDetails = onlineTravelAgentByExtractDetailsExtract
					.getOtaDetails();

			int lengthOfStay = (int) ((transaction.getDepartureDate().getTime() - transaction
					.getArrivalDate().getTime()) / (1000 * 60 * 60 * 24));

			String bookingStatusName = transaction.getBookingStatus();
			if(bookingStatusName == null || bookingStatusName.length() <= 0)
			{
				bookingStatusName = "No Show";
			}
			BookingStatusOta bookingStatus = bookingStatusDao
					.findBookingStatusByName(bookingStatusName,
							onlineTravelAgentDetails);
			if (bookingStatus == null) {
				logger.error("Booking Status: "
						+ bookingStatusName
						+ " information is missing for OTA: " + otaName
						+ " in database..");
				throw new ISellDBException(2, "Booking Status: "
						+ bookingStatusName
						+ " information is missing for OTA: " + otaName
						+ " in database..");
			}
			Currency currency = currencyDao.findCurrencyByName(transaction
					.getTransactionCurrency());

			// TODO Property UID will give the details
			PropertyDetails propertyDetails = onlineTravelAgentByExtractDetailsExtract
					.getPropertyDetails();
			if (propertyDetails == null) {
				logger.error("Property configuration is missing for property: "
						+ propertyDetailsDTO.getName());
				throw new ISellDBException(3,
						"Property configuration is missing for property: "
								+ propertyDetailsDTO.getName());
			}
			if (currency == null) {
				currency = propertyDetails.getHotelCurrency();
				logger.warn("**********Currency is null in the extracts so assigning property currency...");
			}
			String bookingNumber = transaction.getBookingRefID();
			if(bookingNumber == null || bookingNumber.trim().length() <= 0)
			{
				bookingNumber = transaction.getRefID();
			}
			
			TransactionalDetails transactionalDetails = dao
					.findTransactionalDetailsByBookingReference(
							bookingNumber, propertyDetails,
							onlineTravelAgentByExtractDetailsExtract.getOtaDetails());
			if (transactionalDetails == null) {
				transactionalDetails = new TransactionalDetails(
						propertyDetails,
						onlineTravelAgentByExtractDetailsExtract
						.getOtaDetails(), transaction.getCaptureDate(),
						transaction.getArrivalDate(),
						transaction.getDepartureDate(), lengthOfStay,
						bookingStatus.getBookingStatusMaster(), currency, transaction.getTotalAmount(),
						transaction.getNumGuests(),
						bookingNumber, transaction.getNumOfRooms());
			} else {
				transactionalDetails.setOtaUpdateDate(transaction
						.getCaptureDate());
				transactionalDetails.setArrivalDate(transaction
						.getArrivalDate());
				transactionalDetails.setDepartureDate(transaction
						.getDepartureDate());
				transactionalDetails.setLengthOfStay(lengthOfStay);
				transactionalDetails.setBookingStatus(bookingStatus.getBookingStatusMaster());
				transactionalDetails.setTransactionCurrency(currency);
				transactionalDetails.setTotalAmount(transaction
						.getTotalAmount());
				transactionalDetails.setNumGuests(transaction.getNumGuests());
			}
			transactionalDetails.setModificationDate(new Date());
			MyJson jsonObject = new MyJson();

			JsonObject additionalDetailsJSON = transaction
					.getAdditionalDetails();
			if (additionalDetailsJSON != null) {
				jsonObject.setStringProp(additionalDetailsJSON.toString());
				transactionalDetails.setAdditionalDetails(jsonObject);
			}
			dao.saveTransactionDetails(transactionalDetails);
			
			List<TransactionalOtherDetailsDTO> transactionalAdditionalInformationDTOList
							= transaction.getTransactionalOtherInformationDTOList();
			
			for(TransactionalOtherDetailsDTO transactionalOtherDetailsDTO : transactionalAdditionalInformationDTOList)
			{
				String propertyRoomTypeName = transactionalOtherDetailsDTO.getRoomType();
				PropertyRoomTypeMapping propertyRoomTypeMapping = propertyRoomTypeDao.findRoomTypeByNameForProperty(propertyRoomTypeName, propertyDetails);
				MyJson jsonRoomRevenueObject = new MyJson();

				if(propertyRoomTypeMapping == null)
				{
					logger.error("Hotel Room Type to Master Room type is missing for hotel room type: " + propertyRoomTypeName);
					throw new ISellDBException(-1,
							"Hotel Room Type to Master Room type is missing for hotel room type: " + propertyRoomTypeName);
				}
				JsonObject revenueByRoomTypeJson = transaction
						.getAdditionalDetails();
				
				jsonRoomRevenueObject.setStringProp(revenueByRoomTypeJson.toString());
				OtherTransactionalDetails otherTranscationalDetails = 
						new OtherTransactionalDetails(transactionalDetails, propertyRoomTypeMapping, transactionalOtherDetailsDTO.getRoomRevenue(), jsonRoomRevenueObject, null);
				
				otherTransactionalDetailsDao.saveOtherTransactionDetails(otherTranscationalDetails);
			}	
			
		} catch (HibernateException e) {
			logger.error("Exception in method saveTransactionDetails: "
					+ e.getMessage());
			throw new ISellDBException(-1,
					"Exception in method saveTransactionDetails: "
							+ e.getMessage());
		}
		catch(Exception e)
		{
			logger.error("Exception is:" + e.getMessage());
		}
	}

	public List<TransactionDetailsDTO> lengthOfStay() throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TransactionDetailsDTO> findTransactionalDetailsByProperty(
			String propertyName) throws ISellDBException {

		List<TransactionalDetails> transDetailsList = dao
				.findTransactionalDetailsByProperty(propertyName);
		List<TransactionDetailsDTO> transDetailsDTOList = new ArrayList<TransactionDetailsDTO>();
		for (TransactionalDetails transDetails : transDetailsList) {
			PropertyDetails propDetails = transDetails.getPropertyDetails();
			String propertyChainName = "";
			HotelChainDetails propChain = propDetails.getHotelChainDetails();
			if (propChain != null) {
				propertyChainName = propChain.getName();
			}
			CityDTO cityDto = new CityDTO(propDetails.getCity().getName(),
					propDetails.getCity().getState().getName(), propDetails
					.getCity().getState().getCountry().getName());
			PropertyDetailsDTO propDetailsDTO = new PropertyDetailsDTO(
					propDetails.getName(), propertyChainName,
					propDetails.getAddress(), cityDto,
					propDetails.getPinCode(), propDetails.getCapcity(),
					propDetails.getPropertyStatus().getName(), propDetails
					.getHotelCurrency().getName(), new Date(),
					propDetails.getLatitude(), propDetails.getLongitude());
			TransactionDetailsDTO transactionDetailsDTO = new TransactionDetailsDTO(
					propDetailsDTO, transDetails.getBookingRefID(),
					transDetails.getOtaDetails().getName(),
					transDetails.getOtaUpdateDate(),
					transDetails.getArrivalDate(),
					transDetails.getDepartureDate(), transDetails
					.getBookingStatus().getName(), transDetails
					.getTransactionCurrency().getName(),
					transDetails.getTotalAmount(), transDetails.getNumGuests(),transDetails.getNumRooms());
			Gson gson = new Gson();
			Type typeOfList = new TypeToken<List<AdditionalInformationDTO>>() {
			}.getType();

			String json = gson.toJson(transDetails.getAdditionalDetails()
					.getStringProp(), typeOfList);
			JsonElement jelem = gson.fromJson(json, JsonElement.class);
			JsonObject jobj = jelem.getAsJsonObject();
			transactionDetailsDTO.setAdditionalDetails(jobj);
			transDetailsDTOList.add(transactionDetailsDTO);
		}
		return transDetailsDTOList;
	}

	public List<TransactionDetailsDTO> findTransactionalDetailsByCountry(
			String country) throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteTransactionalDetails(String bookingRefNo, int propertyID,
			int channelID) throws ISellDBException {
		// TODO Auto-generated method stub

	}

	public List<TransactionDetailsDTO> findAllTransactionalDetails()
			throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void saveTransactionDetailsList(
			ArrayList<TransactionDetailsDTO> transactionDetailsDTOList)
					throws ISellDBException {

		PropertyDetailsDTO propertyDetailsDto = null;
		try {
			if (transactionDetailsDTOList != null
					&& transactionDetailsDTOList.size() > 0
					&& transactionDetailsDTOList.get(0) != null) {

				propertyDetailsDto = transactionDetailsDTOList.get(0)
						.getPropertyDetailsDto();
				PropertyDetails propertyDetails = propertyDetailsDao
						.findPropertyDetails(propertyDetailsDto);
				if (propertyDetails != null) {
					List<PropertyOnlineTravelAgentConnectionMapping> propertyOtaList = onlineTravelAgentByExtractDetailsDao
							.findPropertyOnlineTravelAgentByPropertyAndOta(propertyDetailsDto);
					if (propertyOtaList != null || propertyOtaList.size() > 0) {
						Map<String, PropertyOnlineTravelAgentConnectionMapping> propertyOtaMappingMap = new HashMap<String, PropertyOnlineTravelAgentConnectionMapping>();
						for (PropertyOnlineTravelAgentConnectionMapping propertyOtaMap : propertyOtaList) {
							propertyOtaMappingMap.put(
									propertyOtaMap.getPropertyOtaName(),
									propertyOtaMap);
						}

						Map<String, Map<String, BookingStatusOta>> otaMapByPropertyOtaStatus = new HashMap<String, Map<String, BookingStatusOta>>();
						for (String propertyOtaName : propertyOtaMappingMap
								.keySet()) {

							OnlineTravelAgentDetails otaDetails = propertyOtaMappingMap
									.get(propertyOtaName).getOtaDetails();
							List<BookingStatusOta> bookingStatusOtaList = bookingStatusDao
									.findAllBookingStatusesForOTA(otaDetails);
							Map<String, BookingStatusOta> mapByBookingStatus = new HashMap<String, BookingStatusOta>();
							for (BookingStatusOta bookingStatus : bookingStatusOtaList) {
								mapByBookingStatus.put(bookingStatus.getName(),
										bookingStatus);
							}
							otaMapByPropertyOtaStatus.put(propertyOtaName,
									mapByBookingStatus);
						}
						if (otaMapByPropertyOtaStatus != null
								|| otaMapByPropertyOtaStatus.size() > 0) {
							
							int cnt =0;
							
							
							for (TransactionDetailsDTO transaction : transactionDetailsDTOList) {
								cnt++;
								
								
								if( cnt == 115)
								{
									logger.debug("Debugging remove if condition later.");
								}

							

								int lengthOfStay = (int) ((transaction
										.getDepartureDate().getTime() - transaction
										.getArrivalDate().getTime()) / (1000 * 60 * 60 * 24));
								
								String otaName = transaction.getOtaName().trim();
/*								if(otaName.equals("agodaycs"))
								{
									logger.debug("OTA is" + otaName);
								}
*/								String bookingNumber = transaction.getBookingRefID();
								if(bookingNumber == null || bookingNumber.trim().length() <= 0)
								{
									bookingNumber = transaction.getRefID();
								}
								logger.debug("bookingNumber: " + bookingNumber + " otaName: " + otaName) ;
								
								if(bookingNumber == null || bookingNumber.trim().length() <= 0 )
								{
									logger.warn("Booking Reference ID is null so ignoring the transaction: " + bookingNumber);
									continue;
								}
								
								PropertyOnlineTravelAgentConnectionMapping propertyOtaMapping =  propertyOtaMappingMap.get(otaName);
								if( propertyOtaMapping == null)
								{
									logger.error( "Property Ota mapping not defined for ota: " + otaName + " and property : " +  propertyDetails.getName());
									throw new ISellDBException(1, "Property Ota mapping not defined for ota: " + otaName + " and property : " +  propertyDetails.getName());
								}
								OnlineTravelAgentDetails otaDetails = propertyOtaMapping.getOtaDetails();
								TransactionalDetails transactionalDetails = dao
										.findTransactionalDetailsByBookingReference(
												bookingNumber, propertyDetails, otaDetails);
								
								Map<String, BookingStatusOta> bookingStatusMapByOta = otaMapByPropertyOtaStatus.get(otaName);

								String bookingStat = transaction.getBookingStatus();
								if( bookingStat == null || bookingStat.length() <= 0)
								{
									transaction.setBookingStatus("No Show");
								}
								BookingStatusOta bookingStatusOta = bookingStatusMapByOta.get(transaction.getBookingStatus());
								bookingStatusOta.getId();
								if( bookingStatusOta == null )
								{
									logger.error("Booking status: " +transaction.getBookingStatus() + " Not defined for ota: " + otaName);
									throw new ISellDBException(1, "Booking status: " +transaction.getBookingStatus() + " Not defined for ota: " + otaName);
								}
								if (transactionalDetails == null) {	
									Currency currency = currencyDao
											.findCurrencyByName(transaction
													.getTransactionCurrency());
									currency.getId();
									transactionalDetails = new TransactionalDetails(
											propertyDetails,
											otaDetails, transaction.getCaptureDate(),
											transaction.getArrivalDate(),
											transaction.getDepartureDate(), lengthOfStay,
											bookingStatusOta.getBookingStatusMaster(), currency, transaction.getTotalAmount(),
											transaction.getNumGuests(),
											bookingNumber,transaction.getNumOfRooms());									
								} else {
									transactionalDetails.setOtaUpdateDate(transaction
											.getCaptureDate());
									transactionalDetails.setArrivalDate(transaction
											.getArrivalDate());
									transactionalDetails.setDepartureDate(transaction
											.getDepartureDate());
									transactionalDetails.setLengthOfStay(lengthOfStay);
									transactionalDetails.setBookingStatus(bookingStatusOta.getBookingStatusMaster());
//									transactionalDetails.setTransactionCurrency(currency);
									transactionalDetails.setTotalAmount(transaction
											.getTotalAmount());
									transactionalDetails.setNumRooms(transaction.getNumOfRooms());
									transactionalDetails.setNumGuests(transaction.getNumGuests());
									transactionalDetails.setOtaDetails(otaDetails);
								}
								transactionalDetails.setModificationDate(new Date());
								MyJson jsonObject = new MyJson();

								JsonObject additionalDetailsJSON = transaction
										.getAdditionalDetails();
								if (additionalDetailsJSON != null) {
									jsonObject.setStringProp(additionalDetailsJSON.toString());
									transactionalDetails.setAdditionalDetails(jsonObject);
								}
//								logger.debug("Transaction details are: " + transactionalDetails.toString());
								dao.saveTransactionDetails(transactionalDetails);
								
								//APARNA DO THIS ON PRIORITY ADD THE CLASS TO STORE ADDITIONAL INFORMATION
								List<TransactionalOtherDetailsDTO> transactionalAdditionalInformationDTOList
								= transaction.getTransactionalOtherInformationDTOList();
								if(transactionalAdditionalInformationDTOList != null)
								{
									for(TransactionalOtherDetailsDTO transactionalOtherDetailsDTO : transactionalAdditionalInformationDTOList)
									{
										
										String propertyRoomTypeName = transactionalOtherDetailsDTO.getRoomType();
										PropertyRoomTypeMapping propertyRoomTypeMapping = propertyRoomTypeDao.findRoomTypeByNameForProperty(propertyRoomTypeName, propertyDetails);
										
										if(propertyRoomTypeMapping == null)
										{
											logger.error("Hotel Room Type to Master Room type is missing for hotel room type: " + propertyRoomTypeName);
											throw new ISellDBException(-1,
													"Hotel Room Type to Master Room type is missing for hotel room type: " + propertyRoomTypeName);
										}
										propertyRoomTypeMapping.getId();
										
										//TODO if room  type property mapping is not present just highlight error
										MyJson jsonRoomRevenueObject = new MyJson();
					
										JsonObject revenueByRoomTypeJson = transaction
												.getAdditionalDetails();
										
										jsonRoomRevenueObject.setStringProp(revenueByRoomTypeJson.toString());
										OtherTransactionalDetails otherTranscationalDetails = otherTransactionalDetailsDao.findOtherTransactionalDetailsForTransactionAndRoomType(transactionalDetails, propertyRoomTypeMapping);
										if( otherTranscationalDetails == null)
										{
											otherTranscationalDetails = new OtherTransactionalDetails(transactionalDetails, propertyRoomTypeMapping, transactionalOtherDetailsDTO.getRoomRevenue(), jsonRoomRevenueObject, null);
										}
										otherTranscationalDetails.setRevenueByRoomType(jsonRoomRevenueObject);
										otherTransactionalDetailsDao.saveOtherTransactionDetails(otherTranscationalDetails);
									}
								}
								if(cnt % 100 == 0) {
									try
									{
//										dao.commit();
//										dao.evictAll();
										
										dao.flush();
										dao.clear();
										otherTransactionalDetailsDao.flush();
										otherTransactionalDetailsDao.clear();
										
										propertyDetails = propertyDetailsDao
												.findPropertyDetails(propertyDetailsDto);
										propertyDetails.getId();
									}
									catch(HibernateException e)
									{
										logger.error("HibernateException while clearing the Transactional  details data: " + e.getMessage() + "For Transaction : " + transaction.toString());
//										throw new ISellDBException(-1, e.getMessage());
									}
									catch(Exception e){
										logger.error("Exception  while storing the Transactional  details data: " + e.getMessage());
										throw new ISellDBException(-1, e.getMessage());
									}
									/*propertyDetailsDao.clear();
									
									 * propertyDetailsDao.flush();
									 */
									/*
									otherTransactionalDetailsDao.flush();
									otherTransactionalDetailsDao.clear();
									propertyDetailsDao.flush();
									propertyDetailsDao.clear();*/
							    }
							}
						} else {
							logger.error("Mapping for Booking Status and OTA is not defined. ");
							throw new ISellDBException(1,
									"Mapping for Booking Status and OTA is not defined.");
						}

					} else {
						logger.error("Mapping for Property and OTA is not defined for property : "
								+ propertyDetailsDto.toString());
						throw new ISellDBException(1,
								"Mapping for Property and OTA is not defined for property : "
										+ propertyDetailsDto.toString());
					}

				} else {
					logger.error("Property Configuration does not exists in database : "
							+ propertyDetailsDto.toString());
					throw new ISellDBException(1,
							"Property Configuration does not exists in database : "
									+ propertyDetailsDto.toString());
				}

			}

			else {
				logger.error("Method called to save transcational details with empty data. There is no data sent to save for property: ");
				throw new ISellDBException(
						1,
						"Method called to save transcational details with empty data. There is no data sent to save  for property: ");
			}

		} catch (HibernateException e) {
			logger.error("HibernateException while storing the Transactional  details data: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}catch(Exception e){
			logger.error("Exception  while storing the Transactional  details data: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
