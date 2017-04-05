import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;


public class CalculateWeekOfYear {

	public static void main(String[] args) {
//		String date = "2017-01-02";
//		String date = "2017-01-03"; // value should be 1
		String date = "2017-01-09"; // Value should be 2
//		String date = "2017-01-08";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if( date == null || date.length() > 0)
		{
			try {
				Date checkInDate = formatter.parse(date);	
				Calendar checkInDateCalender= Calendar.getInstance();
				checkInDateCalender.setTime(checkInDate);
				
				//Get year from checkin date
				int year = checkInDateCalender.get(Calendar.YEAR);
				
				Date eightJanDate = formatter.parse( year + "-01-08");		
				
				Date sixJanDate = formatter.parse( year + "-01-06");
				Calendar sixJanDateCalender= Calendar.getInstance();
				sixJanDateCalender.setTime(sixJanDate);

				int dayOfWeek6Jan = sixJanDateCalender.get(Calendar.DAY_OF_WEEK);
				
				long timeDiff = eightJanDate.getTime() - dayOfWeek6Jan * 60 * 60 * 24;
				
				System.out.println("timeDiff: " + timeDiff);
				
				Calendar diffDateCalender= Calendar.getInstance();
				diffDateCalender.setTime(new Date(timeDiff));
				
				Date diffDate = new Date(timeDiff);
//						formatter.parse( diffDateCalender.get(Calendar.YEAR) + "-" + diffDateCalender.get(Calendar.MONTH) + "-" + diffDateCalender.get(Calendar.DATE));
				
				System.out.println("diffDateCalender: " + diffDate);
				
				long diffCheckinDate = diffDate.getTime() - checkInDate.getTime();
				
				diffCheckinDate = ( diffCheckinDate / (1000 * 60 * 60 * 24) ) - 5;
				
				System.out.println("Date difference is: " + diffCheckinDate);
				int weekNumber = 0;
				if(diffCheckinDate > 0)
				{
					weekNumber = 52;
				}
				else
				{
					weekNumber = checkInDateCalender.get(Calendar.WEEK_OF_YEAR)  ;
				}
				
				System.out.println("Week Number is: " + weekNumber);
		
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
