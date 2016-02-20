package ua.chntu.sheduler;

public class Classes {
	
	private String lectureName1;
	private String lecturerAndHall1;
	private String lectureName2;
	private String lecturerAndHall2;
	private int type;

	public static final int LECTURE = 0;
	public static final int PRAXIS = 1;
	
	public Classes(String lectureName1, String lecturerAndHall1) {
		this.lectureName1 = lectureName1;
		this.lecturerAndHall1 = lecturerAndHall1;
		this.type = LECTURE;
	}
	
	public Classes(String lectureName1, String lecturerAndHall1,
			String lectureName2, String lecturerAndHall2) {
		this.lectureName1 = lectureName1;
		this.lecturerAndHall1 = lecturerAndHall1;
		this.lectureName2 = lectureName2;
		this.lecturerAndHall2 = lecturerAndHall2;
		this.type = PRAXIS;
	}

	public String getLectureName1() {
		return lectureName1;
	}
	
	public String getLecturerAndHall1() {
		return lecturerAndHall1;
	}
	
	public String getLectureName2() {
		return lectureName2;
	}
	
	public String getLecturerAndHall2() {
		return lecturerAndHall2;
	}
	
	public int getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "Classes ["
				+ "lectureName1=" + (lectureName1 == null 
					? "null" : lectureName1)
				+ ", lecturerAndHall1="	+ (lecturerAndHall1 == null 
					? "null" : lecturerAndHall1)
				+ ", lectureName2=" + (lectureName2 == null 
					? "null" : lectureName2)
				+ ", lecturerAndHall2=" + (lecturerAndHall2 == null 
					? "null" : lecturerAndHall2)
				+ ", type=" + type + "]";
	}

}
