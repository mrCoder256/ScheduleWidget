package ua.chntu.sheduler.integration.db;

public interface IDatabaseHandler {
	
	public DatabaseHandler getInstance();
	public TeachersHandler getTeachersHandler();
	public GroupsHandler getGroupsHandler();
	public HallsHandler getHallsHandler();
	public LessonsHandler getLessonsHandler();

}
