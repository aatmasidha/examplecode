package com.ai.sample.common.dto;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.codehaus.jettison.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class TransactionDetailsDTO {
	private int id;
	private PropertyDetailsDTO propertyDetailsDto;
/*	private CityDTO cityName;*/
	private String bookingRefID;
	private String refID;

	private String otaName;
	private Date captureDate;
	private Date arrivalDate;
	private Date departureDate;
	private Date modificationDate;
	private int lengthOfStay;
	private String bookingStatus;
	private String transactionCurrency;
	private float totalAmount;
	private String arrivalCountry;
	private String arrivalCity;
	private int numGuests;
	private String address;
	private int numOfRooms;
	
	private JsonObject additionalDetails;	
	public List<AdditionalInformationDTO> additionalInformationDTO;
	public List<TransactionalOtherDetailsDTO> transactionalOtherInformationDTOList;

	public TransactionDetailsDTO() {
		super();
	}

	public TransactionDetailsDTO(PropertyDetailsDTO propertyDetailsDto, String bookingRefID,
			String otaName, Date captureDate, Date arrivalDate,
			Date departureDate, String bookingStatus,
			String transactionCurrency, float totalAmount, int numGuests, int numOfRooms) {
		super();
		this.propertyDetailsDto = propertyDetailsDto;
		this.bookingRefID = bookingRefID;
		this.otaName = otaName;
		this.captureDate = captureDate;
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
		this.bookingStatus = bookingStatus;
		this.transactionCurrency = transactionCurrency;
		this.totalAmount = totalAmount;
		this.numGuests = numGuests;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	public PropertyDetailsDTO getPropertyDetailsDto() {
		return propertyDetailsDto;
	}

	public void setPropertyDetailsDto(PropertyDetailsDTO propertyDetailsDto) {
		this.propertyDetailsDto = propertyDetailsDto;
	}

	public String getBookingRefID() {
		return bookingRefID;
	}

	public void setBookingRefID(String bookingRefID) {
		this.bookingRefID = bookingRefID;
	}


	public String getOtaName() {
		return otaName;
	}


	public void setOtaName(String otaName) {
		this.otaName = otaName;
	}


	public Date getCaptureDate() {
		return captureDate;
	}


	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
	}


	public Date getArrivalDate() {
		return arrivalDate;
	}


	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}


	public Date getDepartureDate() {
		return departureDate;
	}


	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}


	public Date getModificationDate() {
		return modificationDate;
	}


	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}


	public int getLengthOfStay() {
		return lengthOfStay;
	}


	public void setLengthOfStay(int lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
	}


	public String getBookingStatus() {
		return bookingStatus;
	}


	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}


	public String getTransactionCurrency() {
		return transactionCurrency;
	}


	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}


	public float getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}


	public String getArrivalCountry() {
		return arrivalCountry;
	}


	public void setArrivalCountry(String arrivalCountry) {
		this.arrivalCountry = arrivalCountry;
	}


	public String getArrivalCity() {
		return arrivalCity;
	}


	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}


	public int getNumGuests() {
		return numGuests;
	}


	public void setNumGuests(int numGuests) {
		this.numGuests = numGuests;
	}


	public JsonObject getAdditionalDetails() {
		return additionalDetails;
	}


	public void setAdditionalDetails(JsonObject additionalDetails) {
		this.additionalDetails = additionalDetails;
	}

	public List<AdditionalInformationDTO> getAdditionalInformationDTO() {
		return additionalInformationDTO;
	}

	public void setAdditionalInformationDTO(
			List<AdditionalInformationDTO> additionalInformationDTO) throws JSONException {
		this.additionalInformationDTO = additionalInformationDTO;
//		 Gson gson = new Gson();
	   //  String json = gson.toJson(additionalInformationDTO);
		/* JSONObject obj = new JSONObject();
		 for(int i = 0; i < additionalInformationDTO.size(); i++)
		 {
			 obj.put("key", additionalInformationDTO.get(i).getKey());
			 obj.put("value", additionalInformationDTO.get(i).getValue());
		 }*/
		
		Type listType = new TypeToken<List<AdditionalInformationDTO>>() {}.getType();
		Gson gson = new Gson();
		 String json = gson.toJson(additionalInformationDTO, listType);
//		 List<AdditionalInformationDTO> parsedList = gson.fromJson(json, listType);
		 
//		 this.additionalDetails = 
//		 this.additionalDetails = new JsonParser().parse(json);
		 JsonElement element = gson.fromJson (json, JsonArray.class);
//		 JsonArray jsonObjArray = element.getAsJsonArray();
		 JsonObject jo = new JsonObject();
		 
		 // populate the array
		 jo.add("Object",element);
//		 this.additionalDetails =  jsonObjArray.getAsJsonObject();
		 this.additionalDetails =  jo;
	}



	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public List<TransactionalOtherDetailsDTO> getTransactionalOtherInformationDTOList() {
		return transactionalOtherInformationDTOList;
	}

	public void setTransactionalOtherInformationDTOList(
			List<TransactionalOtherDetailsDTO> transactionalOtherInformationDTOList) {
		this.transactionalOtherInformationDTOList = transactionalOtherInformationDTOList;
	}

	public int getNumOfRooms() {
		return numOfRooms;
	}

	public void setNumOfRooms(int numOfRooms) {
		this.numOfRooms = numOfRooms;
	}

	public String getRefID() {
		return refID;
	}

	public void setRefID(String refID) {
		this.refID = refID;
	}

	@Override
	public String toString() {
		return "TransactionDetailsDTO [id=" + id + ", propertyDetailsDto="
				+ propertyDetailsDto + ", bookingRefID=" + bookingRefID
				+ ", refID=" + refID + ", otaName=" + otaName
				+ ", captureDate=" + captureDate + ", arrivalDate="
				+ arrivalDate + ", departureDate=" + departureDate
				+ ", modificationDate=" + modificationDate + ", lengthOfStay="
				+ lengthOfStay + ", bookingStatus=" + bookingStatus
				+ ", transactionCurrency=" + transactionCurrency
				+ ", totalAmount=" + totalAmount + ", arrivalCountry="
				+ arrivalCountry + ", arrivalCity=" + arrivalCity
				+ ", numGuests=" + numGuests + ", address=" + address
				+ ", numOfRooms=" + numOfRooms + ", additionalDetails="
				+ additionalDetails + ", additionalInformationDTO="
				+ additionalInformationDTO
				+ ", transactionalOtherInformationDTOList="
				+ transactionalOtherInformationDTOList + "]";
	}
	
	
}
