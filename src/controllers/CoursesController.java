package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import helpers.SceneCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Course;
import models.users.CurrentUser;
import models.users.Student;
import models.users.Teacher;
import models.users.UserType;

public class CoursesController implements Initializable {

	@FXML
	private Pane pnlCourses;

	@FXML
	private VBox boxInfo1;

	@FXML
	private VBox boxInfo2;

	@FXML
	private VBox boxInfo3;

	@FXML
	private VBox coursesList;

	@FXML
	private Button btnCourse;

	// fxml setup as teacher
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillCourses();
		refresh();
		if (CurrentUser.getUser().getUserType() == UserType.STUDENT) {
			btnCourse.setText("Enroll");
			((Label) boxInfo3.getChildren().get(1)).setText("Available Courses");
		}
	}

	public void refresh() {
		((Label) boxInfo1.getChildren().get(0)).setText(CurrentUser.getUser().getCourses().size() + "");
		((Label) boxInfo2.getChildren().get(0)).setText(CurrentUser.getUser().getTotalCredits() + "");
		if (CurrentUser.getUser().getUserType() == UserType.STUDENT)
			((Label) boxInfo3.getChildren().get(0))
					.setText(((Student) CurrentUser.getUser()).getAvailableCourses().size() + "");
		else
			((Label) boxInfo3.getChildren().get(0))
					.setText(((Teacher) CurrentUser.getUser()).getNumOfStudentsEnrolled() + "");
	}

	public VBox getCoursesList() {
		return this.coursesList;
	}

	public Pane getPane() {
		return pnlCourses;
	}

	void fillCourses() {
		for (Course course : CurrentUser.getUser().getCourses())
			coursesList.getChildren()
					.add(ViewsCreator.createCourseItem(this, course, CurrentUser.getUser().getUserType()));
	}

	@FXML
	void onBtnCourseClicked(ActionEvent event) {

		if (CurrentUser.getUser().getUserType() == UserType.TEACHER)
			ViewsCreator.createAddCourseView(this);
		else
			ViewsCreator.createEnrollView(this);
		refresh();
	}

	public void pushToFront() {
		SceneCreator.animateToFront(pnlCourses);
		refresh();
	}
}
