package com.ai.sample.db.dao.ota;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.configuration.BookingStatusMasterDao;
import com.ai.sample.db.model.configuration.BookingStatusMaster;
import com.ai.sample.db.model.ota.BookingStatusOta;
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;

@Repository("bookingStatusOtaDao")
public class BookingStatusOtaDaoImpl extends AbstractDao implements BookingStatusOtaDao{

	Logger logger = Logger.getLogger(BookingStatusOtaDaoImpl.class);

	@Autowired
	OnlineTravelAgentDetailsDao otaDetailsDao;

	@Autowired
	BookingStatusMasterDao bookingStatusMasterDao;

	@Override
	public void saveBookingStatus(BookingStatusOta bookingStatus)
			throws ISellDBException {
		try
		{
			saveOrUpdate(bookingStatus);
		}
		catch(PSQLException | HibernateException e)
		{
			logger.error("Exception in BookingStatusOtaDaoImpl::saveBookingStatus" + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingStatusOta> findAllBookingStatuses()
			throws ISellDBException {
		try
		{
			final Criteria criteria = getSession().createCriteria(BookingStatusOta.class);
			return (List<BookingStatusOta>) criteria.list();
		}
		catch(PSQLException | HibernateException e)
		{
			logger.error("Exception in BookingStatusOtaDaoImpl::findAllBookingStatuses" + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteBookingStausByName(String bookingStatusName)
			throws ISellDBException {
	}

	@Override
	public BookingStatusOta findBookingStatusByName(String bookingStatusName,
			OnlineTravelAgentDetails onlineTravelAgentDetails)
					throws ISellDBException {
		try
		{
			BookingStatusMaster bookingStatusMaster = bookingStatusMasterDao.findBookingStatusByName(bookingStatusName);
			Query query = getSession().createQuery("from BookingStatusOta where otaDetails = :otaDetails and bookingStatusMaster = :bookingStatusMaster");
			query.setParameter("otaDetails", onlineTravelAgentDetails);
			query.setParameter("bookingStatusMaster", bookingStatusMaster);
			logger.debug("******************* Status query is: " + query.getQueryString());
			List<BookingStatusOta> bookingStatusList = query.list();
			if( bookingStatusList != null && bookingStatusList.size() > 0)
			{	
				return  bookingStatusList.get(0);
			}
			else
			{
				return null;
			}
		}
		catch(PSQLException | HibernateException e)
		{
			logger.error("Exception in BookingStatusOtaDaoImpl::findBookingStatusByName" + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<BookingStatusOta> findAllBookingStatusesForOTA(
			OnlineTravelAgentDetails onlineTravelAgentDetails)
					throws ISellDBException {
		try
		{
			Query query = getSession().createQuery("from BookingStatusOta where otaDetails = :otaDetails ");
			query.setParameter("otaDetails", onlineTravelAgentDetails);
			//		query.setCacheable(true);
			logger.debug("******************* Status query is: " + query.getQueryString());
			List<BookingStatusOta> bookingStatusList = query.list();
			return  bookingStatusList;
		}
		catch(PSQLException | HibernateException e)
		{
			logger.error("Exception in BookingStatusOtaDaoImpl::findAllBookingStatusesForOTA" + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}

	}
}
