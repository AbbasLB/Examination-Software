package controllers;

import models.Course;

public interface ItemCourseController {
	public void setCourse(Course course);

	public void setCoursesController(CoursesController courseController);

	public void initializeData();

}
