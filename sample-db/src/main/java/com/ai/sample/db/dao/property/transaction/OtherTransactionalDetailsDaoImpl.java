package com.ai.sample.db.dao.property.transaction;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.mapping.PropertyRoomTypeMappingDao;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;
import com.ai.sample.db.model.property.transaction.OtherTransactionalDetails;
import com.ai.sample.db.model.property.transaction.TransactionalDetails;

@Repository("otherTransactionalDetailsDao")
public class OtherTransactionalDetailsDaoImpl extends AbstractDao implements
OtherTransactionalDetailsDao {

	static final Logger LOGGER = Logger
			.getLogger(OtherTransactionalDetailsDaoImpl.class);

	@Autowired
	private PropertyDetailsDao propertyDetailsDao;
	
	@Autowired
	PropertyRoomTypeMappingDao  propertyRoomTypeMappingDao;
	
	@Override
	public void flush() throws ISellDBException {
	try
	{
		getSession().flush();
	}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in OtherTransactionalDetailsDaoImpl::flush : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void clear() throws ISellDBException {
		try
		{
		getSession().clear();
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in OtherTransactionalDetailsDaoImpl::clear : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void saveOtherTransactionDetails(
			OtherTransactionalDetails transaction) throws ISellDBException {
		
		try
		{
			saveOrUpdate(transaction);
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("Exception in OtherTransactionalDetailsDaoImpl::saveOtherTransactionDetails" + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public OtherTransactionalDetails findOtherTransactionalDetailsForTransactionAndRoomType(
			TransactionalDetails transactionDetails, PropertyRoomTypeMapping propertyRoomType) throws ISellDBException {
		try
		{
		LOGGER.debug("Inside OtherTransactionalDetailsDaoImpl::findOtherTransactionalDetailsForTransactionAndRoomType");

		final Criteria criteria = getSession().createCriteria(
				OtherTransactionalDetails.class);

		criteria.add(Restrictions.eq("roomType", propertyRoomType));
		criteria.add(Restrictions.eq("transactionalDetails", transactionDetails));
		criteria.setCacheable(true);
		OtherTransactionalDetails otherTransactionDetails = (OtherTransactionalDetails) criteria
				.uniqueResult();
		LOGGER.debug("Exit OtherTransactionalDetailsDaoImpl::findOtherTransactionalDetailsForTransactionAndRoomType");
		return otherTransactionDetails;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in OtherTransactionalDetailsDaoImpl::findOtherTransactionalDetailsForTransactionAndRoomType : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public OtherTransactionalDetails findOtherTransactionalDetailsForTransaction(
			TransactionalDetails transactionDetails) throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRoomTypeTransactionDetailsForProperty(
			PropertyDetails propertyDetails) throws ISellDBException {
		try
		{
			Query query = getSession().createSQLQuery(
					"delete from other_transactional_details where  propertyroomtypeid in "
					+ "( select id from property_room_type_mapping where propertydetailsid = :propertyDetails) ");
			query.setInteger("propertyDetails", propertyDetails.getId());
			query.executeUpdate();
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("Exception in OtherTransactionalDetailsDaoImpl::deleteRoomTypeTransactionDetailsForProperty: " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

}
