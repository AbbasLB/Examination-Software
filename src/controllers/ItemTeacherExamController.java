package controllers;

import helpers.DialogCreator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import models.CourseImageFactory;
import models.Exam;
import models.users.CurrentUser;
import models.users.Teacher;

public class ItemTeacherExamController implements ItemExamController {

	@FXML
	private BorderPane paneItem;

	@FXML
	private Label lblCourseName;

	@FXML
	private ImageView imgCourseSubject;

	@FXML
	private Label lblExamTitle;

	@FXML
	private ImageView imgEditCourse;

	@FXML
	private ImageView imgDeleteCourse;

	private Exam exam;
	private ExamsController examsController;

	@Override
	public void setExam(Exam exam) {
		this.exam = exam;

	}

	@Override
	public void setExamsController(ExamsController examsController) {
		this.examsController = examsController;

	}

	@Override
	public void initializeData() {
		lblCourseName.setText(exam.getCourse().getName());
		imgCourseSubject.setImage(CourseImageFactory.getCourseImage(exam.getCourse()));
		lblExamTitle.setText(exam.getTitle());
	}

	public Exam getExam() {
		return this.exam;
	}

	@FXML
	void onImgDeleteCourseClicked(MouseEvent event) {
		if (DialogCreator.showDeleteExamDialog(exam))
			if (((Teacher) CurrentUser.getUser()).deleteExam(exam)) {
				((VBox) paneItem.getParent()).getChildren().remove(paneItem);
				examsController.refresh();
			} else
				DialogCreator.showDialog(AlertType.ERROR, "Error", "An Error Occurred while deleting the Exam", null);
	}

	@FXML
	void onImgEditCourseClicked(MouseEvent event) {
		ViewsCreator.createEditExamView(this);
		initializeData();
		examsController.refresh();
	}

}
