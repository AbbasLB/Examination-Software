package models;

public class TResultsTableModel {
	String name, email, course, examTitle;
	float grade;

	public TResultsTableModel(String name, String email, String course, String examTitle, float grade) {
		this.name = name;
		this.email = email;
		this.course = course;
		this.examTitle = examTitle;
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getCourse() {
		return course;
	}

	public String getExamTitle() {
		return examTitle;
	}

	public float getGrade() {
		return grade;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

}
