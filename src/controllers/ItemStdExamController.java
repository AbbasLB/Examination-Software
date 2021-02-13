package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import models.CourseImageFactory;
import models.Exam;
import models.users.CurrentUser;
import models.users.Student;

public class ItemStdExamController implements ItemExamController {

	@FXML
	private BorderPane itemMainPane;

	@FXML
	private Label lblCourseName;

	@FXML
	private ImageView imgCourseSubject;

	@FXML
	private Label lblExamTitle;

	@FXML
	private Label lblFinished;

	@FXML
	private Button btnStartExam;

	private Exam exam;
	private ExamsController examController;

	@Override
	public void setExam(Exam exam) {
		this.exam = exam;

	}

	public Exam getExam() {
		return this.exam;
	}

	@Override
	public void setExamsController(ExamsController examsController) {
		this.examController = examsController;
	}

	@Override
	public void initializeData() {
		lblCourseName.setText(exam.getCourse().getName());
		imgCourseSubject.setImage(CourseImageFactory.getCourseImage(exam.getCourse()));
		lblExamTitle.setText(exam.getTitle());
		if (exam.checkIfDone((Student) CurrentUser.getUser()))
			btnStartExam.setVisible(false);
	}

	@FXML
	void onBtnStartExamClicked(ActionEvent event) {
		ViewsCreator.createStartExamView(this);
		initializeData();
		examController.refresh();
	}
}
