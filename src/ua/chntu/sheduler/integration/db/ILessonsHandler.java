package ua.chntu.sheduler.integration.db;

import ua.chntu.sheduler.integration.db.entities.Lesson;

interface ILessonsHandler {

	int addLesson(String name, int i_teacher, int i_hall, String day);
	void addGroupToLesson(int i_group, int i_lesson);
	Lesson getLessonByILesson(int i_lesson);
	
}
