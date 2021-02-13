package controllers;

import helpers.DialogCreator;
import helpers.SceneCreator;
import helpers.ValidationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.DBModel;
import models.users.Student;

public class LoginController {

	@FXML
	private Pane pnlSignIn;

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField txtPass;

	@FXML
	private Button btnSignIn;

	@FXML
	private Button btnSignUpPage;

	@FXML
	private Pane pnlSignUp;

	@FXML
	private Button btnSignUp;

	@FXML
	private TextField txtNewName;

	@FXML
	private PasswordField txtNewPass;

	@FXML
	private ImageView btnBack;

	@FXML
	private TextField txtNewEmail;

	@FXML
	private PasswordField txtNewPassVerify;

	@FXML
	private ChoiceBox<String> choiceBoxGender;

	@FXML
	private StackPane btnMinimize;

	@FXML
	private StackPane btnClose;

	@FXML
	void onBtnCloseClicked(MouseEvent event) {
		System.exit(0);
	}

	@FXML
	void onBtnMinimizeClicked(MouseEvent event) {
		Stage obj = (Stage) btnMinimize.getScene().getWindow();
		obj.setIconified(true);
	}

	@FXML
	void onBtnSignUpPageClicked(ActionEvent event) {
		pnlSignUp.toFront();

	}

	@FXML
	void onBtnBackClicked(MouseEvent event) {
		pnlSignIn.toFront();
	}

	/*
	 * @FXML void onTxtEmailChange(KeyEvent event) { if
	 * (txtNewEmail.getText().matches(".*@.*"))
	 * txtNewEmail.setStyle("-fx-text-box-border: red; -fx-focus-color: red;"); else
	 * btnSignUp.setDisable(true); }
	 */

	@FXML
	void onBtnSignInClicked(ActionEvent event) {
		signIn();
	}

	@FXML
	void onBtnSignUpClicked(ActionEvent event) {
		if (ValidationHelper.validateNewUserForm(txtNewName, txtNewEmail, txtNewPass, txtNewPassVerify, choiceBoxGender)
				&& DBModel.getInstance().addNewUser(new Student(txtNewName.getText(),
						choiceBoxGender.getValue().charAt(0), txtNewEmail.getText(), txtNewPass.getText()))) {
			txtEmail.setText(txtNewEmail.getText());
			txtPass.setText(txtNewPass.getText());
			clearSignUpForm();
			DialogCreator.showDialog(AlertType.INFORMATION, "Registration Completed", "Signed Up Successfully",
					"Account created");
			pnlSignIn.toFront();
		}
	}

	public void clearSignUpForm() {
		txtNewName.setText("");
		txtNewEmail.setText("");
		txtNewPass.setText("");
		txtNewPassVerify.setText("");
		choiceBoxGender.setValue("Gender");
	}

	@FXML
	void onKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER)
			signIn();

	}

	private void signIn() {
		if (!ValidationHelper.validateEmptyTextField(txtEmail, "email")
				|| !ValidationHelper.validateEmptyTextField(txtPass, "password"))
			return;

		if (!DBModel.getInstance().login(txtEmail.getText(), txtPass.getText())
				&& !DBModel.getInstance().login(txtEmail.getText() + "@ul.edu.lb", txtPass.getText())
				&& !DBModel.getInstance().login(txtEmail.getText() + "@st.ul.edu.lb", txtPass.getText()))
			DialogCreator.showDialog(AlertType.ERROR, "Error", "Invalid Login Credentials",
					"Please enter correct email and Password");
		else {
			((Stage) pnlSignIn.getScene().getWindow()).close();
			SceneCreator.createScene("../resources/views/MainMenuView.fxml", false);
		}
	}

}
