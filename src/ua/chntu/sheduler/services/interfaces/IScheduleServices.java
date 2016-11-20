package ua.chntu.sheduler.services.interfaces;

import java.util.ArrayList;

import ua.chntu.sheduler.integration.db.entities.Group;
import ua.chntu.sheduler.integration.db.entities.Hall;
import ua.chntu.sheduler.integration.db.entities.Lesson;
import ua.chntu.sheduler.integration.db.entities.Teacher;

public interface IScheduleServices {
	
	public boolean importScheduleFromExcel(String excelFileName);
	public ArrayList<Group> getListOfGroups();
	public ArrayList<Teacher> getListOfTeachers();
	public ArrayList<Hall> getListOfHalls();
	public ArrayList<Lesson> getScheduleOfGroup(int groupId);
	public ArrayList<Lesson> getScheduleOfGroupStream(String groupStreamName);
	public ArrayList<Lesson> getScheduleOfTeacher(int teacherId);
	public ArrayList<Lesson> getScheduleInHall(int hallId);

}
