package com.ai.sample.db.dao.property.transaction;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.TransientObjectException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.model.ota.OnlineTravelAgentDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.transaction.TransactionalDetails;

@Repository("transactionalDetailsDao")
public class TransactionalDetailsDaoImpl extends AbstractDao implements
		TransactionalDetailsDao {

	static final Logger LOGGER = Logger
			.getLogger(TransactionalDetailsDaoImpl.class);

	@Autowired
	private PropertyDetailsDao propertyDetailsDao;
	
	public void saveTransactionDetails(TransactionalDetails transaction)
			throws ISellDBException {
		LOGGER.debug("Inside TransactionalDetailsDaoImpl::saveTransactionDetails");
		
		try
		{
			/*if(transaction.getId() == 0)
			{
				persist(transaction);
			}
			else
			{
				merge(transaction.getTransactionCurrency());
				merge(transaction.getPropertyDetails());
				merge(transaction.getBookingStatus());
				merge(transaction.getOtaDetails());
				merge(transaction);
				saveOrUpdate(transaction);
			}*/
			
			saveOrUpdate(transaction);
			
		}
		catch(TransientObjectException e)
		{
			LOGGER.error(e.getMessage());
		}
		catch(NonUniqueObjectException e)
		{
			LOGGER.error(e.getMessage());
			try {
				if( getSession().contains(transaction.getTransactionCurrency() ) )
				{
					LOGGER.info("TransactionalDetailsDaoImpl::Contains TransactionCurrency: " + transaction.getTransactionCurrency() );
					getSession().evict(transaction.getTransactionCurrency());
				}
			} catch (PSQLException e1) {
					LOGGER.info("TransactionalDetailsDaoImpl::Contains TransactionCurrency: " + transaction.getTransactionCurrency() );			}
//			getSession().merge(transaction.getTransactionCurrency());
			/*getSession().merge(transaction.getBookingStatus());
			getSession().merge(transaction.getPropertyDetails());
			getSession().merge(transaction.getPropertyDetails().getPropertyStatus());
			getSession().merge(transaction);*/
			
			
			try {
				saveOrUpdate(transaction);
			} catch (PSQLException e1) {
				LOGGER.error("Exception in TransactionalDetailsDaoImpl::saveTransactionDetails " + e.getMessage());
				throw new ISellDBException(-1, e.getMessage());
			}
		}
		catch(HibernateException | PSQLException e)
		{
			LOGGER.error("Exception in TransactionalDetailsDaoImpl::saveTransactionDetails " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	
		LOGGER.debug("Exit TransactionalDetailsDaoImpl::saveTransactionDetails");
	}

	public List<TransactionalDetails> findAllTransactionalDetails()
			throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<TransactionalDetails> findTransactionalDetailsByProperty(
			String propertyName) throws ISellDBException {
		try
		{
			PropertyDetails propertyDetails = propertyDetailsDao
					.findPropertyDetailsByUID(propertyName);
			Query query = getSession()
					.createQuery(
							"from  TransactionalDetails where propertyDetails = :propertyDetails");
			query.setParameter("propertyDetails", propertyDetails);
			LOGGER.debug("******************* TransactionDetails query is: "
					+ query.getQueryString());
			final List<TransactionalDetails> transctionalDetailsList = query.list();
	
			return transctionalDetailsList;
		}
		catch(HibernateException | PSQLException e)
		{
			LOGGER.error("Exception in TransactionalDetailsDaoImpl::findTransactionalDetailsByProperty " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	public List<TransactionalDetails> findTransactionalDetailsByCountry(
			String country) throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public TransactionalDetails findTransactionalDetailsByBookingReference(
			String bookingRefID, PropertyDetails propertyDetails,
			OnlineTravelAgentDetails otaDetails)
			throws ISellDBException {

		try
		{
			Query query = getSession().createSQLQuery(
					"select * from transactional_details s  where s.bookingrefid = :bookingrefid and s.PropertyId = :propertyid and s.OtaId = :otaid ")
					.addEntity(TransactionalDetails.class)
					.setParameter("bookingrefid", bookingRefID)
				  
				 .setParameter("propertyid", Integer.parseInt(propertyDetails.getId()+"")).setParameter("otaid", otaDetails.getId());
//					query.setCacheable(true);
					TransactionalDetails transctionalData = (TransactionalDetails) query.uniqueResult();
					return transctionalData;

		}
		catch( HibernateException | PSQLException e)
		{
			LOGGER.error("Exception in TransactionalDetailsDaoImpl::findTransactionalDetailsByBookingReference: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	public void deleteTransactionalDetails(String bookingRefID, int propertyID,
			int channelID) throws ISellDBException {

	}

	@Override
	public void flush() throws PSQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() throws PSQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit() throws PSQLException {
		Transaction tx = getSession().getTransaction();
		if(!tx.wasCommitted() ) { 
		    tx.commit();
		}
	}

	@Override
	public void deleteTransactionsForProperty(PropertyDetails propertyDetails)
			throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from TransactionalDetails where propertyDetails = :propertydetailsid");
			query.setParameter("propertydetailsid", propertyDetails);
			query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in TransactionalDetailsDaoImpl::deleteTransactionsForProperty : "
					+ e.getMessage());
			
			throw new ISellDBException(-1, e.getMessage());
		}
	}
}
