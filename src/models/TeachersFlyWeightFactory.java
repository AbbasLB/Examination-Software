package models;

import java.util.HashMap;

import models.users.Teacher;

public class TeachersFlyWeightFactory {

	public static final HashMap<Integer, Teacher> teacherMap = new HashMap<Integer, Teacher>();

	public static Teacher getTeacher(int tid) {
		Teacher teacher = teacherMap.get(tid);
		if (teacher == null) {
			teacher = DBModel.getInstance().getTeacherByTID(tid);
			teacherMap.put(tid, teacher);
		}
		return teacher;
	}
}
