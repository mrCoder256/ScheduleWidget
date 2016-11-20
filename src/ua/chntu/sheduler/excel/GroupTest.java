package ua.chntu.sheduler.excel;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GroupTest {
	
	private String name;
	private ArrayList<ScheduleForDay> schedule;
	
	private class ScheduleForDay {
		
		public String day;
		public ArrayList<LessonTest> classes;

		public ScheduleForDay(String day, ArrayList<LessonTest> classes) {
			this.day = day;
			this.classes = classes;
		}

	}
	
	public GroupTest(String name) {
		this.name = name;
		schedule = new ArrayList<ScheduleForDay>();
	}
	
	public String getGroupName() {
		return name;
	}

	public GroupTest addDay(String name, ArrayList<LessonTest> classes) {
		schedule.add(schedule.size(), new ScheduleForDay(name, classes));
		return this;
	}

	public void printTo(PrintWriter textFile) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("____________\n");
		sBuilder.append("Гпупа " + name + ".\n");
		sBuilder.append("** Непарний тиждень **\n");
		for (int iDay = 0; iDay < schedule.size(); iDay++) {
			if ((iDay > 0) && (schedule.get(iDay).day.equalsIgnoreCase("ПН"))) {
				sBuilder.append("\n** Парний тиждень **\n");
			}
			
			sBuilder.append(schedule.get(iDay).day + "\n");
			ArrayList<LessonTest> classes = schedule.get(iDay).classes;
			for (int j = 0; j < classes.size(); j++) {
				sBuilder.append((j + 1) + ": " + classes.get(j).toString() + "\n");
			}
		}
		
		textFile.print(sBuilder.append("\n").toString());
	}
	
}
