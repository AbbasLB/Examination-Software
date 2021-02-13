package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import helpers.DialogCreator;
import helpers.SceneCreator;
import helpers.ValidationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import models.users.CurrentUser;
import models.users.Teacher;
import models.users.UserType;

public class AccountController implements Initializable {

	@FXML
	private Pane pnlAccount;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField txtPass;

	@FXML
	private PasswordField txtPassVerify;

	@FXML
	private Button btnSaveChanges;

	@FXML
	private Button btnReset;

	// invisible by default
	@FXML
	private Button btnAddNewTeacher;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (CurrentUser.getUser().getUserType() == UserType.TEACHER && ((Teacher) CurrentUser.getUser()).isAdmin())
			btnAddNewTeacher.setVisible(true);
		initializeFields();
	}

	void initializeFields() {
		txtName.setText(CurrentUser.getUser().getName());
		txtEmail.setText(CurrentUser.getUser().getEmail());
		txtPass.setText("");
		txtPassVerify.setText("");
	}

	@FXML
	void onBtnAddNewUserClicked(ActionEvent event) {
		SceneCreator.createScene("../resources/views/AddNewTeacherView.fxml", false);
	}

	@FXML
	void onBtnResetClicked(ActionEvent event) {
		initializeFields();
	}

	@FXML
	void onBtnSaveChangesClicked(ActionEvent event) {

		if (ValidationHelper.validateEditAccountForm(txtName, txtEmail, txtPass, txtPassVerify)
				&& CurrentUser.getUser().EditInfo(txtName.getText(), txtEmail.getText(), txtPass.getText())) {
			updateUserNameLbl();
			DialogCreator.showDialog(AlertType.INFORMATION, "Operation Completed", "Account Info",
					"Info changed successfully");
		}
		initializeFields();
	}

	void updateUserNameLbl() {
		Label lblUserName = (Label) pnlAccount.getScene().getWindow().getUserData();
		if (CurrentUser.getUser().getUserType() == UserType.STUDENT)
			lblUserName.setText("Student " + CurrentUser.getUser().getName());
		else
			lblUserName.setText("Teacher " + CurrentUser.getUser().getName());
	}

	public void pushToFront() {
		SceneCreator.animateToFront(pnlAccount);
	}

	public Pane getPane() {
		return pnlAccount;
	}

}
