package com.ai.sample.db.dao.isell;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.dto.rateshopping.ISellRateShoppingRatesByDayDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.model.isell.RateShoppingRatesSummaryByDay;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.type.MyJson;
import com.google.gson.Gson;

@Repository("iSellRateShoppingRatesByDayDao")
public class ISellRateShoppingRatesByDayDaoImpl extends AbstractDao implements
ISellRateShoppingRatesByDayDao {

	Logger logger = Logger
			.getLogger(OtaPerformanceSummaryDetailsByDayDao.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public RateShoppingRatesSummaryByDay findRateShoppingRateForPropertyAndDay(
			PropertyDetails propertyDetails, Date date) throws ISellDBException {
		try {
			final Criteria criteria = getSession().createCriteria(
					RateShoppingRatesSummaryByDay.class);
			criteria.add(Restrictions.eq("propertyDetails", propertyDetails));
			criteria.add(Restrictions.eq("businessDate", date));
			return (RateShoppingRatesSummaryByDay) criteria.uniqueResult();
		} catch (PSQLException | HibernateException e) {
			logger.error("Exception in ISellRateShoppingRatesByDayDaoImpl::findRateShoppingRateForPropertyAndDay : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public RateShoppingRatesSummaryByDay saveOrUpdateRateShoppingRateForPropertyAndDay(
			RateShoppingRatesSummaryByDay iSellRateShoppingRatesByDay)
					throws ISellDBException {
		try
		{
			saveOrUpdate(iSellRateShoppingRatesByDay);
			return iSellRateShoppingRatesByDay;
		} catch (PSQLException | HibernateException e) {
			logger.error("Exception in ISellRateShoppingRatesByDayDaoImpl::saveOrUpdateRateShoppingRateForPropertyAndDay : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public Map<Date, ArrayList<ISellRateShoppingRatesByDayDTO>> findRateShoppingRateForPropertyForConfigurableDays(
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
							"select date, rateshoppingrates "
									+ "from generate_series(DATE(\'"
									+ businessDateString
									+ "\'),  "
									+ "DATE(\'"
									+ reportEndDateString
									+ "\'), '1 day') AS date LEFT OUTER JOIN "
									+ "(select distinct  businessdate, rateshoppingrates from "
									+ "rate_shopping_rates_summary_by_day where propertydetailsid = :propertyDetailsID   ) results ON (date = results.businessdate) order by date");
			query.setParameter("propertyDetailsID", propertyDetails.getId());
			List<Object[]> rows = query.list();
			Map<Date, ArrayList<ISellRateShoppingRatesByDayDTO>> propertyCompetitorRateList = new LinkedHashMap<Date, ArrayList<ISellRateShoppingRatesByDayDTO>>();
			for (Object row[] : rows) {

				ArrayList<ISellRateShoppingRatesByDayDTO> isellDayRatesList = new ArrayList<ISellRateShoppingRatesByDayDTO>();
				if (row[1] != null) {
					byte[] bytes = (byte[]) row[1];

					MyJson jsonRate = new MyJson();
					List<String> objList = new ArrayList<String>();

					Gson gson = new Gson();

					String propertyRate = new String(bytes);
					jsonRate.setStringProp(propertyRate);
					objList.add(jsonRate.getStringProp());

					StringTokenizer st2 = new StringTokenizer(propertyRate, "{");

					while (st2.hasMoreElements()) {

						String propertyData = (String) st2.nextElement();
						logger.debug("Property Data: " + propertyData);
						if (propertyData.contains("propertyDetailsID")) {
							String data = "{" + propertyData;
							data = data.substring(0, data.length() - 1);
							ISellRateShoppingRatesByDayDTO rateShoprate = (ISellRateShoppingRatesByDayDTO) gson
									.fromJson(
											data,
											ISellRateShoppingRatesByDayDTO.class);
							logger.debug(rateShoprate.toString());
							isellDayRatesList.add(rateShoprate);
						}
					}
				}
				Date occupancyDate;

				occupancyDate = sm.parse(row[0].toString());

				propertyCompetitorRateList
				.put(occupancyDate, isellDayRatesList);
			}
			return propertyCompetitorRateList;
		} catch (ParseException e) {
			throw new ISellDBException(1, e.getMessage());
		}
	}

}
