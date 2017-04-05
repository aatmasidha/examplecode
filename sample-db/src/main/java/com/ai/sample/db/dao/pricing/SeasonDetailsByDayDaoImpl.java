package com.ai.sample.db.dao.pricing;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.isell.InterceptPointDetailsDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.model.algo.SeasonDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Repository("seasonDetailsByDayDao")
public class SeasonDetailsByDayDaoImpl extends AbstractDao implements
		SeasonDetailsByDayDao {

	static final Logger LOGGER = Logger
			.getLogger(SeasonDetailsByDayDaoImpl.class);

	@Autowired
	private PropertyDetailsDao propertyDetailsDao;
	
	@Autowired
	InterceptPointDetailsDao interceptPointDetailsDao;

	@Override
	public void saveSeasonDetailsByDay(SeasonDetails seasonDetails)
			throws ISellDBException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SeasonDetails> findSeasonDetails() throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeasonDetails findSeasonDetailsFromWeekNumberForProperty(
			PropertyDetailsDTO propertyDetailsDto, int weekNumber)
			throws ISellDBException {
		try
		{
		PropertyDetails propertyDetails = propertyDetailsDao
				.findPropertyDetails(propertyDetailsDto);

		final Criteria criteria = getSession().createCriteria(
				SeasonDetails.class);
		criteria.add(Restrictions.eq("propertyDetails", propertyDetails));

		List<SeasonDetails> seasonDetailsList = criteria.list();
		SeasonDetails seasonData = null;
		for (SeasonDetails seasonDetails : seasonDetailsList) {
			if (seasonDetails.getCondition().equalsIgnoreCase("OR") ){
				if (seasonDetails.getToWeekCondition().equals(">=")
						&& seasonDetails.getFromWeekCondition().equals("<=")) {
					if (weekNumber >= seasonDetails.getFromWeek()
							|| weekNumber <= seasonDetails.getFromWeek()) {
						seasonData = seasonDetails;
					}
				}
				if (seasonDetails.getFromWeekCondition().equals("<=")
						&& seasonDetails.getToWeekCondition().equals(">=")) {
					if (weekNumber <= seasonDetails.getFromWeek()
							|| weekNumber >= seasonDetails.getFromWeek()) {
						seasonData = seasonDetails;
					}
				}
			} else if (seasonDetails.getCondition().equalsIgnoreCase("AND")) {
				if (seasonDetails.getFromWeekCondition().equals(">=")
						&& seasonDetails.getToWeekCondition().equals("<=")) {
					if (weekNumber >= seasonDetails.getFromWeek()
							|| weekNumber <= seasonDetails.getFromWeek()) {
						seasonData = seasonDetails;
					}
				}
				if (seasonDetails.getFromWeekCondition().equals("<=")
						&& seasonDetails.getToWeekCondition().equals(">=")) {
					if (weekNumber <= seasonDetails.getFromWeek()
							|| weekNumber  >= seasonDetails.getFromWeek()) {
						seasonData = seasonDetails;
					}
				}
			} else {
				if (seasonDetails.getFromWeekCondition().equals(">=")
						&& seasonDetails.getToWeekCondition().equals("<=")) {
					if (weekNumber >= seasonDetails.getFromWeek()
							|| weekNumber <= seasonDetails.getFromWeek()) {
						seasonData = seasonDetails;
					}
				}
				if (seasonDetails.getFromWeekCondition().equals("<=")
						&& seasonDetails.getToWeekCondition().equals(">=")) {
					if (weekNumber <= seasonDetails.getFromWeek()
							|| weekNumber >= seasonDetails.getFromWeek()) {
						seasonData = seasonDetails;

					}
				}

			}
		}
		return seasonData;
		}catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in SeasonDetailsByDayDaoImpl::findSeasonDetailsFromWeekNumberForProperty : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteSeasonDetailsForProperty(
			PropertyDetailsDTO propertyDetailsDto) throws ISellDBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSeasonDetailsForProperty(PropertyDetails propertyDetails)
			throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from SeasonDetails where propertyDetails = :propertyDetails");
			query.setParameter("propertyDetails", propertyDetails);
			query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in SeasonDetailsByDayDaoImpl::deleteSeasonDetailsForProperty : "
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
			LOGGER.error("Exception in SeasonDetailsByDayDaoImpl::commit" + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		 }
	}
}
