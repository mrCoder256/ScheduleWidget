package ua.chntu.sheduler.integration.db.interfaces;

import java.util.ArrayList;

import ua.chntu.sheduler.integration.db.entities.Group;
import ua.chntu.sheduler.integration.db.entities.Lesson;

public interface IGroupsHandler {
	
	int addGroup(String name);
	Group getGroupByIGroup(int i_group);
	Group getGroupByName(String name);
	ArrayList<Group> getListOfGroups();
	ArrayList<Lesson> getScheduleOfGroup(int i_group);
	ArrayList<Lesson> getScheduleOfCourse(String streamName);

}
