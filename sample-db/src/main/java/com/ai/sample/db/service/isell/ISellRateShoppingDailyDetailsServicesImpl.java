package com.ai.sample.db.service.isell;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.rateshopping.RateShoppingISellDataDTO;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.dao.isell.OtaPerformanceSummaryDetailsByDayDao;
import com.ai.sample.db.dao.property.transaction.TransactionalDetailsDao;
import com.ai.sample.db.model.property.configuration.PropertyDetails;

@Service("iSellRateShoppoingDailyDetailsServices")
@Transactional
public class ISellRateShoppingDailyDetailsServicesImpl implements
		ISellRateShoppoingDailyDetailsServices {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	TransactionalDetailsDao transactionalDetailsDao;

	@Autowired
	OtaPerformanceSummaryDetailsByDayDao otaPerformanceSummaryDetailsByDayDao;

	@Override
	public List<RateShoppingISellDataDTO> getRateShoppingDailyDetailsForProperty(
			Date businessDate, PropertyDetails propertyDetails, int numReportDays)
			throws ISellProcessingException {

		try {
			SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
			// myDate is the java.util.Date in yyyy-mm-dd format
			// Converting it into String using formatter
			String businessDateString = sm.format(businessDate);
			// Converting the String back to java.util.Date
			Date reportEndDate = DateUtils.addDays(businessDate, numReportDays);
			String reportEndDateString = sm.format(reportEndDate);

			String propID = propertyDetails.getId()+ "";
			SQLQuery query = sessionFactory
					.getCurrentSession()
					.createSQLQuery("SELECT date, coalesce(netrate, 0) as netrate, coalesce(onsiterate,0) as onsiterate, id, "
							+ "promotional, closed, roomtypedetailsid, coalesce(discount, 0) as disocunt, rateshoppingpropertyuid , "
							+ "rateshoppingotaid "
							+ "FROM generate_series(DATE(\'"
									+ businessDateString
									+ "\'),  "
									+ "DATE(\'"
									+ reportEndDateString
									+ "\'), '1 day') AS date "
							+ "LEFT OUTER JOIN ( SELECT distinct checkindate, netrate, onsiterate, A.id as id, promotional, "
							+ "closed, roomtypedetailsid, discount,ratepropertymapping.rateshoppingpropertyuid "
							+ "as rateshoppingpropertyuid, ratepropertymapping.rateshoppingotaid  as rateshoppingotaid "
							+ "FROM rate_shopping_rates_by_day AS A , rate_shopping_property_details_mapping "
							+ "ratepropertymapping WHERE A.netrate  = ( SELECT MIN(netrate) FROM rate_shopping_rates_by_day B, "
							+ "rate_shopping_property_details_mapping ratepropertymapping WHERE B.checkindate = A.checkindate "
							+ " and B.netrate <> 0 and B.rateShoppingPropertyDetailsID=ratepropertymapping.id and "
							+ "ratepropertymapping.propertydetailsid= "+ propID+ " ) and A.rateShoppingPropertyDetailsID = "
							+ "ratepropertymapping.id order by checkindate, netrate) results ON (date = results.checkindate) "
							+ "order by date");
		     
			List<Object[]> rows = query.list();
			List<RateShoppingISellDataDTO> rateShoppingDailyMinRatesList = new ArrayList<RateShoppingISellDataDTO>();
			String rateShopPropertyCode = "";
			String rateShopOtaCode = "";
			for (Object row[] : rows) {
				// int occupancy = (int) row[2];

				float netRate = Float.valueOf(row[1].toString());
				float onSiteRate = Float.valueOf(row[2].toString());
				float discount = 0;
				if(row[6] != null )
				{
					discount = Float.valueOf(row[6].toString());
				}
				boolean isPropmotional = false;
				if(row[4] != null )
				{
					isPropmotional = Boolean.valueOf(row[4].toString());
				}
				boolean isClosed = false;
				if( row[5] != null )
				{
					isClosed = Boolean.valueOf(row[5].toString());
				}
				if( row[8] != null)
				{
					rateShopPropertyCode = row[8].toString();
				}
				if(row[9] != null)
				{
					rateShopOtaCode = row[9].toString();
				}
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date checkinDate;

				checkinDate = format.parse(row[0].toString());
				RateShoppingISellDataDTO rateShoppingDailyMinRate = new RateShoppingISellDataDTO(
						rateShopPropertyCode, rateShopOtaCode, netRate,
						onSiteRate, checkinDate, isPropmotional, isClosed,
						discount);
				rateShoppingDailyMinRatesList.add(rateShoppingDailyMinRate);		        
			}
			return rateShoppingDailyMinRatesList;

		} catch (HibernateException | ParseException e) {
			throw new ISellProcessingException(-1, e.getMessage());
		}
	}
}
