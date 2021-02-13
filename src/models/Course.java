package models;

import java.util.HashSet;
import java.util.Set;

import models.users.Student;
import models.users.Teacher;

public class Course {
	private int id;
	private int credits;
	private Teacher teacher;
	private String name;
	private String desc;
	private Set<Exam> exams;
	private Set<Student> students;

	public Course(int id, int credits, String name, String desc, Teacher teacher, Set<Exam> exams,
			Set<Student> students) {
		this.id = id;
		this.credits = credits;
		this.name = name;
		this.desc = desc;
		this.teacher = teacher;
		this.exams = exams;
		this.students = students;
	}

	public Course(int id, int credits, String name, String desc, Teacher teacher) {
		this.id = id;
		this.credits = credits;
		this.name = name;
		this.desc = desc;
		this.teacher = teacher;
		this.exams = new HashSet<Exam>();
		this.students = new HashSet<Student>();
	}

	public Course(int credits, String name, String desc, Teacher teacher) {
		this.credits = credits;
		this.name = name;
		this.desc = desc;
		this.teacher = teacher;
		this.exams = new HashSet<Exam>();
		this.students = new HashSet<Student>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Set<Exam> getExams() {
		return exams;
	}

	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public void initializeExamsFromDB() {
		exams = DBModel.getInstance().getCourseExams(this);
	}

	public int getTotalStudents() {
		return DBModel.getInstance().getNumOfEnrolledStudentsInCourse(this);
	}

	@Override
	public String toString() {
		return this.name + " " + this.desc;
	}
}