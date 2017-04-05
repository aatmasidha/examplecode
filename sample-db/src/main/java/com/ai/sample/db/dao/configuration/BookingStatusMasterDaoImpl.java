package com.ai.sample.db.dao.configuration;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.configuration.BookingStatusMaster;

@Repository("bookingStatusMasterDao")
public class BookingStatusMasterDaoImpl extends AbstractDao implements BookingStatusMasterDao{

	static final Logger LOGGER = Logger.getLogger(BookingStatusMasterDaoImpl.class);

	@Override
	public void saveBookingStatus(BookingStatusMaster bookingStatus)
			throws ISellDBException {
		try
		{
			LOGGER.debug("Inside BookingStatusMasterDaoImpl::saveBookingStatus");
			saveOrUpdate(bookingStatus);
			LOGGER.debug("Exit BookingStatusMasterDaoImpl::saveBookingStatus");
		}
		catch( HibernateException | PSQLException e)
		{
			LOGGER.error("Exception in BookingStatusMasterDaoImpl::BookingStatusMasterDaoImpl: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingStatusMaster> findAllBookingStatuses()
			throws ISellDBException {
		try
		{
			LOGGER.debug("Inside BookingStatusMasterDaoImpl::findAllBookingStatuses");
			final Criteria criteria = getSession().createCriteria(BookingStatusMaster.class);
			LOGGER.debug("Exit BookingStatusMasterDaoImpl::findAllBookingStatuses");
			return (List<BookingStatusMaster>) criteria.list();
		}
		catch( HibernateException | PSQLException e)
		{
			LOGGER.error("Exception in BookingStatusMasterDaoImpl::findAllBookingStatuses: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public BookingStatusMaster findBookingStatusByName(String bookingStatusName)
			throws ISellDBException {
		try
		{
			LOGGER.debug("Inside BookingStatusMasterDaoImpl::findBookingStatusByName");
			final Criteria criteria = getSession().createCriteria(BookingStatusMaster.class);
			criteria.add(Restrictions.eq("name",bookingStatusName));
			BookingStatusMaster bookingStatus = (BookingStatusMaster) criteria.uniqueResult();
			LOGGER.debug("Exit BookingStatusMasterDaoImpl::findBookingStatusByName");
			return  bookingStatus;
		}
		catch( HibernateException | PSQLException e)
		{
			LOGGER.error("Exception in BookingStatusMasterDaoImpl::findBookingStatusByName: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteBookingStausByName(String bookingStatusName)
			throws ISellDBException {
		try
		{
			LOGGER.debug("Inside BookingStatusMasterDaoImpl::deleteBookingStausByName");
			Query query = getSession().createSQLQuery("delete from BookingStatusMaster where name = :name");
			query.setString("name", bookingStatusName);
			query.executeUpdate();
			LOGGER.debug("Exit BookingStatusMasterDaoImpl::deleteBookingStausByName");
		}
		catch( HibernateException | PSQLException e)
		{
			LOGGER.error("Exception in BookingStatusMasterDaoImpl::deleteBookingStausByName: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
