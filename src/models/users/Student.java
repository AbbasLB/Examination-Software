package models.users;

import java.util.HashSet;
import java.util.Set;

import models.Course;
import models.DBModel;
import models.Exam;
import models.Grade;

public class Student extends User {
	Set<Grade> grades;

	public Student(int id, String name, char gender, String email, String pass, Set<Course> courses,
			Set<Grade> grades) {
		super(id, name, gender, email, pass, courses);
		this.grades = grades;
	}

	public Student(int id, String name, char gender, String email, String pass, Set<Course> courses) {
		super(id, name, gender, email, pass, courses);
		this.grades = new HashSet<Grade>();
	}

	public Student(int id, String name, char gender, String email, String pass) {
		super(id, name, gender, email, pass);
		this.grades = new HashSet<Grade>();
	}

	public Student(String name, char gender, String email, String pass) {
		super(0, name, gender, email, pass);
		this.grades = new HashSet<Grade>();
	}

	@Override
	public UserType getUserType() {
		return UserType.STUDENT;
	}

	public Set<Grade> getGrades() {
		return grades;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}

	public boolean enroll(Course course) {
		if (DBModel.getInstance().enrollStudent(this, course)) {
			courses.add(course);
			course.getStudents().add(this);
			return true;
		}
		return false;
	}

	public boolean unEnroll(Course course) {
		if (courses.contains(course) && DBModel.getInstance().unEnrollStudent(this, course)) {
			courses.remove(course);
			course.getStudents().remove(this);
			return true;
		}
		return false;
	}

	public int getNumOfExamsDone() {
		int num = 0;
		for (Course c : courses)
			for (Exam e : c.getExams())
				if (e.checkIfDone(this))
					num++;
		return num;
	}

	public Set<Course> getAvailableCourses() {
		return DBModel.getInstance().getAvailableCourses(this);
	}
}
