package models.users;

import java.util.HashSet;

import models.Course;
import models.DBModel;

public class Admin extends Teacher {
	public Admin(int id, String name, char gender, String email, String pass, HashSet<Course> courses) {
		super(id, name, gender, email, pass, courses);
	}

	public Admin(int id, String name, char gender, String email, String pass) {
		super(id, name, gender, email, pass);
	}

	public Admin(String name, char gender, String email, String pass) {
		super(name, gender, email, pass);
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

	public boolean addNewTeacher(Teacher teacher) {
		return DBModel.getInstance().addNewUser(teacher);

	}

}
