package com.ai.sample.db.service.ota;

import java.util.List;

import com.ai.sample.common.dto.configuration.OnlineTravelAgentDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;

public interface OnlineTravelAgentDetailsService {

	public void saveOnlineTravelAgentDetails(OnlineTravelAgentDetailsDTO onlineTravelAgentDetailsDto) throws ISellDBException;

	public OnlineTravelAgentDetailsDTO findAllOnlineTravelAgentDetails(String onlineTravelAgentName) throws ISellDBException;

	List<OnlineTravelAgentDetailsDTO> findAllOnlineTravelAgentDetails() throws ISellDBException;
}
