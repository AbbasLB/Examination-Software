package helpers;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import models.users.CurrentUser;

public class ValidationHelper {

	private ValidationHelper() {
	};

	public static boolean validateEmptyTextField(TextField txtField, String fieldName) {
		if (txtField.getText().isBlank()) {
			DialogCreator.showDialog(AlertType.ERROR, "Error", "Empty " + fieldName, "Please enter " + fieldName);
			return false;
		}
		return true;
	}

	public static boolean validateUnselectedchoiceBox(ChoiceBox<?> choiceBox, String defaultValue) {
		if (choiceBox.getValue().toString().equals("") || choiceBox.getValue().toString().equals(defaultValue)) {
			DialogCreator.showDialog(AlertType.ERROR, "Error", defaultValue + " Not Specified",
					"Please select " + defaultValue);
			return false;
		}
		return true;
	}

	public static boolean validatePasswordFields(TextField txtFieldPassword, TextField txtFieldPasswordConfirmation) {
		if (!validateEmptyTextField(txtFieldPassword, "password")
				|| !validateEmptyTextField(txtFieldPasswordConfirmation, "password verification"))
			return false;
		if (!txtFieldPassword.getText().equals(txtFieldPasswordConfirmation.getText())) {
			DialogCreator.showDialog(AlertType.ERROR, "Error", "Password Mismatch", "Please enter your password again");
			txtFieldPassword.setText("");
			txtFieldPasswordConfirmation.setText("");
			return false;
		}
		return true;
	}

	public static boolean validateEmailTextField(TextField txtEmail) {
		if (!ValidationHelper.validateEmptyTextField(txtEmail, "email"))
			return false;
		if (!txtEmail.getText().matches("(.+)@(.+)\\.(.+)")) {
			DialogCreator.showDialog(AlertType.ERROR, "Error", "Invalid Email Address",
					"Please enter a valid email address");
			return false;
		}
		return true;

	}

	public static boolean validateEditAccountForm(TextField txtName, TextField txtEmail, TextField txtPass,
			TextField txtPassVerify) {
		if (!ValidationHelper.validateEmptyTextField(txtName, "name")
				|| !ValidationHelper.validateEmailTextField(txtEmail))
			return false;
		if (!txtPass.getText().isBlank() && !txtPass.getText().equals(txtPassVerify.getText()))
			DialogCreator.showDialog(AlertType.ERROR, "Error", "Password Mismatch", "Please enter your password again");
		else if (txtEmail.getText().equals(CurrentUser.getUser().getEmail())
				&& txtName.getText().equals(CurrentUser.getUser().getName()) && txtPass.getText().isBlank())
			DialogCreator.showDialog(AlertType.INFORMATION, "Account Info", "Same Name,Email and Password",
					"No changes Made");
		else
			return true;
		return false;
	}

	public static boolean validateNewUserForm(TextField txtNewName, TextField txtNewEmail, TextField txtNewPass,
			TextField txtNewPassVerify, ChoiceBox<String> choiceBoxGender) {
		return (ValidationHelper.validateEmptyTextField(txtNewName, "name")
				&& ValidationHelper.validateUnselectedchoiceBox(choiceBoxGender, "Gender")
				&& ValidationHelper.validateEmailTextField(txtNewEmail)
				&& ValidationHelper.validatePasswordFields(txtNewPass, txtNewPassVerify));
	}

	public static boolean validateCourseForm(TextField txtCourseCode, TextField txtCourseDesc, TextField txtCredits,
			ChoiceBox<String> choiceBoxSubject) {
		if (!validateUnselectedchoiceBox(choiceBoxSubject, "Course Subject")
				|| !validateEmptyTextField(txtCourseCode, "Course Code")
				|| !validateEmptyTextField(txtCourseDesc, "Course Desc")
				|| !validateEmptyTextField(txtCredits, "Course Credits"))
			return false;
		if (!txtCourseCode.getText().matches("\\d+") || txtCourseCode.getText().length() != 4)
			DialogCreator.showDialog(AlertType.ERROR, "Error", "Invalid Course Code", "Please enter 4 digits code");
		else if (!txtCredits.getText().matches("\\d+") || Integer.valueOf(txtCredits.getText()) > 100)
			DialogCreator.showDialog(AlertType.ERROR, "Error", "Invalid Course Credits",
					"Please enter valid number of credits");
		else
			return true;
		return false;
	}

	public static boolean validateEmptyToggleGroup(ToggleGroup toggleGroup, String fieldName) {
		if (toggleGroup.getSelectedToggle() == null) {
			DialogCreator.showDialog(AlertType.ERROR, "Error", "No " + fieldName + " selected",
					"Please select " + fieldName);
			return false;
		}
		return true;
	}

	public static boolean validateQuestion(TextField txtQuestion, TextField txtChoiceA, TextField txtChoiceB,
			TextField txtChoiceC, TextField txtChoiceD, ToggleGroup correct) {
		if (txtQuestion.getText().isBlank() || txtChoiceA.getText().isBlank() || txtChoiceB.getText().isBlank()
				|| txtChoiceC.getText().isBlank() || txtChoiceD.getText().isBlank()
				|| correct.getSelectedToggle() == null)
			return false;
		return true;
	}

}
