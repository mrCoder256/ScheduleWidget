package ua.chntu.sheduler.integration.db.entities;

/**
 *
 * @author Vladyslav Shapoval
 *
 */
public class Group {

	private int i_group;
	private String name;
	private String stream;

	public int getIGroup() {
		return i_group;
	}

	public void setIGroup(int i_group) {
		this.i_group = i_group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

}
