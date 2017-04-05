package com.ai.sample.db.service.configuration;

import java.util.List;

import com.ai.sample.common.dto.configuration.OnlineTravelAgentDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;

public interface OnlineTravelAgentService {

	public void saveOnlineTravelAgent(OnlineTravelAgentDetailsDTO otaDetails) throws ISellDBException;
	
	public List<OnlineTravelAgentDetailsDTO> findAllOnlineTravelAgents() throws ISellDBException;
	
	public OnlineTravelAgentDetailsDTO findOnlineTravelAgentByName(String otaName) throws ISellDBException;
	
	public void deleteOnlineTravelAgentNameByName(String otaName) throws ISellDBException;
}
