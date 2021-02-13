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
import models.users.Student;

public class ItemStdCourseController implements ItemCourseController {

	@FXML
	private BorderPane itemMainPane;

	@FXML
	private Label lblCourseName;

	@FXML
	private ImageView imgCourseSubject;

	@FXML
	private Label lblCourseDesc;

	@FXML
	private ImageView imgDelete;

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

	@FXML
	void onImgDeleteClicked(MouseEvent event) {
		if (DialogCreator.showUnenrollDialog(course))
			if (((Student) CurrentUser.getUser()).unEnroll(course)) {
				((VBox) itemMainPane.getParent()).getChildren().remove(itemMainPane);
				coursesController.refresh();
			} else
				DialogCreator.showDialog(AlertType.ERROR, "Error",
						"An Error Occurred while unenrolling from the course", null);
	}

	@Override
	public void initializeData() {
		lblCourseName.setText(course.getName());
		imgCourseSubject.setImage(CourseImageFactory.getCourseImage(course));
		lblCourseDesc.setText(course.getDesc());
	}

}
