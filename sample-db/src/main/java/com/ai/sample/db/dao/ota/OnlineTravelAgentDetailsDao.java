package com.ai.sample.db.dao.ota;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;

public interface OnlineTravelAgentDetailsDao {

	void saveOnlineTravelAgentDetails(OnlineTravelAgentDetails onlineAgentName) throws ISellDBException;
	
	List<OnlineTravelAgentDetails> findAllOnlineTravelAgent() throws ISellDBException;
	
	OnlineTravelAgentDetails findOnlineTravelAngentByName(String onlineAgentName) throws ISellDBException;
	
	void deleteOnlineTravelAgentByName(String onlineAgentName) throws ISellDBException;
}
