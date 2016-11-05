package ua.chntu.sheduler.integration.db.handlers;

import java.util.ArrayList;

import ua.chntu.sheduler.integration.db.entities.Group;
import ua.chntu.sheduler.integration.db.entities.Lesson;
import ua.chntu.sheduler.integration.db.interfaces.IGroupsHandler;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *
 * @author Vladyslav Shapoval
 *
 */
public class GroupsHandler implements IGroupsHandler {

	private DatabaseHandler databaseHanlder;

	public GroupsHandler(DatabaseHandler databaseHandler) {
		this.databaseHanlder = databaseHandler;
	}

	/**
	 * Adds a new group
	 *
	 * @param name
	 * @return returns id of the created entity (i_group)
	 */
	public int addGroup(String name) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);

		Long i_group = db.insert("Group", null, values);
		db.close();

		return i_group.intValue();
	}

	/**
	 * Returns Group object by given i_group
	 *
	 * @param i_group
	 * @return
	 */
	public Group getGroupByIGroup(int i_group) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();

		String query = "SELECT i_group, name FROM Groups WHERE i_group = ?";
		Cursor cursor = db.rawQuery(query,
				new String[] { String.valueOf(i_group) });
		if (cursor != null) {
			cursor.moveToFirst();
		} else {
			return null;
		}
		Group group = new Group();
		group.setIGroup(Integer.parseInt(cursor.getString(0)));
		group.setName(cursor.getString(1));
		return group;
	}

	/**
	 * Returns Group object by given group name
	 *
	 * @param name
	 * @return
	 */
	public Group getGroupByName(String name) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();

		String query = "SELECT i_group, name FROM Groups WHERE name = ?";
		Cursor cursor = db.rawQuery(query, new String[] { name });
		if (cursor != null) {
			cursor.moveToFirst();
		} else {
			return null;
		}
		Group group = new Group();
		group.setIGroup(Integer.parseInt(cursor.getString(0)));
		group.setName(cursor.getString(1));
		return group;
	}

	@Override
	public ArrayList<Group> getListOfGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Lesson> getScheduleOfGroup(int i_group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Lesson> getScheduleOfCourse(String streamName) {
		// TODO Auto-generated method stub
		return null;
	}
}
