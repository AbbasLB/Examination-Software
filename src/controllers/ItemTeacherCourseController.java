package controllers;

import helpers.DialogCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import models.Course;
import models.CourseImageFactory;
import models.users.CurrentUser;
import models.users.Teacher;

public class ItemTeacherCourseController implements ItemCourseController {

	@FXML
	private Label lblCourseName;

	@FXML
	private BorderPane paneItem;

	@FXML
	private ImageView imgCourseSubject;

	@FXML
	private Label lblCourseDesc;

	@FXML
	private ImageView imgEditCourse;

	@FXML
	private ImageView imgDeleteCourse;

	private Course course;
	private CoursesController coursesController;

	@Override
	public void setCourse(Course course) {
		this.course = course;

	}

	@Override
	public void setCoursesController(CoursesController courseController) {
		this.coursesController = courseController;

	}

	@Override
	public void initializeData() {
		lblCourseName.setText(course.getName());
		imgCourseSubject.setImage(CourseImageFactory.getCourseImage(course));
		lblCourseDesc.setText(course.getDesc());
	}

	public Course getCourse() {
		return this.course;
	}

	@FXML
	void onImgDeleteCourseClicked(MouseEvent event) {
		if (DialogCreator.showDeleteCourseDialog(course))
			if (((Teacher) CurrentUser.getUser()).deleteCourse(course)) {
				((VBox) paneItem.getParent()).getChildren().remove(paneItem);
				coursesController.refresh();
			}

			else
				DialogCreator.showDialog(AlertType.ERROR, "Error", "An Error Occurred while deleting the course", null);

	}

	@FXML
	void onImgEditCourseClicked(MouseEvent event) {
		ViewsCreator.createEditCourseView(this);
		initializeData();
		coursesController.refresh();
	}
}
