package ua.chntu.sheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.util.Log;
import ua.chntu.sheduler.DateWrapper.Day;

public class ScheduleProvider {
	
	final static String LOG_TAG = "myLogs";
	
	private static final HashMap<Day, Schedule> SCEDULES;
	private static DateWrapper currentDay;
	
	static {
		currentDay = new DateWrapper();
		SCEDULES = new HashMap<Day, Schedule>();
		
		SCEDULES.put(Day.ODD_MN, new Schedule("I ПН ", new ArrayList<Classes>() {
			{
				add(new Classes("", ""));
				add(new Classes("Grid обч. (лб)", "Пріла IV-73"));
				add(new Classes("Сучасні мет.наук.досл. (лек)", "Литвинов IV-95"));
			}
		}));
		SCEDULES.put(Day.ODD_TU, new Schedule("I ВТ ", new ArrayList<Classes>() {
			{
				add(new Classes("", ""));
				add(new Classes("Дослідж. ІР техн (лек)", "Заровський IV-63"));
				add(new Classes("Проектув. інтерф. (лб)", "Кисіль IV-63"));
			}
		}));
		SCEDULES.put(Day.ODD_WE, new Schedule("I СР ", new ArrayList<Classes>() {
			{
				add(new Classes("Дослідж.ІР техн (лб)", "Заровський IV-73"));
				add(new Classes("Проектув. інтерф. (лек)", "Пріла IV-63"));
			}
		}));
		SCEDULES.put(Day.ODD_TH, new Schedule("I ЧТ ", new ArrayList<Classes>() {
			{
				add(new Classes("", ""));
				add(new Classes("", ""));
				add(new Classes("Ін. мова", "Литвин I-202"));
				add(new Classes("Сучасні мет.наук.досл. (лб)", "Скітер IV-10"));
			}
		}));
		SCEDULES.put(Day.ODD_FR, new Schedule("I ПТ ", new ArrayList<Classes>() {
			{
			}
		}));
		SCEDULES.put(Day.ODD_ST, new Schedule("I СБ ", new ArrayList<Classes>() {
			{
			}
		}));
		SCEDULES.put(Day.ODD_SN, new Schedule("I НД ", new ArrayList<Classes>() {
			{
			}
		}));

		SCEDULES.put(Day.EVEN_MN, new Schedule("II ПН ", new ArrayList<Classes>() {
			{
				add(new Classes("", ""));
				add(new Classes("", ""));
				add(new Classes("Сучасні мет.наук.досл. (лек)", "Литвинов IV-95"));
				add(new Classes("Grid обч. (лб)", "Пріла IV-73"));
			}
		}));
		SCEDULES.put(Day.EVEN_TU, new Schedule("II ВТ ", new ArrayList<Classes>() {
			{
				add(new Classes("", ""));
				add(new Classes("Ін. мова", "Литвин I-202"));
				add(new Classes("Сучасні мет.наук.досл. (пр)", "Скітер IV-63"));
			}
		}));
		SCEDULES.put(Day.EVEN_WE, new Schedule("II СР ", new ArrayList<Classes>() {
			{
				add(new Classes("Проектув. інтерф. (лб)", "Кисіль IV-63"));
				add(new Classes("Grid обч. (лек)", "Пріла IV-63"));
			}
		}));
		SCEDULES.put(Day.EVEN_TH, new Schedule("II ЧТ ", new ArrayList<Classes>() {
			{
				add(new Classes("", ""));
				add(new Classes("Дослідж.ІР техн (лб)", "Заровський IV-73"));
				add(new Classes("Сучасні мет.наук.досл. (лб)", "Скітер IV-54"));
			}
		}));
		SCEDULES.put(Day.EVEN_FR, new Schedule("II ПТ ", new ArrayList<Classes>() {
			{
			}
		}));
		SCEDULES.put(Day.EVEN_ST, new Schedule("II СБ ", new ArrayList<Classes>() {
			{
			}
		}));
		SCEDULES.put(Day.EVEN_SN, new Schedule("II НД ", new ArrayList<Classes>() {
			{
			}
		}));
	}

	public ArrayList<Classes> getSchedule(Day day) {
		return SCEDULES.get(day).getClasses();
	}
	
	public String getDate(Day day) {
		return SCEDULES.get(day).getDate() + currentDay.toString();
	}
	
	public Day getNextDay() {
		return currentDay.next();
	}

	public Day getPrevDay() {
		return currentDay.previous();
	}

	public Day getDay(Date d) {
		return currentDay.setCurrentDate(d);
	}
	
	public Day getToday() {
		return currentDay.setCurrentDate(new Date());
	}
	
}
