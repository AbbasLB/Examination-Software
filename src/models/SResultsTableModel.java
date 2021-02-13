package models;

public class SResultsTableModel {
	String course, examTitle;
	float grade;
	int credits;

	public SResultsTableModel(String course, String examTitle, float grade, int credits) {
		this.course = course;
		this.examTitle = examTitle;
		this.grade = grade;
		this.credits = credits;
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

	public int getCredits() {
		return credits;
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

	public void setCredits(int credits) {
		this.credits = credits;
	}

}
