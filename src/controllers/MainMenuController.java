package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import helpers.ExceptionHandler;
import helpers.SceneCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.UserProfileFactory;
import models.users.CurrentUser;
import models.users.UserType;

public class MainMenuController implements Initializable {

	@FXML
	private Label lblUserName;

	@FXML
	private Button btnHome;

	@FXML
	private ImageView imgProfile;

	@FXML
	private Button btnCourses;

	@FXML
	private Button btnExam;

	@FXML
	private Button btnResult;

	@FXML
	private Button btnAccount;

	@FXML
	private Button btnLogOut;

	@FXML
	private StackPane stackPane;

	private CoursesController coursesController;
	private AccountController accountController;
	private ExamsController examsController;
	private ResultController resultController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createUserViews();
	}

	public void createUserViews() {
		try {
			imgProfile.setImage(UserProfileFactory.getUserProfile(CurrentUser.getUser()));
			coursesController = SceneCreator.createFXML("../resources/views/CoursesView.fxml").getController();
			accountController = SceneCreator.createFXML("../resources/views/AccountView.fxml").getController();
			examsController = SceneCreator.createFXML("../resources/views/ExamsView.fxml").getController();
			if (CurrentUser.getUser().getUserType() == UserType.STUDENT) {
				lblUserName.setText("Student " + CurrentUser.getUser().getName());
				resultController = SceneCreator.createFXML("../resources/views/SResultsView.fxml").getController();
			} else {
				lblUserName.setText("Teacher " + CurrentUser.getUser().getName());
				resultController = SceneCreator.createFXML("../resources/views/TResultsView.fxml").getController();
			}
			stackPane.getChildren().addAll(accountController.getPane(), examsController.getPane(),
					resultController.getPane(), coursesController.getPane());
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	@FXML
	void handleMenuButtons(ActionEvent actionEvent) {
		Pane top = (Pane) stackPane.getChildren().get(3);
		if (actionEvent.getSource() == btnCourses && top != coursesController.getPane())
			coursesController.pushToFront();
		else if (actionEvent.getSource() == btnExam && top != examsController.getPane()) {
			examsController.pushToFront();
		} else if (actionEvent.getSource() == btnResult && top != resultController.getPane())
			resultController.pushToFront();
		else if (actionEvent.getSource() == btnAccount && top != accountController.getPane()) {
			accountController.pushToFront();
			lblUserName.getScene().getWindow().setUserData(lblUserName);
		}
	}

	@FXML
	void onBtnLogOutClicked(ActionEvent event) {
		((Stage) btnLogOut.getScene().getWindow()).close();
		CurrentUser.getUser().logOut();
		SceneCreator.createScene("../resources/views/LoginView.fxml", false);
	}

	@FXML
	void onBtnCloseClicked(MouseEvent event) {
		System.exit(0);
	}

	@FXML
	void onBtnMinimizeClicked(MouseEvent event) {
		Stage obj = (Stage) btnCourses.getScene().getWindow();
		obj.setIconified(true);
	}

}
