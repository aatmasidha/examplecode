package com.ai.sample.db.dao.configuration;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.configuration.RoomTypeMaster;

public interface RoomTypeMasterDao {
	void saveOrUpdateRoomType (RoomTypeMaster roomType) throws ISellDBException;
	RoomTypeMaster findRoomTypeByName(String roomType) throws ISellDBException;
	
	List<RoomTypeMaster> findAllMasterRoomTypes() throws ISellDBException;
	
	void deleteMasterRoomTypeByName(String roomType) throws ISellDBException;
}