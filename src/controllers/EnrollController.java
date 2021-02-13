package controllers;

import helpers.ValidationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Course;
import models.users.CurrentUser;
import models.users.Student;
import models.users.UserType;

public class EnrollController {

	@FXML
	private ChoiceBox<Course> choiceBoxCourse;

	@FXML
	private Button btnEnroll;

	private CoursesController coursesController;

	public void initializeData() {
		choiceBoxCourse.getItems().setAll(((Student) CurrentUser.getUser()).getAvailableCourses());
	}

	public void setCoursesController(CoursesController coursesController) {
		this.coursesController = coursesController;
	}

	@FXML
	void onBtnEnrollClicked(ActionEvent event) {
		if (ValidationHelper.validateUnselectedchoiceBox(choiceBoxCourse, "Course")
				&& ((Student) CurrentUser.getUser()).enroll(choiceBoxCourse.getValue())) {
			coursesController.getCoursesList().getChildren().add(
					ViewsCreator.createCourseItem(coursesController, choiceBoxCourse.getValue(), UserType.STUDENT));
			((Stage) btnEnroll.getScene().getWindow()).close();
		}

	}

	@FXML
	void onBtnCloseClicked(MouseEvent event) {
		((Stage) choiceBoxCourse.getScene().getWindow()).close();
	}

}
