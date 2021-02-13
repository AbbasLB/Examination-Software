package controllers;

import helpers.DialogCreator;
import helpers.ValidationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.users.Admin;
import models.users.CurrentUser;
import models.users.Teacher;

public class AddTeacherController {

	@FXML
	private Button btnAddNewTeacher;

	@FXML
	private TextField txtNewName;

	@FXML
	private PasswordField txtNewPass;

	@FXML
	private TextField txtNewEmail;

	@FXML
	private PasswordField txtNewPassVerify;

	@FXML
	private ChoiceBox<String> choiceBoxGender;

	@FXML
	private CheckBox checkBoxIsAdmin;

	@FXML
	void onBtnCloseClicked(MouseEvent event) {
		((Stage) checkBoxIsAdmin.getScene().getWindow()).close();
	}

	@FXML
	void onBtnAddNewTeacherClicked(ActionEvent event) {
		if (!ValidationHelper.validateNewUserForm(txtNewName, txtNewEmail, txtNewPass, txtNewPassVerify,
				choiceBoxGender))
			return;
		boolean result;
		if (checkBoxIsAdmin.isSelected())
			result = ((Admin) CurrentUser.getUser()).addNewTeacher(new Admin(txtNewName.getText(),
					choiceBoxGender.getValue().charAt(0), txtNewEmail.getText(), txtNewPass.getText()));
		else
			result = ((Admin) CurrentUser.getUser()).addNewTeacher(new Teacher(txtNewName.getText(),
					choiceBoxGender.getValue().charAt(0), txtNewEmail.getText(), txtNewPass.getText()));
		if (result) {
			DialogCreator.showDialog(AlertType.INFORMATION, "Registration Completed", "Added Successfully",
					"Teacher account created");
			((Stage) checkBoxIsAdmin.getScene().getWindow()).close();
		}
	}
}
