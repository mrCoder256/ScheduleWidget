package ua.chntu.sheduler.integration.db;

import java.util.ArrayList;

import ua.chntu.sheduler.integration.db.entities.Lesson;
import ua.chntu.sheduler.integration.db.entities.Teacher;

interface ITeachersHandler {
	
	int addTeacher(String name);
	Teacher getTeacherByITeacher(int i_teacher);
	ArrayList<Teacher> getListOfTeachers();
	ArrayList<Lesson> getScheduleOfTeacher(int i_teacher);

}