package com.ai.sample.db.service.algo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.sample.common.dto.ChannelManagerISellDTO;
import com.ai.sample.common.dto.OccupancyDetailsDTO;
import com.ai.sample.common.dto.PropertyDetailsDTO;
import com.ai.sample.common.dto.pricing.RecommendedPriceByDayDTO;
import com.ai.sample.common.exception.ISellDBException;
import com.ai.sample.common.exception.ISellProcessingException;
import com.ai.sample.db.dao.isell.InterceptPointDetailsDao;
import com.ai.sample.db.dao.isell.SlopePointDetailsDao;
import com.ai.sample.db.dao.ota.OnlineTravelAgentDetailsDao;
import com.ai.sample.db.dao.pricing.RecommendedPriceByDayDao;
import com.ai.sample.db.dao.pricing.SeasonDetailsByDayDao;
import com.ai.sample.db.dao.property.configuration.PropertyDetailsDao;
import com.ai.sample.db.dao.property.transaction.ChannelManagerAvailabilityDetailsDao;
import com.ai.sample.db.dao.property.transaction.PropertyOccupancyDetailsDao;
import com.ai.sample.db.model.algo.SeasonDetails;
import com.ai.sample.db.model.property.configuration.PropertyDetails;
import com.ai.sample.isell.util.CustomDateFunctions;

@Service("calculateRecommendedPrice")
@Transactional
public class CalculateRecommendedPriceImpl implements CalculateRecommendedPrice {

	Logger logger = Logger.getLogger(CalculateRecommendedPriceImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	RecommendedPriceByDayDao recommendedPriceByDayDao;

	@Autowired
	OnlineTravelAgentDetailsDao onlineTravelAgentDetailsDao;
	
	@Autowired
	SeasonDetailsByDayDao seasonDetailsByDayDao;
	
	@Autowired
	PropertyOccupancyDetailsDao propertyOccupancyDetailsDao;
	
	@Autowired
	ChannelManagerAvailabilityDetailsDao channelManagerAvailabilityDetailsDao;

	@Autowired
	PropertyDetailsDao propertyDetailsDao;
	
	@Autowired
	SlopePointDetailsDao slopePointDetailsDao;
	
	@Autowired
	InterceptPointDetailsDao interceptPointDetailsDao;
	
	
/*	@Override
	public float[] calculateRecommendedPriceForProperty(
			PropertyDetailsDTO propertyDetailsDto, Date businessDate,
			int numReportDays) throws ISellProcessingException {
		
		try {

			float[] recommendedPriceArray = new float[numReportDays+1];
			PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);

			List<OccupancyDetailsDTO> remainingCapacityByOccupancyList = propertyOccupancyDetailsDao.findOccupancyDetailsForISell(propertyDetails, businessDate, numReportDays);
			
			List<ChannelManagerISellDTO> channelAvailabilityDetails = channelManagerAvailabilityDetailsDao.findChannelManagerAvailabilityDetailsForISell(propertyDetails, businessDate, numReportDays);
			
			int cnt = 0;
			for (OccupancyDetailsDTO row : remainingCapacityByOccupancyList) {
				

				Date checkinDate = row.getOccupancyDate();
				int weekNumber = CustomDateFunctions
						.getWeekNumberFromDate(checkinDate);

				RecommendedPriceByDayDTO recommendedPriceByDayDto =  new RecommendedPriceByDayDTO();
				
				recommendedPriceByDayDto.setCheckindate(checkinDate);
				 
				
				  SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
				  String input_date=format1.format(checkinDate);
				  Date dt1 = format1.parse(input_date);
				  DateFormat format2=new SimpleDateFormat("EEEE"); 
				  String finalDay = format2.format(dt1);
				  
				
				recommendedPriceByDayDto.setWeekNumber(weekNumber);
				SeasonDetails seasonDetails = seasonDetailsByDayDao.findSeasonDetailsFromWeekNumberForProperty(propertyDetailsDto, weekNumber);
				if(seasonDetails == null )
				{
					logger.error("Season Details could not be found for date: " + checkinDate);
					logger.error("Week Number is: " + checkinDate);
					continue;
				}
				recommendedPriceByDayDto.setSeasonDetails(seasonDetails.getId());
				
				
				int remainingCapacityFromOccupancy = row.getVacantCount();
				
				logger.debug("cnt " + cnt);
				logger.debug("channelAvailabilityDetails.get(cnt)" + channelAvailabilityDetails.get(cnt));
				int remainingCapacityFromChannel = channelAvailabilityDetails.get(cnt).getRemainingCapacity();
				
				int remainingCapacity = 0;
				if(remainingCapacityFromOccupancy > 0)
				{
					remainingCapacity = remainingCapacityFromOccupancy;
				}
				else
				{
					remainingCapacity = remainingCapacityFromChannel;
				}
				
				recommendedPriceByDayDto.setPrice(remainingCapacity);
				
				float interceptValue = interceptPointDetailsDao.findInterceptForPropertyDayOfWeekAndSeason(seasonDetails.getPropertyDetails(), seasonDetails, finalDay);
				float slopeValue = slopePointDetailsDao.findSlopeForPropertyDayOfWeekAndSeason(seasonDetails.getPropertyDetails(), seasonDetails, finalDay);
				if(slopeValue == 0)
				{
					slopeValue = 0.0009f;
				}
				float recommendedPrice = (remainingCapacity + interceptValue) / slopeValue;
				if(recommendedPrice < 0 )
				{
					recommendedPrice = -recommendedPrice;
				}
				recommendedPriceArray[cnt] = recommendedPrice;
				
				cnt++;
			}
			return recommendedPriceArray;
		} catch (ISellProcessingException e) {
			logger.error("ISellDBException is CalculateRecommendedPriceImpl:: " + e.getMessage() );
			logger.error("ISellDBException in CalculateRecommendedPriceImpl:: " + e.getMessage());
			throw e;
		} catch (ISellDBException e) {
			logger.error("ISellDBException is CalculateRecommendedPriceImpl:: " + e.getMessage() );
			throw new ISellProcessingException(1, e.getMessage());
		} catch (ParseException e) {
			logger.error("ParseException is CalculateRecommendedPriceImpl:: " + e.getMessage() );
			throw new ISellProcessingException(1, e.getMessage());
		}
	}*/
	
	@Override
	public float[] calculateRecommendedPriceForProperty(
			PropertyDetailsDTO propertyDetailsDto, Date businessDate,
			int numReportDays) throws ISellProcessingException {
		
		try {

			float[] recommendedPriceArray = new float[numReportDays+1];
			PropertyDetails propertyDetails = propertyDetailsDao.findPropertyDetails(propertyDetailsDto);

			List<OccupancyDetailsDTO> remainingCapacityByOccupancyList = propertyOccupancyDetailsDao.findOccupancyDetailsForISell(propertyDetails, businessDate, numReportDays);
			
			List<ChannelManagerISellDTO> channelAvailabilityDetails = channelManagerAvailabilityDetailsDao.findChannelManagerAvailabilityDetailsForISell(propertyDetails, businessDate, numReportDays);
			
			int cnt = 0;
			for (OccupancyDetailsDTO row : remainingCapacityByOccupancyList) {
				

				Date checkinDate = row.getOccupancyDate();
				int weekNumber = CustomDateFunctions
						.getWeekNumberFromDate(checkinDate);

				RecommendedPriceByDayDTO recommendedPriceByDayDto =  new RecommendedPriceByDayDTO();
				
				recommendedPriceByDayDto.setCheckindate(checkinDate);
				 
				
				  SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
				  String input_date=format1.format(checkinDate);
				  Date dt1 = format1.parse(input_date);
				  DateFormat format2=new SimpleDateFormat("EEEE"); 
				  String finalDay = format2.format(dt1);
				  
				
	/*			recommendedPriceByDayDto.setWeekNumber(weekNumber);
				SeasonDetails seasonDetails = seasonDetailsByDayDao.findSeasonDetailsFromWeekNumberForProperty(propertyDetailsDto, weekNumber);
				if(seasonDetails == null )
				{
					logger.error("Season Details could not be found for date: " + checkinDate);
					logger.error("Week Number is: " + checkinDate);
					continue;
				}
				recommendedPriceByDayDto.setSeasonDetails(seasonDetails.getId());
	*/			
				
				int remainingCapacityFromOccupancy = row.getVacantCount();
				
				logger.debug("cnt " + cnt);
				logger.debug("channelAvailabilityDetails.get(cnt)" + channelAvailabilityDetails.get(cnt));
				int remainingCapacityFromChannel = channelAvailabilityDetails.get(cnt).getRemainingCapacity();
				
				int remainingCapacity = 0;
				if(remainingCapacityFromOccupancy > 0)
				{
					remainingCapacity = remainingCapacityFromOccupancy;
				}
				else
				{
					remainingCapacity = remainingCapacityFromChannel;
				}
				
				recommendedPriceByDayDto.setPrice(remainingCapacity);
				
			/*	float interceptValue = interceptPointDetailsDao.findInterceptForPropertyDayOfWeekAndSeason(seasonDetails.getPropertyDetails(), seasonDetails, finalDay);
				float slopeValue = slopePointDetailsDao.findSlopeForPropertyDayOfWeekAndSeason(seasonDetails.getPropertyDetails(), seasonDetails, finalDay);
				if(slopeValue == 0)
				{
					slopeValue = 0.0009f;
				}
				float recommendedPrice = (remainingCapacity + interceptValue) / slopeValue;
				if(recommendedPrice < 0 )
				{
					recommendedPrice = -recommendedPrice;
				}
				recommendedPriceArray[cnt] = recommendedPrice;
				*/
				cnt++;
			}
			return recommendedPriceArray;
		} catch (ISellProcessingException e) {
			logger.error("ISellDBException is CalculateRecommendedPriceImpl:: " + e.getMessage() );
			logger.error("ISellDBException in CalculateRecommendedPriceImpl:: " + e.getMessage());
			throw e;
		} catch (ISellDBException e) {
			logger.error("ISellDBException is CalculateRecommendedPriceImpl:: " + e.getMessage() );
			throw new ISellProcessingException(1, e.getMessage());
		} catch (ParseException e) {
			logger.error("ParseException is CalculateRecommendedPriceImpl:: " + e.getMessage() );
			throw new ISellProcessingException(1, e.getMessage());
		}
	}

}
