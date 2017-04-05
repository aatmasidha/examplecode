package com.ai.sample.db.dao.property.configuration;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.HotelChainDetails;

public interface HotelChainDetailsDao {

	void saveHotelChainDetails(HotelChainDetails hotelChain) throws ISellDBException;
	
	List<HotelChainDetails> findAllHotelChains() throws ISellDBException;
	
	HotelChainDetails findHotelChainByName(String name) throws ISellDBException;
	
	void deleteHotelChainByName(String name) throws ISellDBException;
}
