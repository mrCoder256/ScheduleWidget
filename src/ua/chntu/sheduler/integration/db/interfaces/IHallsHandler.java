package ua.chntu.sheduler.integration.db.interfaces;

import java.util.ArrayList;

import ua.chntu.sheduler.integration.db.entities.Hall;
import ua.chntu.sheduler.integration.db.entities.Lesson;

public interface IHallsHandler {

	int addHall();
	Hall getHallByIHall(int i_hall);
	Hall getHallByLocation(String location);
	ArrayList<Hall> getListOfHalls();
	ArrayList<Lesson> getScheduleInHall(int i_hall);
	
}
