package ua.chntu.sheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

public class DateWrapper {
	
	final static String LOG_TAG = "myLogs";
	
	enum Day {
		ODD_SN(0),
		ODD_MN(1),
		ODD_TU(2),
		ODD_WE(3),
		ODD_TH(4),
		ODD_FR(5),
		ODD_ST(6),
		EVEN_SN(7),
		EVEN_MN(8),
		EVEN_TU(9),
		EVEN_WE(10),
		EVEN_TH(11),
		EVEN_FR(12),
		EVEN_ST(13);
		
		private int dayIndex;

		// TODO: remove dayIndex with ordinal()?
		private Day(int dayIndex) { this.dayIndex = dayIndex; }

		public static Day valueOf(int dayIndex) {
			dayIndex %= 14;
			
			for (Day day : Day.values()) {
				if (day.dayIndex == dayIndex) return day;
			}
			return ODD_MN;
		}
	}
	
	private Calendar currentDate;
	private Day currentDay;	
	private static final SimpleDateFormat DATE_FORMAT = 
			new SimpleDateFormat("dd.MM", Locale.getDefault());
	
	public DateWrapper() {
		currentDate = Calendar.getInstance();
		currentDay = getDayFromCurrentDate();	
	}
	
	public Day next() {
		currentDate.roll(Calendar.DAY_OF_YEAR, true);
		currentDay = getDayFromCurrentDate();
		Log.d(LOG_TAG, "Day::next " + currentDay.toString());
		return currentDay;
	}
	
	public Day previous() {
		currentDate.roll(Calendar.DAY_OF_YEAR, false);
		currentDay = getDayFromCurrentDate();
		return currentDay;
	}
	
	public Day setCurrentDate(Date date) {
		if (currentDate.getTime().compareTo(date) != 0) {
			currentDate.setTime(date);
			currentDay = getDayFromCurrentDate();
		}
		Log.d(LOG_TAG, "Day::setCurrentDate " + toString());
		return currentDay;
	}
	
	public Date getCurrentDate() {
		return currentDate.getTime();
	}
	
	public Day getCurrentDay() {
		return currentDay;
	}

	public String toString() {
		return DATE_FORMAT.format(currentDate.getTime());
	}
	
	private Day getDayFromCurrentDate() {
		Calendar september1st = Calendar.getInstance();
		september1st.set(2015, Calendar.SEPTEMBER, 1);
		
		int weekOfClasses = (currentDate.get(Calendar.WEEK_OF_YEAR) -
				september1st.get(Calendar.WEEK_OF_YEAR)) % 2;
		if (weekOfClasses < 0) weekOfClasses *= -1;
		
		return Day.valueOf(weekOfClasses * 7 + 
				currentDate.get(Calendar.DAY_OF_WEEK) - 1);
	}
	
}
