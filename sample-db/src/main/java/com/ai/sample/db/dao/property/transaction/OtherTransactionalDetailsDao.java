package com.ai.sample.db.dao.property.transaction;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;
import com.ai.sample.db.model.property.transaction.OtherTransactionalDetails;
import com.ai.sample.db.model.property.transaction.TransactionalDetails;

public interface OtherTransactionalDetailsDao {

	void saveOtherTransactionDetails(OtherTransactionalDetails transaction) throws ISellDBException ;
	
	OtherTransactionalDetails findOtherTransactionalDetailsForTransaction(TransactionalDetails transactionDetails) throws ISellDBException;

	void flush() throws ISellDBException;

	void clear()throws ISellDBException;

	OtherTransactionalDetails findOtherTransactionalDetailsForTransactionAndRoomType(
			TransactionalDetails transactionDetails, PropertyRoomTypeMapping propertyRoomTypeMapping)
			throws ISellDBException;
	
	public void deleteRoomTypeTransactionDetailsForProperty(PropertyDetails propertyDetails)
			throws ISellDBException;

}
