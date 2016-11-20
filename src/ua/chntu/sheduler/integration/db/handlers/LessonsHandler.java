package ua.chntu.sheduler.integration.db.handlers;

import java.util.ArrayList;

import ua.chntu.sheduler.integration.db.entities.Lesson;
import ua.chntu.sheduler.integration.db.interfaces.ILessonsHandler;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *
 * @author Vladyslav Shapoval
 *
 */
public class LessonsHandler implements ILessonsHandler {

	private DatabaseHandler databaseHanlder;

	public LessonsHandler(DatabaseHandler databaseHandler) {
		this.databaseHanlder = databaseHandler;
	}

	/**
	 * Adds a group to a lesson
	 *
	 * @param i_group
	 * @param i_lesson
	 */
	public void addGroupToLesson(int i_group, int i_lesson) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("i_group", i_group);
		values.put("i_lesson", i_lesson);

		db.insert("LessonGroups", null, values);
		db.close();
	}

	/**
	 * Adds a new lesson
	 *
	 * @param name
	 * @param i_teacher
	 * @param i_hall
	 * @param day
	 * @return returns id of the created entity (i_lesson)
	 */
	public int addLesson(String name, int i_teacher, int i_hall, String day, int number) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("i_teacher", i_teacher);
		values.put("i_hall", i_hall);
		values.put("day", day);
		values.put("number", number);
		
		Long i_lesson = db.insert("Lessons", null, values);
		db.close();

		return i_lesson.intValue();
	}

	/**
	 * Returns Lesson object by given i_lesson
	 *
	 * @param i_lesson
	 * @return
	 */
	public Lesson getLessonByILesson(int i_lesson) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();

		String query = "SELECT i_lesson, name, i_teacher, i_hall, number";
		query += "FROM Lessons WHERE i_lesson = ?";
		Cursor cursor = db.rawQuery(query,
				new String[] { String.valueOf(i_lesson) });
		if (cursor != null) {
			cursor.moveToFirst();
		} else {
			return null;
		}
		Lesson lesson = new Lesson();
		lesson.setILesson(Integer.parseInt(cursor.getString(0)));
		lesson.setName(cursor.getString(1));
		lesson.setITeacher(Integer.parseInt(cursor.getString(2)));
		lesson.setIHall(Integer.parseInt(cursor.getString(3)));
		lesson.setNumber(Integer.parseInt(cursor.getString(4)));
		return lesson;
	}

	/**
	 * Returns list of lessons by the given i_group
	 */
	@Override
	public ArrayList<Lesson> getScheduleOfGroup(int i_group) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();

		ArrayList<Lesson> lessonsList = new ArrayList<Lesson>();
		String query = "SELECT i_lesson, name, i_teacher, i_hall, number ";
		query += "FROM Lessons ";
		query += "LEFT JOIN LessonGroups ";
		query += "ON LessonGroups.i_lesson = Lessons.i_lesson ";
		query += "WHERE LessonGroups.i_group = ?";
		Cursor cursor = db.rawQuery(query,
				new String[] { String.valueOf(i_group) });
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					Lesson lesson = new Lesson();
					lesson.setILesson(Integer.parseInt(cursor.getString(0)));
					lesson.setName(cursor.getString(1));
					lesson.setITeacher(Integer.parseInt(cursor.getString(2)));
					lesson.setIHall(Integer.parseInt(cursor.getString(3)));
					lesson.setNumber(Integer.parseInt(cursor.getString(4)));
					lessonsList.add(lesson);
				} while (cursor.moveToNext());
			}
		}
		return lessonsList;
	}

	/**
	 * Returns list of lessons by the given stream name
	 */
	@Override
	public ArrayList<Lesson> getScheduleOfCourse(String streamName) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();

		ArrayList<Lesson> lessonsList = new ArrayList<Lesson>();
		String query = "SELECT i_lesson, name, i_teacher, i_hall, number ";
		query += "FROM Lessons ";
		query += "LEFT JOIN LessonGroups ";
		query += "ON LessonGroups.i_lesson = Lessons.i_lesson ";
		query += "LEFT JOIN Groups ";
		query += "ON Groups.i_group = LessonGroups.i_group ";
		query += "WHERE Groups.stream = ?";
		Cursor cursor = db.rawQuery(query,
				new String[] { String.valueOf(streamName) });
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					Lesson lesson = new Lesson();
					lesson.setILesson(Integer.parseInt(cursor.getString(0)));
					lesson.setName(cursor.getString(1));
					lesson.setITeacher(Integer.parseInt(cursor.getString(2)));
					lesson.setIHall(Integer.parseInt(cursor.getString(3)));
					lesson.setNumber(Integer.parseInt(cursor.getString(4)));
					lessonsList.add(lesson);
				} while (cursor.moveToNext());
			}
		}
		return lessonsList;
	}

	/**
	 * Returns list of lessons by the given i_hall
	 */
	@Override
	public ArrayList<Lesson> getScheduleInHall(int i_hall) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();

		ArrayList<Lesson> lessonsList = new ArrayList<Lesson>();
		String query = "SELECT i_lesson, name, i_teacher, i_hall, number ";
		query += "FROM Lessons ";
		query += "WHERE Lessons.i_hall = ?";
		Cursor cursor = db.rawQuery(query,
				new String[] { String.valueOf(i_hall) });
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					Lesson lesson = new Lesson();
					lesson.setILesson(Integer.parseInt(cursor.getString(0)));
					lesson.setName(cursor.getString(1));
					lesson.setITeacher(Integer.parseInt(cursor.getString(2)));
					lesson.setIHall(Integer.parseInt(cursor.getString(3)));
					lesson.setNumber(Integer.parseInt(cursor.getString(4)));
					lessonsList.add(lesson);
				} while (cursor.moveToNext());
			}
		}
		return lessonsList;
	}

	/**
	 * Returns list of lessons by the given i_teacher
	 */
	@Override
	public ArrayList<Lesson> getScheduleOfTeacher(int i_teacher) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();

		ArrayList<Lesson> lessonsList = new ArrayList<Lesson>();
		String query = "SELECT i_lesson, name, i_teacher, i_hall, number ";
		query += "FROM Lessons ";
		query += "WHERE Lessons.i_teacher = ?";
		Cursor cursor = db.rawQuery(query,
				new String[] { String.valueOf(i_teacher) });
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					Lesson lesson = new Lesson();
					lesson.setILesson(Integer.parseInt(cursor.getString(0)));
					lesson.setName(cursor.getString(1));
					lesson.setITeacher(Integer.parseInt(cursor.getString(2)));
					lesson.setIHall(Integer.parseInt(cursor.getString(3)));
					lesson.setNumber(Integer.parseInt(cursor.getString(4)));
					lessonsList.add(lesson);
				} while (cursor.moveToNext());
			}
		}
		return lessonsList;
	}

}
