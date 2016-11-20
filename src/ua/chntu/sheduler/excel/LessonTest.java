package ua.chntu.sheduler.excel;

public class LessonTest {
	
	private String name;
	private String teacher;
	private String hall;
	
	private final String DEEPENING_OF_STUDY =
			"День для поглибленого вивчення дисциплін та науково-дослідної роботи";
	
	public LessonTest() {
		this.name = "";
		this.teacher = "";
		this.hall = "";
	}
	
	public LessonTest(String lessonRepresentation) {
		if (lessonRepresentation.equalsIgnoreCase(DEEPENING_OF_STUDY) ||
				lessonRepresentation.isEmpty()) {
			this.name = lessonRepresentation;
			this.teacher = "";
			this.hall = "";
		} else getFieldsFromString(lessonRepresentation);
	}
	
	public LessonTest(String name, String teacher, String hall) {
		this.name = name;
		this.teacher = teacher;
		this.hall = hall;
	}
	
	private void getFieldsFromString(String lesson) {		
		//an example of a lesson:
		//Методи  досліджень (лб)     Доц. Скітер І.С. ауд. 4-54

		StringBuilder sBuilder = new StringBuilder(lesson);
		int endOfLessonName = sBuilder.indexOf(")");
		// find first not space char after ')'
		int startOfLecturerName;
		for (startOfLecturerName = endOfLessonName + 1;
				lesson.charAt(startOfLecturerName) == ' ';
				startOfLecturerName++);

		this.name = sBuilder.substring(0, endOfLessonName + 1);
		this.teacher = sBuilder.substring(startOfLecturerName, sBuilder.indexOf(" ауд") - 1);
		this.hall = sBuilder.substring(sBuilder.lastIndexOf(" ") + 1);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getHall() {
		return hall;
	}
	public void setHall(String hall) {
		this.hall = hall;
	}

	@Override
	public String toString() {
		return "[name=" + name + ", teacher=" + teacher + ", hall="
				+ hall + "]";
	}
	
}
