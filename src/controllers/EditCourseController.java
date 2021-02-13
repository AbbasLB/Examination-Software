package controllers;

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

public class EditCourseController {

	@FXML
	private TextField txtCourseCode;

	@FXML
	private TextField txtCredits;

	@FXML
	private TextField txtCourseDesc;

	@FXML
	private ChoiceBox<String> choiceBoxSubject;

	@FXML
	private Button btnEditCourse;

	private ItemTeacherCourseController teacherCourseController;
	private Course course;

	public void initializeData() {
		course = teacherCourseController.getCourse();
		choiceBoxSubject.getSelectionModel().selectFirst();
		while (choiceBoxSubject.getSelectionModel().getSelectedItem().charAt(0) != course.getName().charAt(0))
			choiceBoxSubject.getSelectionModel().selectNext();
		txtCourseCode.setText(course.getName().substring(1));
		txtCredits.setText(course.getCredits() + "");
		txtCourseDesc.setText(course.getDesc());
	}

	public void setTeacherCourseController(ItemTeacherCourseController teacherCourseController) {
		this.teacherCourseController = teacherCourseController;
	}

	@FXML
	void onBtnEditCourseClicked(ActionEvent event) {
		if (!ValidationHelper.validateCourseForm(txtCourseCode, txtCourseDesc, txtCredits, choiceBoxSubject))
			return;
		if (((Teacher) CurrentUser.getUser()).editCourse(course,
				choiceBoxSubject.getValue().charAt(0) + txtCourseCode.getText(), txtCourseDesc.getText(),
				Integer.valueOf(txtCredits.getText()))) {
			((Stage) btnEditCourse.getScene().getWindow()).close();
		}

	}

	@FXML
	void onBtnCloseClicked(MouseEvent event) {
		((Stage) txtCourseCode.getScene().getWindow()).close();
	}

}
