package com.ai.sample.db.dao.configuration;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.configuration.BookingStatusMaster;

public interface BookingStatusMasterDao {

	void saveBookingStatus(BookingStatusMaster bookingStatus) throws ISellDBException ;
	
	List<BookingStatusMaster> findAllBookingStatuses() throws ISellDBException;
	
	BookingStatusMaster findBookingStatusByName(String bookingStatusName) throws ISellDBException;
	
	void deleteBookingStausByName(String bookingStatusName) throws ISellDBException;
}
