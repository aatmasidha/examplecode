package com.ai.sample.db.service.property.transaction;

import java.util.ArrayList;
import java.util.List;

import com.ai.sample.common.dto.TransactionDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;

public interface TransactionalDetailsService {

	void saveTransactionDetails(TransactionDetailsDTO transaction) throws ISellDBException ;
	
	List<TransactionDetailsDTO> findAllTransactionalDetails() throws ISellDBException;
	
	List<TransactionDetailsDTO> findTransactionalDetailsByProperty(String propertyName) throws ISellDBException;
	
	List<TransactionDetailsDTO> findTransactionalDetailsByCountry(String country) throws ISellDBException;
	
	void deleteTransactionalDetails(String bookingRefNo, int propertyID, int channelID) throws ISellDBException;

	void saveTransactionDetailsList(
			ArrayList<TransactionDetailsDTO> transactionDetailsDTOList) throws ISellDBException;
}
