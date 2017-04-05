package com.ai.sample.db.dao.isell;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.algo.InterceptPointDetails;
import com.ai.sample.db.model.algo.SeasonDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;


@Repository("interceptPointDetailsDao")
public class InterceptPointDetailsDaoImpl extends AbstractDao implements InterceptPointDetailsDao{

	Logger logger = Logger.getLogger(InterceptPointDetailsDaoImpl.class);
	@Override
	public float findInterceptForPropertyDayOfWeekAndSeason(
			PropertyDetails propertyDetails, SeasonDetails seasonDetails, String dow) throws ISellProcessingException {
		try
		{
		final Criteria criteria = getSession().createCriteria(
				InterceptPointDetails.class);
		criteria.add(Restrictions.eq("propertyDetails", propertyDetails));
		criteria.add(Restrictions.eq("seasonDetails", seasonDetails));
		InterceptPointDetails interceptPointDetails = (InterceptPointDetails) criteria.uniqueResult();
		if(interceptPointDetails == null)
		{
			logger.error("Intercept is not defined for property: " + propertyDetails.getName() + " season: " + seasonDetails.getId());
			throw new ISellProcessingException(1, "Intercept is not defined for property: " + propertyDetails.getName() + " season: " + seasonDetails.getId());
		}
		float interceptValue = 0.0f;
		
		switch(dow)
		{
			case "Monday" : 
						interceptValue = interceptPointDetails.getDow1();	
						break;
			case "Tuesday" : 
						interceptValue = interceptPointDetails.getDow2();	
						break;
			case "Wednesday" :
						interceptValue = interceptPointDetails.getDow3();	
						break;
			case "Thursday" :
						interceptValue = interceptPointDetails.getDow4();	
						break;
			case "Friday" : 
						interceptValue = interceptPointDetails.getDow5();	
						break;
			case "Saturday" :
						interceptValue = interceptPointDetails.getDow6();	
						break;
			case "Sunday" : 
						interceptValue = interceptPointDetails.getDow7();	
						break;
				
		}
		return interceptValue;
		}
		catch(HibernateException | PSQLException e)
		{
			logger.error("Exception in InterceptPointDetailsDaoImpl::findInterceptForPropertyDayOfWeekAndSeason" + e.getMessage());
			throw new ISellProcessingException(-1, e.getMessage());
		}
	}
	
	@Override
	public void deleteInterceptPointDetailForProperty(
			PropertyDetails propertyDetails) throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from InterceptPointDetails where propertyDetails = :propertyDetails");
			query.setParameter("propertyDetails", propertyDetails);
			query.executeUpdate();
//			commit();
		}
		catch (PSQLException | HibernateException e) {
			logger.error("Exception in InterceptPointDetailsDaoImpl::deleteInterceptPointDetailForProperty : "
					+ e.getCause().getMessage());
			throw new ISellDBException(-1, e.getCause().getMessage());
		}	
	}

	@Override
	public void commit() throws ISellDBException {
		 try
		 {
			 Transaction tx = getSession().getTransaction();
				if(!tx.wasCommitted() ) { 
				    tx.commit();
				}
		 }
		catch(PSQLException e)
		 {
			logger.error("Exception in InterceptPointDetailsDaoImpl::commit" + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		 }
	}

}
