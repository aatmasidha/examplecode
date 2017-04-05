package com.ai.sample.common.dto;

import java.util.List;

import com.google.gson.JsonObject;

public class TransactionalOtherDetailsDTO {
	private int id;
	private String roomType;
	private float roomRevenue;
	private JsonObject revenueByRoomType;
	public List<AdditionalInformationDTO> additionalInformationDTO;
	int transactionID;

	public TransactionalOtherDetailsDTO() {
		super();
	}

	public TransactionalOtherDetailsDTO(String roomType,
			JsonObject revenueByRoomType, float roomRevenue,
			List<AdditionalInformationDTO> additionalInformationDTO,
			int transactionID) {
		super();
		this.roomType = roomType;
		this.roomRevenue = roomRevenue;
		this.revenueByRoomType = revenueByRoomType;
		this.additionalInformationDTO = additionalInformationDTO;
		this.transactionID = transactionID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomType() {
		return roomType;
	}
	
	public float getRoomRevenue() {
		return roomRevenue;
	}

	public void setRoomRevenue(float roomRevenue) {
		this.roomRevenue = roomRevenue;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public JsonObject getRevenueByRoomType() {
		return revenueByRoomType;
	}

	public void setRevenueByRoomType(JsonObject revenueByRoomType) {
		this.revenueByRoomType = revenueByRoomType;
	}

	public List<AdditionalInformationDTO> getAdditionalInformationDTO() {
		return additionalInformationDTO;
	}

	public void setAdditionalInformationDTO(
			List<AdditionalInformationDTO> additionalInformationDTO) {
		this.additionalInformationDTO = additionalInformationDTO;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	@Override
	public String toString() {
		return "TransactionalOtherDetailsDTO [id=" + id + ", roomType="
				+ roomType + ", roomRevenue=" + roomRevenue
				+ ", revenueByRoomType=" + revenueByRoomType
				+ ", additionalInformationDTO=" + additionalInformationDTO
				+ ", transactionID=" + transactionID + "]";
	}
}
