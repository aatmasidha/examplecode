package com.ai.sample.db.dao.isell;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.algo.SeasonDetails;
import com.ai.sample.db.model.algo.SlopePointDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Repository("slopePointDetailsDao")
public class SlopePointDetailsDaoImpl extends AbstractDao
		implements SlopePointDetailsDao {

	Logger LOGGER = Logger
			.getLogger(SlopePointDetailsDaoImpl.class);

	@Override
	public float findSlopeForPropertyDayOfWeekAndSeason(
			PropertyDetails propertyDetails, SeasonDetails seasonDetails, String dow)
			throws  ISellProcessingException {
		try
		{
			final Criteria criteria = getSession().createCriteria(
					SlopePointDetails.class);
			criteria.add(Restrictions.eq("propertyDetails", propertyDetails));
			criteria.add(Restrictions.eq("seasonDetails", seasonDetails));
			SlopePointDetails slopePointDetails = (SlopePointDetails) criteria.uniqueResult();
			if(slopePointDetails == null)
			{
				LOGGER.error("Slope is not defined for property: " + propertyDetails.getName() + " season: " + seasonDetails.getId());
				throw new ISellProcessingException(1, "Slope is not defined for property: " + propertyDetails.getName() + " season: " + seasonDetails.getId());
			}
			float slopeValue = 0.0f;
			
			switch(dow)
			{
				case "Monday" : 
							slopeValue = slopePointDetails.getDow1();	
							break;
				case "Tuesday" : 
							slopeValue = slopePointDetails.getDow2();	
							break;
				case "Wednesday" :
							slopeValue = slopePointDetails.getDow3();	
							break;
				case "Thursday" :
							slopeValue = slopePointDetails.getDow4();	
							break;
				case "Friday" : 
							slopeValue = slopePointDetails.getDow5();	
							break;
				case "Saturday" :
							slopeValue = slopePointDetails.getDow6();	
							break;
				case "Sunday" : 
							slopeValue = slopePointDetails.getDow7();	
							break;
					
			}
			return slopeValue;
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in SlopePointDetailsDaoImpl::findSlopeForPropertyDayOfWeekAndSeason : "
					+ e.getMessage());
			throw new  ISellProcessingException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteSlopeDetailsForProperty(PropertyDetails propertyDetails)
			throws ISellDBException {
		try
		{
			Query query1 = getSession().createQuery(
					"delete from SlopePointDetails where propertyDetails = :propertyDetails");
			query1.setParameter("propertyDetails", propertyDetails);
			query1.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in SeasonDetailsByDayDaoImpl::deleteSeasonDetailsForProperty : "
					+ e.getCause().getMessage());
			throw new ISellDBException(-1, e.getCause().getMessage());
		}
	}
}
