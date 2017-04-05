package com.ai.sample.db.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.rateshopping.RateShoppingRatesByDayDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.db.dao.property.mapping.PropertyRoomTypeMappingDao;
import com.ai.sample.db.dao.property.mapping.RateShoppingPropertyDetailsMappingDao;
import com.ai.sample.db.dao.rateshopping.RateShoppingRateCodeTypeDao;
import com.ai.sample.db.dao.rateshopping.RateShoppingRatesByDayDao;
import com.ai.sample.db.model.property.mapping.PropertyRoomTypeMapping;
import com.ai.sample.db.model.property.mapping.RateShoppingPropertyDetailsMapping;
import com.ai.sample.db.model.rateshopping.RateShoppingRateCodeType;
import com.ai.sample.db.model.rateshopping.RateShoppingRatesByDay;

@Service("rateShoppingRatesByDayService")
@Transactional
public class RateShoppingRatesByDayServiceImpl implements
		RateShoppingRatesByDayService {

	Logger logger = Logger.getLogger(RateShoppingRatesByDayServiceImpl.class);

	@Autowired
	private RateShoppingRatesByDayDao dao;

	@Autowired
	private RateShoppingPropertyDetailsMappingDao rateShoppingPropertyDetailsMappingDao;

	@Autowired
	private PropertyRoomTypeMappingDao roomTypeDao;
	
	@Autowired
	private RateShoppingRateCodeTypeDao rateShoppingRateCodeTypeDao;

	@Override
	public void saveRateShoppingRatesByDay(
			RateShoppingRatesByDayDTO rateShoppingRatesByDayDTO)
			throws ISellDBException {
		try {
			RateShoppingPropertyDetailsMapping rateShoppingPropertyDetails = rateShoppingPropertyDetailsMappingDao
					.findRateShoppingPropertyDetails(
							rateShoppingRatesByDayDTO.getHotelCode(),
							rateShoppingRatesByDayDTO.getOtaCode());
			String propertyName = rateShoppingPropertyDetails
					.getPropertyDetails().getName();
			String roomTypeName = rateShoppingRatesByDayDTO.getRoomType();
			if (roomTypeName == null || roomTypeName.isEmpty()) {
				roomTypeName = "Hotel";
			}
			roomTypeName = roomTypeName.trim();
			if (rateShoppingRatesByDayDTO.getNetRate() == 0.0) {
				rateShoppingRatesByDayDTO.setNetRate(rateShoppingRatesByDayDTO
						.getOnSiteRate());
			}

			RateShoppingRatesByDay rateShoppingRatesByDay = dao
					.findRateShoppingRateByCheckinDayRoomTypeDiscount(
							rateShoppingPropertyDetails,
							rateShoppingRatesByDayDTO.getCheckinDate(),
							roomTypeName,
							rateShoppingRatesByDayDTO.getNetRate());

			if(rateShoppingRatesByDayDTO.getRateTypeCode() == null )
			{
				logger.info("");
			}
			RateShoppingRateCodeType rateShoppingRateCodeType = rateShoppingRateCodeTypeDao.findRateCodeForType(rateShoppingRatesByDayDTO.getRateTypeCode(), "rateshopping");
			if (rateShoppingRatesByDay == null) {
				logger.info("RateShoppingRatesByDay Parameters are rateShoppingPropertyDetails.getId() : "
						+ rateShoppingPropertyDetails.getId()
						+ " rateShoppingRatesByDayDTO.getCheckinDate(): "
						+ rateShoppingRatesByDayDTO.getCheckinDate()
						+ " roomTypeName: "
						+ roomTypeName
						+ " rateShoppingRatesByDayDTO.getNetRate(): "
						+ rateShoppingRatesByDayDTO.getNetRate());
				PropertyRoomTypeMapping roomType = roomTypeDao
						.findRoomTypeByNameForProperty(roomTypeName,
								rateShoppingPropertyDetails
										.getPropertyDetails());
				if (roomType == null) {

					logger.error("Property Room Type mapping does not exists for room type: "
							+ roomTypeName + " Property: " + propertyName);
					throw new ISellDBException(-1,
							"Property Room Type mapping does not exists for room type: "
									+ roomTypeName + " Property: "
									+ propertyName);
				}

				if (rateShoppingRatesByDayDTO.getNetRate() == 0.0) {
					rateShoppingRatesByDayDTO
							.setNetRate(rateShoppingRatesByDayDTO
									.getOnSiteRate());
				} else {
					rateShoppingRatesByDayDTO
							.setNetRate(rateShoppingRatesByDayDTO.getNetRate());
					rateShoppingRatesByDayDTO
							.setOnSiteRate(rateShoppingRatesByDayDTO
									.getOnSiteRate());
				}

				
				rateShoppingRatesByDay = new RateShoppingRatesByDay(
						rateShoppingPropertyDetails,
						rateShoppingRatesByDayDTO.getNetRate(),
						rateShoppingRatesByDayDTO.getOnSiteRate(),
						rateShoppingRatesByDayDTO.getCheckinDate(),
						rateShoppingRatesByDayDTO.getCheckoutDate(),
						rateShoppingRatesByDayDTO.isPromotional(),
						rateShoppingRatesByDayDTO.isClosed(),
						rateShoppingRatesByDayDTO.getDiscount(),
						rateShoppingRateCodeType, rateShoppingRatesByDayDTO.getRateTypeDescription(), roomType,
						new Date());
			} else {
				if (rateShoppingRatesByDayDTO.getNetRate() == 0.0
						&& rateShoppingRatesByDayDTO.getOnSiteRate() == 0.0) {
					// If both the rates are zero then do not update the rate
					// value as
					// last received value is more appropriate.
					rateShoppingRatesByDay.setNetRate(rateShoppingRatesByDay
							.getNetRate());
					rateShoppingRatesByDay.setOnsiteRate(rateShoppingRatesByDay
							.getOnsiteRate());
				} else {
					rateShoppingRatesByDay.setNetRate(rateShoppingRatesByDayDTO
							.getNetRate());
					rateShoppingRatesByDay
							.setOnsiteRate(rateShoppingRatesByDayDTO
									.getOnSiteRate());
				}
				rateShoppingRatesByDay.setPromotional(rateShoppingRatesByDayDTO
						.isPromotional());
				rateShoppingRatesByDay.setRateCodeType(rateShoppingRateCodeType);
				rateShoppingRatesByDay.setDiscount(rateShoppingRatesByDayDTO
						.getDiscount());
				rateShoppingRatesByDay.setClosed(rateShoppingRatesByDayDTO
						.isClosed());
				rateShoppingRatesByDay.setUpdatedOn(new Date());
			}
			logger.info("RateShoppingRatesByDay"
					+ rateShoppingRatesByDay.toString());
			dao.saveRateShoppingRatesByDay(rateShoppingRatesByDay);
		} catch (HibernateException e) {
			logger.error("Exception in saveRateShoppingRatesByDay :"
					+ e.getMessage() + " for object:"
					+ rateShoppingRatesByDayDTO.toString());
			logger.error("Exception Cause in saveRateShoppingRatesByDay :"
					+ e.getCause() + " for object:"
					+ rateShoppingRatesByDayDTO.toString());
			throw new ISellDBException(-1, e.getMessage());
		}
	}

	@Override
	public RateShoppingRatesByDayDTO findRateShoppingRateByCheckinDayRoomTypeDiscount(
			RateShoppingRatesByDayDTO rateShoppingRatesByDayDTO)
			throws ISellDBException {
		return null;
	}

	@Override
	public List<RateShoppingRatesByDay> findRateShoppingRateByDay(
			RateShoppingRatesByDayDTO rateShoppingRatesByDay)
			throws ISellDBException {
		// TODO Auto-generated method stub
		return null;
	}

}
