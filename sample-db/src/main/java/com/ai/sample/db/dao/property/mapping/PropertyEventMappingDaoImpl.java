package com.ai.sample.db.dao.property.mapping;

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

import com.ai.sample.common.dto.isell.ISellEventDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyEventMapping;

@Repository("propertyEventMappingDao")
public class PropertyEventMappingDaoImpl extends AbstractDao implements
		PropertyEventMappingDao {
	Logger logger = Logger.getLogger(PropertyEventMappingDaoImpl.class);
	@Autowired
	PropertyDetailsDao propertyDetailsDao;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void saveOrUpdatePropertyCompetitorMappingDetails(
			PropertyEventMapping propertyEventMapping)
			throws ISellDBException {
		try
		{
			saveOrUpdate(propertyEventMapping);
		}catch (PSQLException | HibernateException e) {
			logger.error("Exception in PropertyEventMappingDaoImpl::saveOrUpdatePropertyCompetitorMappingDetails : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PropertyEventMapping> findEventsForProperty(
			PropertyDetails propertyDetails) throws ISellDBException {
		try
		{
		final Criteria criteria = getSession().createCriteria(
				PropertyEventMapping.class);
		criteria.add(Restrictions.eq("propertyDetails", propertyDetails));
		return (List<PropertyEventMapping>) criteria.list();
		}catch (PSQLException | HibernateException e) {
			logger.error("Exception in PropertyEventMappingDaoImpl::findEventsForProperty : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}

	}

	@Override
	public List<ISellEventDetailsDTO> findEventsFromToDate(
			PropertyDetails propertyDetails, Date businessDate,
			int numReportDays) throws ISellDBException {
		try {
			SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
			// myDate is the java.util.Date in yyyy-mm-dd format
			// Converting it into String using formatter
			String businessDateString = sm.format(businessDate);
			// Converting the String back to java.util.Date
			Date reportEndDate = DateUtils.addDays(businessDate, numReportDays);
			String reportEndDateString = sm.format(reportEndDate);

			SQLQuery query = sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT date, "
									+ "CASE "
									+ "	when extract(dow from date) = 0 "
									+ "then 'Sun' when extract(dow from date) = 1 "
									+ "then 'Mon' when extract(dow from date) = 2 "
									+ "then 'Tue' when extract(dow from date) = 3 "
									+ "then 'Wed' when extract(dow from date) = 4 "
									+ "then 'Thu' when extract(dow from date) = 5 "
									+ "then 'Fri' when extract(dow from date) = 6 "
									+ "then 'Sat' END as dayOfWeek"
									+ ", eventname as eventName, eventtype as eventType "
//									+ "FROM generate_series(current_date,  current_date +   interval '90 day', '1 day') AS date "
									+ "FROM generate_series( Date(\'" + businessDateString +  "\'),  Date (\'"+ reportEndDateString  + "\'), '1 day') AS date "
									+ "LEFT OUTER JOIN (SELECT date_trunc('day', eventconfi1_.eventDate) as day, "
									+ "eventconfi1_.name as eventname, event_type_configuration.name as eventtype "
									+ "FROM event_type_configuration, property_event_mapping this_ inner join event_configuration eventconfi1_ "
									+ "on this_.eventconfigurationid=eventconfi1_.id WHERE this_.propertydetailsid= :propertyDetailsID and eventconfi1_.eventDate "
									+ "between \'"
									+ businessDateString
									+ "\' and \'"
									+ reportEndDateString
									+ "\' and eventconfi1_.eventtypeid=event_type_configuration.id "
									+ "group by eventname, day, event_type_configuration.name) results ON (date = results.day) "
									+ "order by date");
			query.setParameter("propertyDetailsID", propertyDetails.getId());
			List<Object[]> rows = query.list();
			List<ISellEventDetailsDTO> iSellEventDetailsDTOList = new ArrayList<ISellEventDetailsDTO>();
			for (Object row[] : rows) {
				Date occupancyDate;

				occupancyDate = sm.parse(row[0].toString());

				String dayOfWeek = row[1].toString();
				String eventName = "";
				if(row[2] != null )
				{
					eventName = row[2].toString();
				}
				String eventType = "";
				if(row[3] != null )
				{
					eventType = row[3].toString();
				}
				ISellEventDetailsDTO eventDetailsDto = new ISellEventDetailsDTO(
						occupancyDate, dayOfWeek, eventName, eventType);
				iSellEventDetailsDTOList.add(eventDetailsDto);
			}
			return iSellEventDetailsDTOList;
		} catch (ParseException e) {
			logger.error("ParseException::findEventsFromToDate" + e.getMessage());
			throw new ISellDBException(1, "ParseException::findEventsFromToDate is: " + e.getMessage());
		}
	}

	@Override
	public void deletePropertyEvents(PropertyDetails propertyDetails)
			throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from PropertyEventMapping where propertyDetails = :propertyDetails");
			query.setParameter("propertyDetails", propertyDetails);
			query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			logger.error("Exception in PropertyEventMappingDaoImpl::deletePropertyEvents : "
					+ e.getCause().getMessage());
			throw new ISellDBException(-1, e.getCause().getMessage());
		}
	}

}
