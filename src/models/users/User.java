package models.users;

import java.util.HashSet;
import java.util.Set;

import models.Course;
import models.DBModel;

public abstract class User {
	protected int id;
	protected String name, email, pass;
	protected char gender;
	protected Set<Course> courses;

	public User(int id, String name, char gender, String email, String pass, Set<Course> courses) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.pass = pass;
		this.gender = gender;
		this.courses = courses;
	}

	public User(int id, String name, char gender, String email, String pass) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.pass = pass;
		this.gender = gender;
		this.courses = new HashSet<Course>();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return pass;
	}

	public char getGender() {
		return gender;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public abstract UserType getUserType();

	public boolean EditInfo(String name, String email, String password) {
		if (password.isBlank())
			return DBModel.getInstance().updateUser(this, false, name, email, password);
		else
			return DBModel.getInstance().updateUser(this, true, name, email, password);
	}

	public void initializeCoursesFromDB() {
		courses = DBModel.getInstance().getUserCourses(this);
	}

	public int getTotalCredits() {
		int total = 0;
		for (Course c : courses)
			total += c.getCredits();
		return total;
	}

	public int getNumOfExams() {
		int num = 0;
		for (Course c : courses)
			num += c.getExams().size();
		return num;
	}

	public void logOut() {
		if (CurrentUser.getUser().equals(this))
			CurrentUser.setUser(null);
	}
}
