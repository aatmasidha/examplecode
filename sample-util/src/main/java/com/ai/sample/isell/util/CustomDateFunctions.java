package com.ai.sample.isell.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ai.sample.common.exception.ISellProcessingException;

public class CustomDateFunctions {

	static Logger logger = Logger.getLogger(CustomDateFunctions.class);
//	The logic to calculate the number of week needs to be implemented
//	as the week should start from First Monday of the year
//	2013-01-07 should be part of first week.
	public static int getWeekNumberFromDate(Date checkinDate)
			throws ISellProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(checkinDate); // Value should be 2
		int weekNumber = 0;
		if (date == null || date.length() > 0) {
			try {
				Date checkInDate = formatter.parse(date);
				Calendar checkInDateCalender = Calendar.getInstance();
				checkInDateCalender.setTime(checkInDate);

				// Get year from checkin date
				int year = checkInDateCalender.get(Calendar.YEAR);

				Date eightJanDate = formatter.parse(year + "-01-08");

				Date sixJanDate = formatter.parse(year + "-01-06");
				Calendar sixJanDateCalender = Calendar.getInstance();
				sixJanDateCalender.setTime(sixJanDate);

				int dayOfWeek6Jan = sixJanDateCalender
						.get(Calendar.DAY_OF_WEEK);

				long timeDiff = eightJanDate.getTime() - dayOfWeek6Jan * 60
						* 60 * 24;

				logger.debug("timeDiff: " + timeDiff);

				Calendar diffDateCalender = Calendar.getInstance();
				diffDateCalender.setTime(new Date(timeDiff));

				Date diffDate = new Date(timeDiff);

				logger.debug("diffDateCalender: " + diffDate);

				long diffCheckinDate = diffDate.getTime()
						- checkInDate.getTime();

				// 5 is subtracted as the Python and Java output has fix
				// difference of 5
				diffCheckinDate = (diffCheckinDate / (1000 * 60 * 60 * 24)) - 5;

				logger.debug("Date difference is: " + diffCheckinDate);
				
				if (diffCheckinDate > 0) {
					weekNumber = 52;
				} else {
					weekNumber = checkInDateCalender.get(Calendar.WEEK_OF_YEAR);
					if(weekNumber > 52)
					{
						weekNumber = 52;
					}
				}

				logger.debug("Week Number is: " + weekNumber);
				if (weekNumber == 0) {
					logger.error("Unable to calculate number of weeke in CustomDateFunctions::getWeekNumberFromDate");
					throw new ISellProcessingException(
							-1,
							"Unable to calculate number of weeke in CustomDateFunctions::getWeekNumberFromDate");
				}
				

			} catch (ParseException e) {
				logger.error("Exception in CustomDateFunctions::getWeekNumberFromDate" + e.getMessage());
				throw new ISellProcessingException(-1,
						"Exception in CustomDateFunctions::getWeekNumberFromDate" + e.getMessage());
			}
		}
		return weekNumber;

	}

}
