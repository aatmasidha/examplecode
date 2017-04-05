package com.ai.sample.db.dao.configuration;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.configuration.PropertyStatusMaster;

public interface PropertyStatusMasterDao {

	void savePropertyStatus(PropertyStatusMaster propertyStatus) throws ISellDBException;
	
	List<PropertyStatusMaster> findAllPropertyStatus() throws ISellDBException;
	
	PropertyStatusMaster findPropertyStatusByName(String status) throws ISellDBException;
	
	void deletePropertyStatusByName(String status) throws ISellDBException;

	void flush()throws ISellDBException;

	void clear()throws ISellDBException;
}
