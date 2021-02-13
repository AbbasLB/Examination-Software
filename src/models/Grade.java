package models;

import models.users.Student;

public class Grade {
	private float grade;
	private Student std;
	private Exam exam;

	public Grade(float grade, Student std, Exam exam) {
		this.grade = grade;
		this.std = std;
		this.exam = exam;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public Student getStd() {
		return std;
	}

	public void setStd(Student std) {
		this.std = std;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

}
