package ua.chntu.sheduler;

import java.util.ArrayList;

public class Schedule {
	
	private String date;
	private ArrayList<Classes> classes;
	
	public Schedule(String date, ArrayList<Classes> classes) {
		this.date = date;
		this.classes = classes;
	}

	public String getDate() {
		return date;
	}

	public ArrayList<Classes> getClasses() {
		return classes;
	}

}
