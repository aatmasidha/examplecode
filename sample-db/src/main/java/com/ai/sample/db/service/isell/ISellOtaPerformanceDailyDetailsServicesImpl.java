package com.ai.sample.db.service.isell;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.ota.OtaSoldCountByOTA;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.dao.isell.OtaPerformanceSummaryDetailsByDayDao;
import com.ai.sample.db.dao.property.transaction.TransactionalDetailsDao;
import com.ai.sample.db.model.isell.OtaPerformanceSummaryDetailsByDay;
import com.ai.sample.db.model.isell.OtaPerformanceSummaryDetailsHistoryByDay;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Service("iSellOtaPerformanceDailyDetailsServices")
@Transactional
public class ISellOtaPerformanceDailyDetailsServicesImpl implements
		ISellOtaPerformanceDailyDetailsServices {

	Logger logger = Logger
			.getLogger(ISellOtaPerformanceDailyDetailsServicesImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	TransactionalDetailsDao transactionalDetailsDao;

	@Autowired
	OtaPerformanceSummaryDetailsByDayDao otaPerformanceSummaryDetailsByDayDao;

	@Override
	public void saveOrUpdateISellOtaPerformanceDailyDetailsData(
			Date businessDate, PropertyDetails propertyDetails)
			throws ISellProcessingException {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		// myDate is the java.util.Date in yyyy-mm-dd format
		// Converting it into String using formatter
		String strDate = sm.format(businessDate);
		// Converting the String back to java.util.Date
		// TODO check if property ID is required to be passed
		Date reportEndDate = DateUtils.addDays(businessDate, 90);
		String reportEndDateString = sm.format(reportEndDate);

		try {
			SQLQuery query = sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"select dates ,sum(td.totalamount "
									+ "/  DATE_PART('day', (td.DepartureDate - td.ArrivalDate )) + 1) as totalrevenue , "
									+ "sum(td.numrooms) as OccupancyCount from transactional_details td, "
									+ "(select  s.a as dates from generate_series(DATE(\'"
									+ strDate
									+ "\'),  "
									+ "DATE(\'"
									+ reportEndDateString
									+ "\'), '1 day') as s(a))  "
									+ "occupanyDate where  td.propertyid = :propertyDetailsID and occupanyDate.dates BETWEEN td.ArrivalDate AND td.DepartureDate "
									+ "AND occupanyDate.dates < td.departuredate and td.bookingstatusid in"
									+ "( select id from booking_status_master where name in ('Confirmed', 'Modified')) "
									+ "group BY dates order by dates");
			query.setParameter("propertyDetailsID", propertyDetails.getId());
			List<Object[]> rows = query.list();
	
			for (Object row[] : rows) {
				Integer occupancy = Integer.valueOf(row[2].toString());
				Float totalAmount = Float.valueOf(row[1].toString());

				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date checkinDate;

				checkinDate = format.parse(row[0].toString());

				// APARNA TODO LASTPICKUP
				OtaPerformanceSummaryDetailsByDay otaPerformanceSummaryDetailsByDay = otaPerformanceSummaryDetailsByDayDao
						.findOtaPerformanceForISellForADate(propertyDetails,
								checkinDate);

	/*			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			        Date occupancyDate = sdf.parse(row[0].toString());*/
			        
			
				
				if (otaPerformanceSummaryDetailsByDay == null) {
					/* OtaPerformanceSummaryDetailsByDay */otaPerformanceSummaryDetailsByDay = new OtaPerformanceSummaryDetailsByDay(
							propertyDetails, checkinDate, occupancy,
							totalAmount, occupancy, new Date());
				} else {
					int lastPickup = otaPerformanceSummaryDetailsByDay
							.getOtaSold();

					otaPerformanceSummaryDetailsByDay.setOtaSold(occupancy);
					otaPerformanceSummaryDetailsByDay
							.setPickUpFromLastLR(lastPickup);
					otaPerformanceSummaryDetailsByDay
							.setOccupanyDate(checkinDate);
					otaPerformanceSummaryDetailsByDay
							.setPickUpFromLastLR(occupancy - lastPickup);
					otaPerformanceSummaryDetailsByDay
							.setOtaTotalRevenue(totalAmount);
					otaPerformanceSummaryDetailsByDay
							.setAvgDailyRate(totalAmount / occupancy);
					otaPerformanceSummaryDetailsByDay
							.setIsellGenrationDate(new Date());
				}
				
/*				OtaPerformanceSummaryDetailsHistoryByDay otaPerformanceSummaryDetailsHistoryByDay 
				= new OtaPerformanceSummaryDetailsHistoryByDay(propertyDetails, checkinDate, occupancy, totalAmount, otaPerformanceSummaryDetailsByDay.getOtaSold(), businessDate);
*/
				otaPerformanceSummaryDetailsByDayDao
						.saveOtaPerformanceSummaryDetailsByDay(otaPerformanceSummaryDetailsByDay);
			}
		} catch (HibernateException | ParseException e) {
			logger.error("HibernateException in ISellOtaPerformanceDailyDetailsServicesImpl::saveOrUpdateISellOtaPerformanceDailyDetailsData :"
					+ e.getMessage());
			throw new ISellProcessingException(-1, e.getMessage());
		} catch (ISellDBException e) {
			logger.error("Exception in ISellOtaPerformanceDailyDetailsServicesImpl::saveOrUpdateISellOtaPerformanceDailyDetailsData :"
					+ e.getMessage());
			throw new ISellProcessingException(-1, e.getMessage());
		}
	}

	@Override
	public Map<Date, Map<String, OtaSoldCountByOTA>> findOtaSoldCountByOTA(
			Date businessDate, PropertyDetails propertyDetails,
			int numReportDays) throws ISellProcessingException {

		Map<Date, Map<String, OtaSoldCountByOTA>> otaSoldCountByOta = new HashMap<Date, Map<String, OtaSoldCountByOTA>>();

		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		// myDate is the java.util.Date in yyyy-mm-dd format
		// Converting it into String using formatter
		String businessDateString = sm.format(businessDate);
		// Converting the String back to java.util.Date
		Date reportEndDate = DateUtils.addDays(businessDate, numReportDays);
		String reportEndDateString = sm.format(reportEndDate);

		String propID = propertyDetails.getId() + "";

		SQLQuery query = sessionFactory
				.getCurrentSession()
				.createSQLQuery(
						"select dates ,ota.name , sum(td.numrooms) as OccupancyCount "
								+ " from transactional_details td,(select  s.a as dates from "
								+ "generate_series(DATE(\'"
								+ businessDateString
								+ "\'),  "
								+ "DATE(\'"
								+ reportEndDateString
								+ "\'), '1 day') "
								+ "as s(a))  occupanyDate,  online_travel_agent ota where  td.propertyid = "
								+ propID
								+ " and occupanyDate.dates BETWEEN td.ArrivalDate AND td.DepartureDate "
								+ "AND occupanyDate.dates < td.departuredate and td.bookingstatusid in"
								+ "( select id from booking_status_master where name in ('Confirmed', 'Modified')) "
								+ " and   td.otaid = ota.id group BY dates,ota.name order by dates");

		List<Object[]> rows = query.list();

		for (Object row[] : rows) {
			new HashMap<String, OtaSoldCountByOTA>();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date checkinDate;

			try {
				checkinDate = format.parse(row[0].toString());
				Map<String, OtaSoldCountByOTA> otaCountByOtaMap = otaSoldCountByOta
						.get(checkinDate);
				if (otaCountByOtaMap == null) {
					otaCountByOtaMap = new HashMap<String, OtaSoldCountByOTA>();
				}
				String otaName = (String) row[1];
				int soldCount = Integer.valueOf(row[2].toString());
/*				
				if(otaSoldMap == null)
				{
					otaSoldMap = new HashMap<String, OtaSoldCountByOTA>();
					OtaSoldCountByOTA otaSoldCountByOTA = new OtaSoldCountByOTA(date, otaName, 0 );
					otaSoldMap.put(otaName, otaSoldCountByOTA);
				}
*/				

				OtaSoldCountByOTA otaObj = new OtaSoldCountByOTA(checkinDate,
						otaName, soldCount);
				otaCountByOtaMap.put(otaName, otaObj);
				
				otaSoldCountByOta.put(checkinDate, otaCountByOtaMap);
			} catch (ParseException e) {
				logger.error("Exception while processing date in ISellOtaPerformanceDailyDetailsServicesImpl::findOtaSoldCountByOTA");
			}

		}

		return otaSoldCountByOta;
	}

}
