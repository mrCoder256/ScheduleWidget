package ua.chntu.sheduler.dbhandlers;

import ua.chntu.sheduler.dbhandlers.entities.Lesson;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *
 * @author Vladyslav Shapoval
 *
 */
public class LessonsHandler {

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
	public int addLesson(String name, int i_teacher, int i_hall, String day) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("i_teacher", i_teacher);
		values.put("i_hall", i_hall);
		values.put("day", day);

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

		String query = "SELECT i_lesson, name, i_teacher, i_hall ";
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
		return lesson;
	}

}
