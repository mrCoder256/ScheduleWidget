package ua.chntu.sheduler.integration.db.handlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * @author Vladyslav Shapoval
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	private static final String DB_NAME = "scheduleWidget.db";
	private static final int DB_VERSION = 1;
	private static DatabaseHandler databaseHandler;
	private static TeachersHandler teachersHandler;
	private static GroupsHandler groupsHandler;
	private static HallsHandler hallsHandler;
	private static LessonsHandler lessonsHandler;

	private DatabaseHandler(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	/**
	 * Returns instance of DatabaseHandler
	 *
	 * @param context
	 * @return instance of DatabaseHandler(singleton)
	 */
	public static DatabaseHandler getInstance(Context context) {
		if (databaseHandler == null) {
			databaseHandler = new DatabaseHandler(context);
		}
		return databaseHandler;
	}

	/**
	 * Returns instance of TeachersHanlder
	 *
	 * @return
	 */
	public TeachersHandler getTeachersHandler() {
		if (teachersHandler == null) {
			teachersHandler = new TeachersHandler(this);
		}
		return teachersHandler;
	}

	/**
	 * Returns instance of GroupsHandler
	 *
	 * @return
	 */
	public GroupsHandler getGroupsHandler() {
		if (groupsHandler == null) {
			groupsHandler = new GroupsHandler(this);
		}
		return groupsHandler;
	}

	/**
	 * Returns instance of HallsHanlder
	 *
	 * @return
	 */
	public HallsHandler getHallsHandler() {
		if (hallsHandler == null) {
			hallsHandler = new HallsHandler(this);
		}
		return hallsHandler;
	}

	/**
	 * Returns instance of LessonsHandler
	 *
	 * @return
	 */
	public LessonsHandler getLessonsHandler() {
		if (lessonsHandler == null) {
			lessonsHandler = new LessonsHandler(this);
		}
		return lessonsHandler;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTeachersTable(db);
		createHallsTable(db);
		createGroupsTable(db);
		createLessonsTable(db);
		createLessonGroupsTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	/**
	 * Creates Teachers table
	 *
	 * @param db
	 */
	private void createTeachersTable(SQLiteDatabase db) {
		String query = "CREATE TABLE Teachers (";
		query += "i_teacher integer PRIMARY KEY AUTOINCREMENT,";
		query += "name text);";
		db.execSQL(query);
	}

	/**
	 * Creates Halls table
	 *
	 * @param db
	 */
	private void createHallsTable(SQLiteDatabase db) {
		String query = "CREATE TABLE Halls (";
		query += "i_hall integer PRIMARY KEY AUTOINCREMENT,";
		query += "location text);";
		db.execSQL(query);
	}

	/**
	 * Creates Groups table
	 *
	 * @param db
	 */
	private void createGroupsTable(SQLiteDatabase db) {
		String query = "CREATE TABLE Groups (";
		query += "i_group integer PRIMARY KEY AUTOINCREMENT,";
		query += "name text);";
		db.execSQL(query);
	}

	/**
	 * Creates Lessons table
	 *
	 * @param db
	 */
	private void createLessonsTable(SQLiteDatabase db) {
		String query = "CREATE TABLE Lessons (";
		query += "i_lesson integer PRIMARY KEY AUTOINCREMENT,";
		query += "name text,";
		query += "i_teacher integer,";
		query += "i_hall integer,";
		query += "day text,";
		query += "FOREIGN KEY(i_teacher) REFERENCES Teachers(i_teacher),";
		query += "FOREIGN KEY(i_hall) REFERENCES Halls(i_hall));";
		db.execSQL(query);
	}

	/**
	 * Creates LessonGroups table
	 *
	 * @param db
	 */
	private void createLessonGroupsTable(SQLiteDatabase db) {
		String query = "CREATE TABLE LessonGroups (";
		query += "i_group integer,";
		query += "i_lesson integer,";
		query += "FOREIGN KEY(i_group) REFERENCES Groups(i_group),";
		query += "FOREIGN KEY(i_lesson) REFERENCES Lessons(i_lesson));";
		db.execSQL(query);
	}

}
