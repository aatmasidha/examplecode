package com.ai.sample.common.dto.ui;

import java.util.ArrayList;
import java.util.List;

public class RateShoppingPropertyDetailsResponseJsonDTO {

	List<RateShoppingPropertyDetailsJsonDTO> rateShoppingPropertyDetailsJsonDTO 
		= new ArrayList<RateShoppingPropertyDetailsJsonDTO>();
	
	ExecutionStatusDTO executionStatusDTO = new ExecutionStatusDTO();

	
	public RateShoppingPropertyDetailsResponseJsonDTO() {
		super();
	}

	public RateShoppingPropertyDetailsResponseJsonDTO(
			List<RateShoppingPropertyDetailsJsonDTO> rateShoppingPropertyDetailsJsonDTO,
			ExecutionStatusDTO executionStatusDTO) {
		super();
		this.rateShoppingPropertyDetailsJsonDTO = rateShoppingPropertyDetailsJsonDTO;
		this.executionStatusDTO = executionStatusDTO;
	}

	public List<RateShoppingPropertyDetailsJsonDTO> getRateShoppingPropertyDetailsJsonDTO() {
		return rateShoppingPropertyDetailsJsonDTO;
	}

	public void setRateShoppingPropertyDetailsJsonDTO(
			List<RateShoppingPropertyDetailsJsonDTO> rateShoppingPropertyDetailsJsonDTO) {
		this.rateShoppingPropertyDetailsJsonDTO = rateShoppingPropertyDetailsJsonDTO;
	}

	public ExecutionStatusDTO getExecutionStatusDTO() {
		return executionStatusDTO;
	}

	public void setExecutionStatusDTO(ExecutionStatusDTO executionStatusDTO) {
		this.executionStatusDTO = executionStatusDTO;
	}

	@Override
	public String toString() {
		return "RateShoppingPropertyDetailsResponseJsonDTO [rateShoppingPropertyDetailsJsonDTO="
				+ rateShoppingPropertyDetailsJsonDTO
				+ ", executionStatusDTO="
				+ executionStatusDTO + "]";
	}
}
