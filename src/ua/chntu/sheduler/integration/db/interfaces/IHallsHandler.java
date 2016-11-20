package ua.chntu.sheduler.integration.db.interfaces;

import java.util.ArrayList;

import ua.chntu.sheduler.integration.db.entities.Hall;

public interface IHallsHandler {

	int addHall(String location);
	Hall getHallByIHall(int i_hall);
	Hall getHallByLocation(String location);
	ArrayList<Hall> getListOfHalls();
	
}
