package com.ai.sample.db.dao.pricing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.pricing.RecommendedPriceByDayDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.model.pricing.RecommendedPriceByDay;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Repository("recommendedPriceByDayDao")
public class RecommendedPriceByDayDaoImpl extends AbstractDao implements RecommendedPriceByDayDao{

	static final Logger LOGGER = Logger.getLogger(RecommendedPriceByDayDaoImpl.class);

	@Autowired
	private PropertyDetailsDao propertyDetailsDao;

	@Override
	public void saveRecommendedPriceByDay(
			RecommendedPriceByDay recommendedPriceByDay)
			throws ISellDBException {
		try
		{
			saveOrUpdate(recommendedPriceByDay);
		}
		catch(PSQLException | HibernateException e)
		{
			LOGGER.error("Exception in RecommendedPriceByDayDaoImpl::saveRecommendedPriceByDay : " + e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<RecommendedPriceByDay> findRecommendedPriceByDay()
			throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RecommendedPriceByDay findRecommendedPriceForPropertyFromDate(
			PropertyDetailsDTO propertyDetailsDto, Date businessDate)
			throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRecommendedPriceByDay(
			PropertyDetailsDTO propertyDetailsDto) throws ISellDBException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RecommendedPriceByDayDTO> findRecommendedPriceForProperty(
			PropertyDetails propertyDetails, Date businessDate,
			int numReportDays) throws ISellDBException {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		// myDate is the java.util.Date in yyyy-mm-dd format
		// Converting it into String using formatter
		String businessDateString = sm.format(businessDate);
		// Converting the String back to java.util.Date
		Date reportEndDate = DateUtils.addDays(businessDate, numReportDays);
		String reportEndDateString = sm.format(reportEndDate);
		
		try {
			SQLQuery query = getSession()
					.createSQLQuery(
							"select date, coalesce(finalrecommendedrate, 0) as finalrecommendedrate, "
							+ "coalesce(systemdefinedrate, 0) as systemdefinedrate, coalesce(overridetype, '') as overridetype, "
							+ "coalesce(overwritten, false) as overwritten from generate_series(DATE(\'" +businessDateString+ "\'),  "
							+ "DATE(\'" +reportEndDateString + "\'), '1 day') AS date "
							+ "LEFT OUTER JOIN (select distinct  checkindate, finalrecommendedrate, systemdefinedrate, overridetype,overwritten "
							+ "from recommended_price_by_day where propertydetailsid=:propertyDetailsID ) results "
							+ "ON (date = results.checkindate) order by date ");

			query.setParameter("propertyDetailsID", propertyDetails.getId());

			
			List<Object[]> rows = query.list();
			List<RecommendedPriceByDayDTO> recommendedPriceByDayDTOList = new ArrayList<RecommendedPriceByDayDTO>();
			for (Object row[] : rows) {
				Date occupancyDate  = sm.parse(row[0].toString());
				float finalRecommendedRate = Float.parseFloat(row[1].toString());
		
				
				RecommendedPriceByDayDTO occupancyDetailsDTO = new RecommendedPriceByDayDTO(occupancyDate, finalRecommendedRate);
				recommendedPriceByDayDTOList.add(occupancyDetailsDTO);
			}
			return recommendedPriceByDayDTOList;
		} catch (ParseException | PSQLException |HibernateException e) {
			LOGGER.error("Exception in RecommendedPriceByDayDaoImpl::findRecommendedPriceForProperty : " + e.getMessage());
			throw new ISellDBException(1, "RecommendedPriceByDayDaoImpl::findRecommendedPriceForProperty: " + e.getMessage());
		}
	}

	@Override
	public void deleteRecommendedPriceByDayForProperty(
			PropertyDetails propertyDetails) throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from RecommendedPriceByDay where propertyDetails = :propertydetailsid");
			query.setParameter("propertydetailsid", propertyDetails);
			query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in RecommendedPriceByDayDaoImpl::deleteRoomTypesForProperty : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
		
	}


}
