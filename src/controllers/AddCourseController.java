package controllers;

import helpers.ExceptionHandler;
import helpers.ValidationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Course;
import models.users.CurrentUser;
import models.users.Teacher;
import models.users.UserType;

public class AddCourseController {

	@FXML
	private TextField txtCourseCode;

	@FXML
	private TextField txtCredits;

	@FXML
	private TextField txtCourseDesc;

	@FXML
	private ChoiceBox<String> choiceBoxSubject;

	@FXML
	private Button btnAddCourse;

	private CoursesController coursesController;

	public void setCoursesController(CoursesController coursesController) {
		this.coursesController = coursesController;
	}

	@FXML
	void onBtnCloseClicked(MouseEvent event) {
		((Stage) txtCourseCode.getScene().getWindow()).close();
	}

	@FXML
	void onBtnAddCourseClicked(ActionEvent event) {
		if (!ValidationHelper.validateCourseForm(txtCourseCode, txtCourseDesc, txtCredits, choiceBoxSubject))
			return;
		try {
			Course course = new Course(Integer.valueOf(txtCredits.getText()),
					choiceBoxSubject.getValue().charAt(0) + txtCourseCode.getText(), txtCourseDesc.getText(),
					(Teacher) CurrentUser.getUser());
			if (((Teacher) CurrentUser.getUser()).addNewCourse(course)) {
				coursesController.getCoursesList().getChildren()
						.add(ViewsCreator.createCourseItem(coursesController, course, UserType.TEACHER));
				((Stage) btnAddCourse.getScene().getWindow()).close();
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

}
