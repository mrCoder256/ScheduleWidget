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
//		context.deleteDatabase(DB_NAME);
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
		
		// test data
		initDB();
	}
	
	public void initDB() {
		GroupsHandler gh = this.getGroupsHandler();
		LessonsHandler lh = this.getLessonsHandler();
		HallsHandler hh = this.getHallsHandler();
		TeachersHandler th = this.getTeachersHandler();

		int i_group_mk = gh.addGroup("МК-111", "6 курс");
		int i_teacher_semend  = th.addTeacher("Семендяй С.М");
		int i_hall_63 = hh.addHall("4-63");
		int i_lesson = lh.addLesson("Методи  аналізу  КМ (лек)", i_teacher_semend, i_hall_63, "ПН 1",  4);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		
		int i_teacher_svet = th.addTeacher("Ст.викл. Светенок Л.К");
		int i_hall_444 = hh.addHall("І-444");
		i_lesson = lh.addLesson("Групова динаміка (лек)", i_teacher_svet, i_hall_444, "ПН 1", 5);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		i_lesson = lh.addLesson("Групова динаміка (пр)", i_teacher_svet, i_hall_444, "ПН 1", 6);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		int i_hall_92 = hh.addHall("4-92");
		i_lesson = lh.addLesson("Методи  аналізу  КМ (лб)", i_teacher_semend, i_hall_92, "СР 1", 4);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		int i_hall_64 = hh.addHall("4-64");
		int i_teacher_yur = th.addTeacher("Юрченко Д.Ю");
		i_lesson = lh.addLesson("Програмування моб.пр (лб)", i_teacher_yur, i_hall_64, "СР 1", 5);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		int i_teacher_rahozin = th.addTeacher("Доц.Рагозін Д.В");
		i_lesson = lh.addLesson("Новітні комп.та ІТ техн (лек)", i_teacher_rahozin, i_hall_63, "ЧТ 1", 2);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		i_lesson = lh.addLesson("Новітні комп.та ІТ техн (лб)", i_teacher_rahozin, i_hall_63, "ЧТ 1", 3);
		lh.addGroupToLesson(i_group_mk, i_lesson);

		int i_teacher_rad = th.addTeacher("Доц.Радченко А.О");
		int i_hall_95 = hh.addHall("4-95");
		i_lesson = lh.addLesson("Програмування моб.пр (лек)", i_teacher_rad, i_hall_95, "ЧТ 1", 4);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		// Парный МК-111
		i_lesson = lh.addLesson("Програмування моб.пр (лек)", i_teacher_rad, i_hall_95, "ПН 2", 3);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		i_lesson = lh.addLesson("Методи  аналізу  КМ (лб)", i_teacher_semend, i_hall_63, "ПН 2",  4);
		lh.addGroupToLesson(i_group_mk, i_lesson);
	
		int i_hall_411 = hh.addHall("І-411");
		i_lesson = lh.addLesson("Групова динаміка (пр)", i_teacher_svet, i_hall_411, "ПН 2", 5);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		int i_teacher_skit = th.addTeacher("Доц. Скітер І.С");
		i_lesson = lh.addLesson("Методи  досліджень (лб)", i_teacher_skit, i_hall_63, "ВТ 2", 2);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		i_lesson = lh.addLesson("Новітні комп.та ІТ техн (лб)", i_teacher_rahozin, i_hall_63, "ВТ 2", 3);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		i_lesson = lh.addLesson("Новітні комп.та ІТ техн (лек)", i_teacher_rahozin, i_hall_95, "ВТ 2", 4);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		i_lesson = lh.addLesson("Методи  аналізу  КМ (лек)", i_teacher_semend, i_hall_63, "ЧТ 2", 4);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		int i_teacher_kaz = th.addTeacher("Проф.Казимир В.В");
		int i_hall_65 = hh.addHall("4-65");
		i_lesson = lh.addLesson("Методи  досліджень (лек)", i_teacher_kaz, i_hall_65, "ЧТ 2", 5);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		i_lesson = lh.addLesson("Програмування моб.пр (лб)", i_teacher_yur, i_hall_64, "ЧТ 2", 6);
		lh.addGroupToLesson(i_group_mk, i_lesson);
		
		
	//MP-111
		
		int i_group_mp = gh.addGroup("МП-111", "6 курс");
		int i_hall_54 = hh.addHall("4-54");
		i_lesson = lh.addLesson("Методи  досліджень (лб)", i_teacher_skit, i_hall_54, "ПН 1", 3);
		lh.addGroupToLesson(i_group_mp, i_lesson);
		
		i_lesson = lh.addLesson("Методи  аналізу  КМ (лек)", i_teacher_semend, i_hall_63, "ПН 1", 4);
		lh.addGroupToLesson(i_group_mp, i_lesson);
		
		i_lesson = lh.addLesson("Групова динаміка (лек)", i_teacher_svet, i_hall_444, "ПН 1", 5);
		lh.addGroupToLesson(i_group_mp, i_lesson);
		
		i_lesson = lh.addLesson("Методи  аналізу  КМ (лб)", i_teacher_semend, i_hall_63, "ВТ 1", 4);
		lh.addGroupToLesson(i_group_mp, i_lesson);
		
		i_lesson = lh.addLesson("Програмування моб.пр (лб)", i_teacher_rad, i_hall_64, "ВТ 1", 5);
		lh.addGroupToLesson(i_group_mp, i_lesson);
		
		i_lesson = lh.addLesson("Новітні комп.та ІТ техн (лб)", i_teacher_rahozin, i_hall_63, "ЧТ 1", 1);
		lh.addGroupToLesson(i_group_mp, i_lesson);
		
		i_lesson = lh.addLesson("Новітні комп.та ІТ техн (лек)", i_teacher_rahozin, i_hall_95, "ЧТ 1", 2);
		lh.addGroupToLesson(i_group_mp, i_lesson);
		
		i_lesson = lh.addLesson("Групова динаміка (пр)", i_teacher_svet, i_hall_411, "ЧТ 1", 3);
		lh.addGroupToLesson(i_group_mp, i_lesson);
		
		i_lesson = lh.addLesson("Програмування моб.пр (лек)", i_teacher_rad, i_hall_95, "ЧТ 1", 4);
		lh.addGroupToLesson(i_group_mp, i_lesson);

		int i_hall_73 = hh.addHall("4-73");
		i_lesson = lh.addLesson("Програмування моб.пр (лб)", i_teacher_rad, i_hall_64, "ПН 2", 2);
		lh.addGroupToLesson(i_group_mp, i_lesson);
		
		i_lesson = lh.addLesson("Програмування моб.пр (лек)", i_teacher_rad, i_hall_95, "ПН 2", 3);
		lh.addGroupToLesson(i_group_mp, i_lesson);
		
		i_lesson = lh.addLesson("Новітні комп.та ІТ техн (лек)", i_teacher_rahozin, i_hall_95, "ВТ 2", 4);
		lh.addGroupToLesson(i_group_mp, i_lesson);
	
		i_lesson = lh.addLesson("Групова динаміка (пр)", i_teacher_svet, i_hall_411, "ВТ 2", 5);
		lh.addGroupToLesson(i_group_mp, i_lesson);
	
		i_lesson = lh.addLesson("Методи  аналізу  КМ (лб)", i_teacher_semend, i_hall_73, "ЧТ 2", 3);
		lh.addGroupToLesson(i_group_mp, i_lesson);
		
		i_lesson = lh.addLesson("Методи  аналізу  КМ (лек)", i_teacher_semend, i_hall_63, "ЧТ 2", 4);
		lh.addGroupToLesson(i_group_mp, i_lesson);
		
		i_lesson = lh.addLesson("Методи  досліджень (лек)", i_teacher_kaz, i_hall_65, "ЧТ 2", 5);
		lh.addGroupToLesson(i_group_mp, i_lesson);
	
		i_lesson = lh.addLesson("Новітні комп.та ІТ техн (лб)", i_teacher_rahozin, i_hall_63, "ЧТ 2", 6);
		lh.addGroupToLesson(i_group_mp, i_lesson);
	
		// МС-111
		int i_group_ms = gh.addGroup("МС-111", "6 курс");
		i_lesson = lh.addLesson("Методи  досліджень (лб)", i_teacher_skit, i_hall_54, "ПН 1", 3);
		lh.addGroupToLesson(i_group_ms, i_lesson);
		
		int i_teacher_rog = th.addTeacher("Ст.викл. Роговенко А.І");
		i_lesson = lh.addLesson("Нов.архіт. та ЗПМС (лб)", i_teacher_rog, i_hall_92, "ПН 1", 4);
		lh.addGroupToLesson(i_group_ms, i_lesson);
		
		i_lesson = lh.addLesson("Групова динаміка (лек)", i_teacher_svet, i_hall_411, "ПН 1", 5);
		lh.addGroupToLesson(i_group_ms, i_lesson);
		
		int i_teacher_mosh = th.addTeacher("Проф.Мошель В.М");
		int i_hall_93 = hh.addHall("4-93");
		i_lesson = lh.addLesson("Комп.сист.вим.та контр (лек)", i_teacher_mosh, i_hall_93, "ВТ 1", 3);
		lh.addGroupToLesson(i_group_ms, i_lesson);
		
		i_lesson = lh.addLesson("Комп.сист.вим.та контр (лб)", i_teacher_mosh, i_hall_93, "ВТ 1", 4);
		lh.addGroupToLesson(i_group_ms, i_lesson);
	
		i_lesson = lh.addLesson("Нов.архіт. та ЗПМС (лек)", i_teacher_rog, i_hall_95, "ВТ 1", 5);
		lh.addGroupToLesson(i_group_ms, i_lesson);
		
		i_lesson = lh.addLesson("Новітні комп.та ІТ техн (лб)", i_teacher_rahozin, i_hall_63, "ЧТ 1", 1);
		lh.addGroupToLesson(i_group_ms, i_lesson);
		
		i_lesson = lh.addLesson("Новітні комп.та ІТ техн (лек)", i_teacher_rahozin, i_hall_63, "ЧТ 1", 2);
		lh.addGroupToLesson(i_group_ms, i_lesson);

		i_lesson = lh.addLesson("Групова динаміка (пр)", i_teacher_svet, i_hall_411, "ЧТ 1", 3);
		lh.addGroupToLesson(i_group_ms, i_lesson);
	
		i_lesson = lh.addLesson("Новітні комп.та ІТ техн (лек)", i_teacher_rahozin, i_hall_95, "ВТ 2", 4);
		lh.addGroupToLesson(i_group_ms, i_lesson);
		
		i_lesson = lh.addLesson("Групова динаміка (пр)", i_teacher_svet, i_hall_411, "ВТ 2", 5);
		lh.addGroupToLesson(i_group_ms, i_lesson);
		
		i_lesson = lh.addLesson("Нов.архіт. та ЗПМС (лек)", i_teacher_rog, i_hall_95, "ВТ 2", 6);
		lh.addGroupToLesson(i_group_ms, i_lesson);
	
		i_lesson = lh.addLesson("Комп.сист.вим.та контр (лек)", i_teacher_mosh, i_hall_93, "СР 2", 3);
		lh.addGroupToLesson(i_group_ms, i_lesson);
		
		i_lesson = lh.addLesson("Комп.сист.вим.та контр (лб)", i_teacher_mosh, i_hall_93, "СР 2", 4);
		lh.addGroupToLesson(i_group_ms, i_lesson);
	
		i_lesson = lh.addLesson("Нов.архіт. та ЗПМС (лб)", i_teacher_rog, i_hall_92, "СР 2", 5);
		lh.addGroupToLesson(i_group_ms, i_lesson);
	
		i_lesson = lh.addLesson("Методи  досліджень (лек)", i_teacher_kaz, i_hall_65, "ЧТ 2", 5);
		lh.addGroupToLesson(i_group_ms, i_lesson);
		
		i_lesson = lh.addLesson("Новітні комп.та ІТ техн (лб)", i_teacher_rahozin, i_hall_63, "ЧТ 2", 6);
		lh.addGroupToLesson(i_group_ms, i_lesson);
		
		
		// MI-111
		int i_group_mi = gh.addGroup("МІ-111", "6 курс");
		int i_hall_51 = hh.addHall("4-51");
		int i_teacher_litv = th.addTeacher("Литвинов В.В");
		
		i_lesson = lh.addLesson("Логіка і форм.сист (лек)", i_teacher_litv, i_hall_51, "ПН 1", 3);
		lh.addGroupToLesson(i_group_mi, i_lesson);
		
		i_lesson = lh.addLesson("Comunikation (лек)", i_teacher_svet, i_hall_411, "ПН 1", 4);
		lh.addGroupToLesson(i_group_mi, i_lesson);
		
		int i_teacher_nitch = th.addTeacher("Нітченко А.Г");
		int i_hall_312 = hh.addHall("1-312");
		i_lesson = lh.addLesson("Інтелект.власн (лек)", i_teacher_nitch, i_hall_312, "СР 1", 2);
		lh.addGroupToLesson(i_group_mi, i_lesson);
		
		i_lesson = lh.addLesson("Інтелект.власн (лек)", i_teacher_nitch, i_hall_312, "СР 1", 3);
		lh.addGroupToLesson(i_group_mi, i_lesson);
	
		i_lesson = lh.addLesson("Моделюв.знань та ІАД (лек)", i_teacher_skit, i_hall_51, "СР 1", 4);
		lh.addGroupToLesson(i_group_mi, i_lesson);
	
		int i_teacher_trun = th.addTeacher("Доц. Трунова О.В");
		int i_hall_10 = hh.addHall("4-10");
		i_lesson = lh.addLesson("Додаткові розділи ДМ (лек)", i_teacher_trun, i_hall_10, "ЧТ 1", 3);
		lh.addGroupToLesson(i_group_mi, i_lesson);

		int i_teacher_stec = th.addTeacher("Проф. Стеценко І.В");
		i_lesson = lh.addLesson("Моделі і зас.пар.та РО (лек)", i_teacher_stec, i_hall_10, "ЧТ 1", 4);
		lh.addGroupToLesson(i_group_mi, i_lesson);
		
		i_lesson = lh.addLesson("Моделі і зас.пар.та РО (лек)", i_teacher_stec, i_hall_10, "ЧТ 1", 5);
		lh.addGroupToLesson(i_group_mi, i_lesson);
		
		i_lesson = lh.addLesson("Додаткові розділи ДМ (лек)", i_teacher_trun, i_hall_10, "ПН 2", 2);
		lh.addGroupToLesson(i_group_mi, i_lesson);
		
		i_lesson = lh.addLesson("Логіка і форм.сист (лек)", i_teacher_litv, i_hall_10, "ПН 2", 3);
		lh.addGroupToLesson(i_group_mi, i_lesson);
		
		i_lesson = lh.addLesson("Додаткові розділи ДМ (пр)", i_teacher_trun, i_hall_51, "СР 2", 2);
		lh.addGroupToLesson(i_group_mi, i_lesson);
	
		i_lesson = lh.addLesson("Моделі і зас.пар.та РО (пр)", i_teacher_stec, i_hall_51, "СР 2", 3);
		lh.addGroupToLesson(i_group_mi, i_lesson);
	
		int i_teacher_zhit = th.addTeacher("Ас. Житник О.Є");
		i_lesson = lh.addLesson("Моделі і зас.пар.та РО (лек)", i_teacher_zhit, i_hall_51, "СР 2", 4);
		lh.addGroupToLesson(i_group_mi, i_lesson);
		
		i_lesson = lh.addLesson("Моделі і зас.пар.та РО (пр)", i_teacher_zhit, i_hall_51, "СР 2", 5);
		lh.addGroupToLesson(i_group_mi, i_lesson);
	
		i_lesson = lh.addLesson("Моделюв.знань та ІАД (лек)", i_teacher_skit, i_hall_10, "ЧТ 2", 1);
		lh.addGroupToLesson(i_group_mi, i_lesson);
		
		int i_hall_328 = hh.addHall("1-328");
		i_lesson = lh.addLesson("Інтелект.власн (пр)", i_teacher_nitch, i_hall_328, "ЧТ 2", 2);
		lh.addGroupToLesson(i_group_mi, i_lesson);
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
		db.execSQL("DROP TABLE IF EXISTS Teachers");
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
		db.execSQL("DROP TABLE IF EXISTS Halls");
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
		db.execSQL("DROP TABLE IF EXISTS Groups");
		String query = "CREATE TABLE Groups (";
		query += "i_group integer PRIMARY KEY AUTOINCREMENT,";
		query += "name text,";
		query += "stream text);";
		db.execSQL(query);
	}

	/**
	 * Creates Lessons table
	 *
	 * @param db
	 */
	private void createLessonsTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS Lessons");
		String query = "CREATE TABLE Lessons (";
		query += "i_lesson integer PRIMARY KEY AUTOINCREMENT,";
		query += "name text,";
		query += "i_teacher integer,";
		query += "i_hall integer,";
		query += "day text,";
		query += "number integer, ";
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
		db.execSQL("DROP TABLE IF EXISTS LessonGroups");
		String query = "CREATE TABLE LessonGroups (";
		query += "i_group integer,";
		query += "i_lesson integer,";
		query += "FOREIGN KEY(i_group) REFERENCES Groups(i_group),";
		query += "FOREIGN KEY(i_lesson) REFERENCES Lessons(i_lesson));";
		db.execSQL(query);
	}

}
