package ua.chntu.sheduler.integration.db.interfaces;

import java.util.ArrayList;

import ua.chntu.sheduler.integration.db.entities.Lesson;

public interface ILessonsHandler {

	int addLesson(String name, int i_teacher, int i_hall, String day, int number);
	void addGroupToLesson(int i_group, int i_lesson);
	Lesson getLessonByILesson(int i_lesson);
	ArrayList<Lesson> getScheduleOfGroup(int i_group);
	ArrayList<Lesson> getScheduleOfCourse(String streamName);
	ArrayList<Lesson> getScheduleInHall(int i_hall);
	ArrayList<Lesson> getScheduleOfTeacher(int i_teacher);

}
