package com.ai.sample.db.dao.ota;

import java.util.List;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.model.ota.BookingStatusOta;
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;

public interface BookingStatusOtaDao {

	void saveBookingStatus(BookingStatusOta bookingStatus) throws ISellDBException ;
	
	List<BookingStatusOta> findAllBookingStatuses() throws ISellDBException;
	
	void deleteBookingStausByName(String bookingStatusName) throws ISellDBException;

	BookingStatusOta findBookingStatusByName(String bookingStatus,
			OnlineTravelAgentDetails onlineTravelAgentDetails) throws  ISellDBException;
	
	List<BookingStatusOta> findAllBookingStatusesForOTA(OnlineTravelAgentDetails onlineTravelAgentDetails) throws  ISellDBException;
}
