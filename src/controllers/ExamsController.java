package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import helpers.SceneCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Course;
import models.Exam;
import models.UserProfileFactory;
import models.users.CurrentUser;
import models.users.Student;
import models.users.Teacher;
import models.users.UserType;

public class ExamsController implements Initializable {

	@FXML
	private Pane pnlExams;

	@FXML
	private VBox boxInfo1;

	@FXML
	private VBox boxInfo2;

	@FXML
	private VBox boxInfo3;

	@FXML
	private VBox examsList;

	@FXML
	private Button btnExam;

	// fxml as teacher
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refresh();
		if (CurrentUser.getUser().getUserType() == UserType.STUDENT) {
			((ImageView) btnExam.getGraphic()).setImage(new Image(
					UserProfileFactory.class.getResource("../resources/images/Refresh_White.png").toString()));
			btnExam.setText("Refresh");
			((Label) boxInfo1.getChildren().get(1)).setText("Total Exams");
			((Label) boxInfo2.getChildren().get(1)).setText("Exams Remaining");
			((Label) boxInfo3.getChildren().get(1)).setText("Exams Done");
		}

	}

	public Pane getPane() {
		return pnlExams;
	}

	public VBox getExamsList() {
		return this.examsList;
	}

	public void refresh() {
		((Label) boxInfo1.getChildren().get(0)).setText(CurrentUser.getUser().getNumOfExams() + "");
		if (CurrentUser.getUser().getUserType() == UserType.STUDENT) {
			int examDone = ((Student) CurrentUser.getUser()).getNumOfExamsDone();
			((Label) boxInfo2.getChildren().get(0)).setText(CurrentUser.getUser().getNumOfExams() - examDone + "");
			((Label) boxInfo3.getChildren().get(0)).setText(examDone + "");
		} else {
			((Label) boxInfo2.getChildren().get(0))
					.setText(((Teacher) CurrentUser.getUser()).getNumOfSubmissions() + "");
			((Label) boxInfo3.getChildren().get(0))
					.setText(((Teacher) CurrentUser.getUser()).getNumOfStudentsEnrolled() + "");
		}
		fillExams();
	}

	void fillExams() {
		examsList.getChildren().clear();
		for (Course course : CurrentUser.getUser().getCourses())
			for (Exam exam : course.getExams())
				examsList.getChildren()
						.add(ViewsCreator.createExamItem(this, exam, CurrentUser.getUser().getUserType()));
	}

	@FXML
	void onBtnExamClicked(ActionEvent event) {

		if (CurrentUser.getUser().getUserType() == UserType.TEACHER)
			ViewsCreator.createAddExamView(this);
		refresh();

	}

	public void pushToFront() {
		SceneCreator.animateToFront(pnlExams);
		refresh();
	}

}
