package com.ai.sample.db.dao.property.transaction;

import java.util.List;

import org.postgresql.util.PSQLException;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.transaction.TransactionalDetails;

public interface TransactionalDetailsDao {

	void saveTransactionDetails(TransactionalDetails transaction) throws ISellDBException ;
	
	List<TransactionalDetails> findAllTransactionalDetails() throws ISellDBException;
	
	List<TransactionalDetails> findTransactionalDetailsByProperty(String propertyName) throws ISellDBException;
	
	List<TransactionalDetails> findTransactionalDetailsByCountry(String country) throws ISellDBException;
	
	void deleteTransactionalDetails(String bookingRefNo, int propertyID, int channelID) throws ISellDBException;
	
	TransactionalDetails findTransactionalDetailsByBookingReference(String bookingRefID, PropertyDetails propertyDetails, OnlineTravelAgentDetails otaDetails) throws ISellDBException;
	
	void deleteTransactionsForProperty(PropertyDetails propertyDetails) throws ISellDBException;

	void flush()throws PSQLException;

	void clear() throws PSQLException;

//	void evictAll();

	void commit() throws PSQLException;
}
