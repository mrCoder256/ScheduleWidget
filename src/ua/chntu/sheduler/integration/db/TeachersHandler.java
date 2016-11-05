package ua.chntu.sheduler.integration.db;

import ua.chntu.sheduler.integration.db.entities.Teacher;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *
 * @author Vladyslav Shapoval
 *
 */
public class TeachersHandler {

	private DatabaseHandler databaseHanlder;

	public TeachersHandler(DatabaseHandler databaseHandler) {
		this.databaseHanlder = databaseHandler;
	}

	/**
	 * Adds a new teacher
	 *
	 * @param name
	 * @return returns id of the created entity (i_teacher)
	 */
	public int addTeacher(String name) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);

		Long i_teacher = db.insert("Teachers", null, values);
		db.close();
		return i_teacher.intValue();
	}

	/**
	 * Returns Teacher object by given i_teacher
	 *
	 * @param i_teacher
	 * @return
	 */
	public Teacher getTeacherByITeacher(int i_teacher) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();

		String query = "SELECT i_teacher, name FROM Teachers WHERE i_teacher = ?";
		Cursor cursor = db.rawQuery(query,
				new String[] { String.valueOf(i_teacher) });
		if (cursor != null) {
			cursor.moveToFirst();
		} else {
			return null;
		}
		Teacher teacher = new Teacher();
		teacher.setITeacher(Integer.parseInt(cursor.getString(0)));
		teacher.setName(cursor.getString(1));
		return teacher;
	}
}
