package ua.chntu.sheduler.integration.db.entities;

/**
 *
 * @author Vladyslav Shapoval
 *
 */
public class Lesson {

	private int i_lesson;
	private String name;
	private int i_teacher;
	private int i_hall;

	public int getILesson() {
		return i_lesson;
	}

	public void setILesson(int i_lesson) {
		this.i_lesson = i_lesson;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getITeacher() {
		return i_teacher;
	}

	public void setITeacher(int i_teacher) {
		this.i_teacher = i_teacher;
	}

	public int getIHall() {
		return i_hall;
	}

	public void setIHall(int i_hall) {
		this.i_hall = i_hall;
	}

}
