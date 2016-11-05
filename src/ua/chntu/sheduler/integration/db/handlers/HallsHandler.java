package ua.chntu.sheduler.integration.db.handlers;

import java.util.ArrayList;

import ua.chntu.sheduler.integration.db.entities.Hall;
import ua.chntu.sheduler.integration.db.entities.Lesson;
import ua.chntu.sheduler.integration.db.interfaces.IHallsHandler;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *
 * @author Vladyslav Shapoval
 *
 */
public class HallsHandler implements IHallsHandler {

	private DatabaseHandler databaseHanlder;

	public HallsHandler(DatabaseHandler databaseHandler) {
		this.databaseHanlder = databaseHandler;
	}

	/**
	 * Add a new hall
	 *
	 * @param location
	 * @return returns id of the created entity (i_hall)
	 */
	public int addHall(String location) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("location", location);

		Long i_hall = db.insert("Halls", null, values);
		db.close();

		return i_hall.intValue();
	}

	/**
	 * Returns Hall object by give i_hall
	 *
	 * @param i_hall
	 * @return
	 */
	public Hall getHallByIHall(int i_hall) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();

		String query = "SELECT i_hall, location FROM Halls WHERE i_hall = ?";
		Cursor cursor = db.rawQuery(query,
				new String[] { String.valueOf(i_hall) });
		if (cursor != null) {
			cursor.moveToFirst();
		} else {
			return null;
		}
		Hall hall = new Hall();
		hall.setIHall(Integer.parseInt(cursor.getString(0)));
		hall.setLocation(cursor.getString(1));
		return hall;
	}

	/**
	 * Returns Hall object by given location
	 *
	 * @param location
	 * @return
	 */
	public Hall getHallByLocation(String location) {
		SQLiteDatabase db = this.databaseHanlder.getWritableDatabase();

		String query = "SELECT i_hall, location FROM Halls WHERE location = ?";
		Cursor cursor = db.rawQuery(query, new String[] { location });
		if (cursor != null) {
			cursor.moveToFirst();
		} else {
			return null;
		}
		Hall hall = new Hall();
		hall.setIHall(Integer.parseInt(cursor.getString(0)));
		hall.setLocation(cursor.getString(1));
		return hall;
	}

	@Override
	public int addHall() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Hall> getListOfHalls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Lesson> getScheduleInHall(int i_hall) {
		// TODO Auto-generated method stub
		return null;
	}
}
