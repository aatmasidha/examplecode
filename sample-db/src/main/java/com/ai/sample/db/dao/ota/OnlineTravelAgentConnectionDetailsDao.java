package com.ai.sample.db.dao.ota;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.ota.OnlineTravelAgentConnectionDetails;

public interface OnlineTravelAgentConnectionDetailsDao {

	void saveOnlineTravelAgent(OnlineTravelAgentConnectionDetails onlineAgentConnectionDetails) throws ISellDBException;
	
	List<OnlineTravelAgentConnectionDetails> findAllOnlineTravelAngent() throws ISellDBException;
	
	OnlineTravelAgentConnectionDetails findOnlineTravelAngentByName(String onlineAgentConnectionDetails, String otaName) throws ISellDBException;
	
	void deleteOnlineTravelAgentByName(String onlineAgentConnectionDetails, String otaName) throws ISellDBException;
}
