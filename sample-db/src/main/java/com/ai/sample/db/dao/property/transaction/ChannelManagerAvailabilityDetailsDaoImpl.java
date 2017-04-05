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
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.sample.common.dto.ChannelManagerISellDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.common.AbstractDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.mapping.PropertyRoomTypeMappingDao;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;
import com.ai.sample.db.model.property.transaction.ChannelManagerAvailabilityDetails;

@Repository("channelManagerAvailabilityDetailsDao")
public class ChannelManagerAvailabilityDetailsDaoImpl extends AbstractDao
implements ChannelManagerAvailabilityDetailsDao {

	@Autowired
	PropertyDetailsDao propertyDetailsDao;

	@Autowired
	PropertyRoomTypeMappingDao propertyRoomTypeMappingDao;

	static final Logger LOGGER = Logger
			.getLogger(ChannelManagerAvailabilityDetailsDaoImpl.class);

	@Override
	public void saveChannelManagerAvailabilityDetails(
			ChannelManagerAvailabilityDetails channelManagerAvailabilityDetails)
					throws ISellDBException {
		try {
			saveOrUpdate(channelManagerAvailabilityDetails);
		} catch (HibernateException | PSQLException e) {
			LOGGER.error("Exception in ChannelManagerAvailabilityDetailsDaoImpl::saveChannelManagerAvailabilityDetails : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public ChannelManagerAvailabilityDetails findChannelManagerAvailabilityByDate(
			PropertyDetailsDTO propertyDetailsDto, Date occupancyDate,
			String roomType) throws ISellDBException {
		try {
			PropertyDetails propertyDetails = propertyDetailsDao
					.findPropertyDetails(propertyDetailsDto);
			PropertyRoomTypeMapping propertyRoomTypeMapping = propertyRoomTypeMappingDao
					.findRoomTypeByNameForProperty(roomType, propertyDetails);
			if (propertyDetails == null || propertyRoomTypeMapping == null) {
				throw new ISellDBException(
						1,
						"Property or property room type mapping missing PropertyOccupancyDetailsDaoImpl::findPropertyOccupancyByDate for property:  "
								+ propertyDetailsDto.getName()
								+ " For Room Type: " + roomType);
			}
			final Criteria criteria = getSession().createCriteria(
					ChannelManagerAvailabilityDetails.class);

			criteria.add(Restrictions.eq("roomTypeMaster",
					propertyRoomTypeMapping.getRoomTypeMaster()));
			criteria.add(Restrictions.eq("propertyDetails", propertyDetails));
			criteria.add(Restrictions.eq("occupancyDate", occupancyDate));

			ChannelManagerAvailabilityDetails propertyOccupancyDetails = (ChannelManagerAvailabilityDetails) criteria
					.uniqueResult();
			return propertyOccupancyDetails;
		} catch (HibernateException | PSQLException e) {
			LOGGER.error("Exception in ChannelManagerAvailabilityDetailsDaoImpl::findChannelManagerAvailabilityByDate : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public List<ChannelManagerISellDTO> findChannelManagerAvailabilityDetailsForISell(
			PropertyDetails propertyDetails, Date businessDate,
			int numReportDays) throws ISellDBException {
		try
		{
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
								"select date as occupancydate, coalesce(minRatePlanAmount, 0) as minRatePlanAmount, coalesce(remainingcapacity, 0) as remainingcapacity "
										+ "from generate_series(DATE(\'"
										+ businessDateString
										+ "\'),  DATE(\'"
										+ reportEndDateString
										+ "\'), '1 day') AS date "
										+ "LEFT OUTER JOIN (select distinct  occupancydate, minRatePlanAmount, sum(remainingcapacity) as remainingcapacity "
										+ "from channel_manager_availability_details where propertyid=:propertyDetailsID group by   occupancydate, minRatePlanAmount ) "
										+ "results ON (date = results.occupancydate) order by date ");
				query.setParameter("propertyDetailsID", propertyDetails.getId());

				List<Object[]> rows = query.list();
				List<ChannelManagerISellDTO> iSellChannelManagerDetailsList = new ArrayList<ChannelManagerISellDTO>();
				for (Object row[] : rows) {
					Date occupancyDate = sm.parse(row[0].toString());
					float minRatePlan = Float.parseFloat(row[1].toString());
					int remainingcapacity = Integer.parseInt(row[2].toString());

					ChannelManagerISellDTO occupancyDetailsDTO = new ChannelManagerISellDTO(
							occupancyDate, minRatePlan, remainingcapacity);
					iSellChannelManagerDetailsList.add(occupancyDetailsDTO);
				}
				return iSellChannelManagerDetailsList;
			} catch (ParseException e) {
				throw new ISellDBException(
						1,
						"ChannelManagerAvailabilityDetailsDaoImpl::findChannelManagerAvailabilityDetailsForISell: "
								+ e.getMessage());
			}
		} catch (HibernateException | PSQLException e) {
			LOGGER.error("Exception in ChannelManagerAvailabilityDetailsDaoImpl::findChannelManagerAvailabilityDetailsForISell : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void flush() throws ISellDBException {
		try
		{
			getSession().flush();
		} catch (HibernateException | PSQLException e) {
			LOGGER.error("Exception in ChannelManagerAvailabilityDetailsDaoImpl::flush : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void clear() throws ISellDBException {
		try
		{
			getSession().clear();
		}
		catch (HibernateException | PSQLException e) {
			LOGGER.error("Exception in ChannelManagerAvailabilityDetailsDaoImpl::clear : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteChannelManagerDataForFuture(
			PropertyDetails propertyDetails, Date businessDate)
					throws ISellDBException {
		try
		{
			Query query = getSession()
					.createQuery(
							"delete ChannelManagerAvailabilityDetails where propertyDetails = :propertyDetails and occupancyDate >= :occupancyDate");
			query.setParameter("propertyDetails", propertyDetails);
			query.setParameter("occupancyDate", businessDate);

			query.executeUpdate();

			flush();
		} catch (HibernateException | PSQLException e) {
			LOGGER.error("Exception in ChannelManagerAvailabilityDetailsDaoImpl::deleteChannelManagerDataForFuture : "
					+ e.getMessage());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public void deleteChannelManagerDataForFuture(
			PropertyDetails propertyDetails) throws ISellDBException {
		try
		{
			Query query = getSession().createQuery(
					"delete from ChannelManagerAvailabilityDetails where propertyDetails = :propertyDetails");
			query.setParameter("propertyDetails", propertyDetails);
			query.executeUpdate();
		}
		catch (PSQLException | HibernateException e) {
			LOGGER.error("Exception in ChannelManagerAvailabilityDetailsDaoImpl::deleteChannelManagerDataForFuture : "
					+ e.getCause().getMessage());
			throw new ISellDBException(-1, e.getCause().getMessage());
		}
		
	}

}
