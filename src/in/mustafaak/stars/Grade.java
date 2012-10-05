package in.mustafaak.stars;

import java.io.Serializable;

public class Grade implements Serializable {
	public String course;
	public String name;
	public String type;
	public String date;
	public String grade;
	public String comment;
	@Override
	public String toString() {
		return course + ", " + name + " - "  + grade;
	}
}
