package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import models.users.Student;

public class Exam {
	private int id;
	private String title;
	private Course course;
	ArrayList<Question> questions;
	Set<Grade> grades;

	public Exam(String title, Course course) {
		this.title = title;
		this.course = course;
		this.questions = new ArrayList<Question>();
		this.grades = new HashSet<Grade>();
	}

	public Exam(int id, String title, Course course, ArrayList<Question> questions, Set<Grade> grades) {
		this.id = id;
		this.title = title;
		this.course = course;
		this.questions = questions;
		this.grades = grades;
	}

	public Exam(int id, String title, Course course, ArrayList<Question> questions) {
		this.id = id;
		this.title = title;
		this.course = course;
		this.questions = questions;
		this.grades = new HashSet<Grade>();
	}

	public Exam(int id, String title, Course course) {
		this.id = id;
		this.course = course;
		this.title = title;
		this.questions = new ArrayList<Question>();
		this.grades = new HashSet<Grade>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Set<Grade> getGrades() {
		return grades;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}

	public void initializeQuestionsFromDB() {
		this.questions = DBModel.getInstance().getExamQuestions(this);
	}

	public boolean checkIfDone(Student student) {
		return DBModel.getInstance().checkIfExamDone(this, student);
	}

	public int getNumOfSubmissions() {
		return DBModel.getInstance().getNumOfExamSubmissions(this);
	}
}
