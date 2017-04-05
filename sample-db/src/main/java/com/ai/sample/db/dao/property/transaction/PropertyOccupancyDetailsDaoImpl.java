package com.ai.sample.db.dao.property.transaction;

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

import com.ai.sample.common.dto.OccupancyDetailsDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.mapping.PropertyRoomTypeMappingDao;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;
import com.ai.sample.db.model.property.transaction.PropertyOccupancyDetails;

@Repository("propertyOccupancyDetailsDao")
public class PropertyOccupancyDetailsDaoImpl extends AbstractDao implements
		PropertyOccupancyDetailsDao {

	static final Logger LOGGER = Logger
			.getLogger(PropertyOccupancyDetailsDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	PropertyDetailsDao propertyDetailsDao;

	@Autowired
	PropertyRoomTypeMappingDao propertyRoomTypeMappingDao;

	@Override
	public void savePropertyOccupancyDetails(
			PropertyOccupancyDetails propertyOccupancyDetails)
			throws ISellDBException {
		try {
			saveOrUpdate(propertyOccupancyDetails);
		} catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyOccupancyDetailsDaoImpl::savePropertyOccupancyDetails"
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void flush() {
		flush();

	}

	@Override
	public void clear() {
		clear();
	}

	@Override
	public PropertyOccupancyDetails findPropertyOccupancyByDate(
			PropertyDetailsDTO propertyDetailsDto, Date occupancyDate,
			String roomType) throws ISellDBException {

		try
		{
			PropertyDetails propertyDetails = propertyDetailsDao
					.findPropertyDetails(propertyDetailsDto);
			PropertyRoomTypeMapping propertyRoomTypeMapping = propertyRoomTypeMappingDao
					.findRoomTypeByNameForProperty(roomType, propertyDetails);
			if (propertyDetails == null || propertyRoomTypeMapping == null) {
				throw new ISellDBException(
						1,
						"Property or property room type mapping missing PropertyOccupancyDetailsDaoImpl::findPropertyOccupancyByDate for property:  "
								+ propertyDetailsDto.getName()
								+ " For Room Type: "
								+ roomType);
			}
			final Criteria criteria = getSession().createCriteria(
					PropertyOccupancyDetails.class);
	
			criteria.add(Restrictions.eq("roomTypeMaster",
					propertyRoomTypeMapping.getRoomTypeMaster()));
			criteria.add(Restrictions.eq("propertyDetails", propertyDetails));
			criteria.add(Restrictions.eq("occupancyDate", occupancyDate));
	
			PropertyOccupancyDetails propertyOccupancyDetails = (PropertyOccupancyDetails) criteria
					.uniqueResult();
			
			if(propertyOccupancyDetails != null)
			{
				propertyOccupancyDetails.setLastPickup(propertyOccupancyDetails.getOccupancy());
			}
			return propertyOccupancyDetails;
		}
		catch(PSQLException | HibernateException e)
		{
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<OccupancyDetailsDTO> findOccupancyDetailsForISell(
			PropertyDetails propertyDetails, Date businessDate,
			int numReportDays)  throws ISellDBException {
		
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		// myDate is the java.util.Date in yyyy-mm-dd format
		// Converting it into String using formatter
		String businessDateString = sm.format(businessDate);
		// Converting the String back to java.util.Date
		Date reportEndDate = DateUtils.addDays(businessDate, numReportDays);
		String reportEndDateString = sm.format(reportEndDate);
		
		try {
			SQLQuery query = sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"select date, coalesce(capacity, 0) as capacity, "
									+ "coalesce(occupancy, 0) as occupancy, coalesce(outoforder, 0) as outoforder, coalesce(vacantCount, 0) as vacantCount, coalesce(lastpickup, 0) as lastpickup "
									+ "from generate_series(DATE(\'" +businessDateString+ "\'),  DATE(\'" +reportEndDateString + "\'), '1 day') AS date "
									+ "LEFT OUTER JOIN (select distinct  occupancydate, capacity, sum(occupancy) as occupancy, "
									+ "sum(outofordercount) as outoforder, vacantCount, sum(lastpickup) as lastpickup from property_occupancy_details where propertyid=:propertyDetailsID"
									+ " group by   occupancydate, capacity, vacantCount ) results ON (date = results.occupancydate) order by date ");
			query.setParameter("propertyDetailsID", propertyDetails.getId());

			
			List<Object[]> rows = query.list();
			List<OccupancyDetailsDTO> iSellEventDetailsDTOList = new ArrayList<OccupancyDetailsDTO>();
			for (Object row[] : rows) {
				Date occupancyDate  = sm.parse(row[0].toString());
				int capacity = Integer.parseInt(row[1].toString());
				int occupancy = Integer.parseInt(row[2].toString());
				int outOfOrder  = Integer.parseInt(row[3].toString());
				int vacant = Integer.parseInt(row[4].toString());
				int lastPickUp = Integer.parseInt(row[5].toString());
				
//				TODO at present from occupancy report we get incorrect value
//				of hotel capacity so setting this value from property details.
//				Bug-4 TFSat
				capacity = propertyDetails.getCapcity();
				OccupancyDetailsDTO occupancyDetailsDTO = new OccupancyDetailsDTO(occupancyDate, capacity, occupancy, outOfOrder, vacant,  lastPickUp);
				iSellEventDetailsDTOList.add(occupancyDetailsDTO);
			}
			return iSellEventDetailsDTOList;
		} catch ( ParseException e) {
			LOGGER.error("Exception in PropertyOccupancyDetailsDaoImpl::findOccupancyDetailsForISell"
					+ e.getMessage());
			throw new ISellDBException(1, "PropertyOccupancyDetailsDaoImpl::findOccupancyDetailsForISell: " + e.getMessage());
		}
	}

	@Override
	public void deleteOccupancyDetailsForProperty(
			PropertyDetails propertyDetails) throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from PropertyOccupancyDetails where propertyDetails = :propertydetails");
			query.setParameter("propertydetails", propertyDetails);
			query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in PropertyOccupancyDetailsDaoImpl::deleteOccupancyDetailsForProperty : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
		
	}

}
