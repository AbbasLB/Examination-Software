package models.users;

import java.util.ArrayList;
import java.util.HashSet;

import models.Course;
import models.DBModel;
import models.Exam;
import models.Question;

public class Teacher extends User {

	public Teacher(int id, String name, char gender, String email, String pass, HashSet<Course> courses) {
		super(id, name, gender, email, pass, courses);
	}

	public Teacher(int id, String name, char gender, String email, String pass) {
		super(id, name, gender, email, pass);
	}

	public Teacher(String name, char gender, String email, String pass) {
		super(0, name, gender, email, pass);
	}

	@Override
	public UserType getUserType() {
		return UserType.TEACHER;
	}

	public boolean isAdmin() {
		return false;
	}

	public boolean addNewCourse(Course c) {
		if (DBModel.getInstance().addNewCourse(c)) {
			courses.add(c);
			return true;
		}
		return false;
	}

	public boolean editCourse(Course course, String newName, String newDesc, int newCredits) {
		if (DBModel.getInstance().updateCourse(course, newName, newDesc, newCredits)) {
			course.setName(newName);
			course.setDesc(newDesc);
			course.setCredits(newCredits);
			return true;
		}
		return false;
	}

	public boolean deleteCourse(Course course) {
		if (courses.contains(course) && DBModel.getInstance().deleteCourse(course)) {
			courses.remove(course);
			return true;
		}
		return false;
	}

	public boolean addNewExam(Exam exam) {
		if (DBModel.getInstance().addNewExam(exam)) {
			exam.getCourse().getExams().add(exam);
			for (Question q : exam.getQuestions())
				if (!DBModel.getInstance().addNewQuestion(q))
					return false;
			return true;
		}
		return false;
	}

	public void editExam(Exam exam, String newTitle, ArrayList<Question> newQuestions) {
		DBModel.getInstance().updateExam(exam, newTitle);
		exam.setTitle(newTitle);
		for (Question q : newQuestions)
			if (exam.getQuestions().contains(q))
				DBModel.getInstance().updateQuestion(q, q.getQuestion(), q.getChoiceA(), q.getChoiceB(), q.getChoiceC(),
						q.getChoiceD(), q.getCorrect());
			else
				DBModel.getInstance().addNewQuestion(q);
		for (Question q : exam.getQuestions())
			if (!newQuestions.contains(q))
				DBModel.getInstance().deleteQuestion(q);
		exam.setQuestions(newQuestions);

	}

	public int getNumOfStudentsEnrolled() {
		int res = 0;
		for (Course c : courses)
			res += c.getTotalStudents();
		return res;
	}

	public int getNumOfSubmissions() {
		int res = 0;
		for (Course c : courses)
			for (Exam e : c.getExams())
				res += e.getNumOfSubmissions();
		return res;
	}

	public boolean deleteExam(Exam exam) {
		if (DBModel.getInstance().deleteExam(exam)) {
			exam.getCourse().getExams().remove(exam);
			return true;
		}
		return false;
	}

}
