package com.ai.sample.db.dao.isell;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.dto.ota.OtaRevenueSummaryDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.isell.OtaPerformanceSummaryDetailsByDay;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Repository("otaPerformanceSummaryDetailsByDayDao")
public class OtaPerformanceSummaryDetailsByDayDaoImpl extends AbstractDao
implements OtaPerformanceSummaryDetailsByDayDao {

	Logger logger = Logger
			.getLogger(OtaPerformanceSummaryDetailsByDayDao.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void saveOtaPerformanceSummaryDetailsByDay(
			OtaPerformanceSummaryDetailsByDay otaPerformanceSummaryDetailsByDay)
					throws ISellDBException {
		try {
			saveOrUpdate(otaPerformanceSummaryDetailsByDay);
		} catch (PSQLException | HibernateException e) {
			logger.error("Hibernate Exception in OtaPerformanceSummaryDetailsByDayDaoImpl::saveOtaPerformanceSummaryDetailsByDay is :"
					+ e.getMessage());
			throw new ISellDBException(
					-1,
					"Hibernate Exception in OtaPerformanceSummaryDetailsByDayDaoImpl::saveOtaPerformanceSummaryDetailsByDay is :"
							+ e.getMessage());
		}
	}

	@Override
	public List<OtaRevenueSummaryDetailsDTO> findOtaPerformanceForISell(
			PropertyDetails propertyDetails, Date businessDate,
			int reportNumDays) throws ISellDBException {
		try {
			SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
			// myDate is the java.util.Date in yyyy-mm-dd format
			// Converting it into String using formatter
			String businessDateString = sm.format(businessDate);
			// Converting the String back to java.util.Date
			Date reportEndDate = DateUtils.addDays(businessDate, reportNumDays);
			String reportEndDateString = sm.format(reportEndDate);

			SQLQuery query = sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"select date, coalesce(otasold, 0) as otasold, coalesce(otatotalrevenue, 0) as otatotalrevenue, "
									+ "coalesce(avgdailyrate, 0) as avgdailyrate, coalesce(pickupfromlastlr, 0) as pickupfromlastlr from generate_series(DATE(\'"
									+ businessDateString
									+ "\'),  DATE(\'"
									+ reportEndDateString
									+ "\') "
									+ ", '1 day') AS date LEFT OUTER JOIN (select distinct  occupanydate, otasold,  "
									+ "otatotalrevenue, avgdailyrate, pickupfromlastlr from ota_performance_summary_details_by_day where "
									+ "propertydetailsid = :propertyDetailsID  ) results ON (date = results.occupanydate) order by date");
			query.setParameter("propertyDetailsID", propertyDetails.getId());

			logger.info("OtaPerformanceSummaryDetailsByDayDaoImpl::query "
					+ query.getQueryString());
			List<Object[]> rows = query.list();
			List<OtaRevenueSummaryDetailsDTO> otaRevenueSummaryDetailsDTOList = new ArrayList<OtaRevenueSummaryDetailsDTO>();
			for (Object row[] : rows) {
				Date occupancyDate = sm.parse(row[0].toString());
				int otaSoldCount = Integer.parseInt(row[1].toString());
				float otaTotalRevenue = Float.parseFloat(row[2].toString());
				float otaADR = Float.parseFloat(row[3].toString());
				int pickupFromLastRun = Integer.parseInt(row[4].toString());
				OtaRevenueSummaryDetailsDTO otaRevenueSummaryDetailsDTO = new OtaRevenueSummaryDetailsDTO(
						occupancyDate, otaSoldCount, otaTotalRevenue, otaADR,
						pickupFromLastRun);
				otaRevenueSummaryDetailsDTOList
				.add(otaRevenueSummaryDetailsDTO);
			}
			return otaRevenueSummaryDetailsDTOList;
		} catch (ParseException e) {
			logger.error("ParseException::findEventsFromToDate"
					+ e.getMessage());
			throw new ISellDBException(1,
					"ParseException::findEventsFromToDate is: "
							+ e.getMessage());
		}
	}

	@Override
	public OtaPerformanceSummaryDetailsByDay findOtaPerformanceForISellForADate(
			PropertyDetails propertyDetails, Date businessDate)
					throws ISellDBException {
		try {
			final Criteria criteria = getSession().createCriteria(
					OtaPerformanceSummaryDetailsByDay.class);
			criteria.add(Restrictions.eq("propertyDetails", propertyDetails));
			criteria.add(Restrictions.eq("occupanyDate", businessDate));
			return (OtaPerformanceSummaryDetailsByDay) criteria.uniqueResult();
		} catch (PSQLException | HibernateException e) {
			logger.error("Hibernate Exception in OtaPerformanceSummaryDetailsByDayDaoImpl::findOtaPerformanceForISellForADate is :"
					+ e.getMessage());
			throw new ISellDBException(
					-1,
					"Hibernate Exception in OtaPerformanceSummaryDetailsByDayDaoImpl::findOtaPerformanceForISellForADate is :"
							+ e.getMessage());
		}
	}

	@Override
	public void deleteOTAPerformanceForProperty(PropertyDetails propertyDetails)
			throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from OtaPerformanceSummaryDetailsByDay where propertyDetails = :propertydetailsid");
			query.setParameter("propertydetailsid", propertyDetails);
			query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			logger.error("Exception in OtaPerformanceSummaryDetailsByDayDaoImpl::deleteOTAPerformanceForProperty : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

}
