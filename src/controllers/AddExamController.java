package controllers;

import java.util.ArrayList;

import helpers.DialogCreator;
import helpers.ExceptionHandler;
import helpers.SceneCreator;
import helpers.ValidationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Course;
import models.Exam;
import models.Question;
import models.users.CurrentUser;
import models.users.Teacher;
import models.users.UserType;

public class AddExamController {

	@FXML
	private AnchorPane pnlCreate;

	@FXML
	private TextField txtExamTitle;

	@FXML
	private ChoiceBox<Course> choiceBoxCourse;

	@FXML
	private Button btnCreateExam;

	@FXML
	private AnchorPane pnlQuestion;

	@FXML
	private TextField txtQuestion;

	@FXML
	private TextField txtChoiceA;

	@FXML
	private TextField txtChoiceB;

	@FXML
	private TextField txtChoiceC;

	@FXML
	private TextField txtChoiceD;

	@FXML
	private Button btnAddQuestion;

	@FXML
	private Button btnDeleteQuestion;

	@FXML
	private RadioButton radioA;

	@FXML
	private ToggleGroup correct;

	@FXML
	private RadioButton radioB;

	@FXML
	private RadioButton radioC;

	@FXML
	private RadioButton radioD;

	@FXML
	private Button btnPreviousQuestion;

	@FXML
	private Button btnNextQuestion;

	@FXML
	private Button btnFinish;

	private int position = 0;
	private ExamsController examsController;
	private ItemTeacherExamController examItemController;
	private Exam exam;
	private ArrayList<Question> questions;
	private String newExamTitle;
	private boolean isNewExam;

	public void initializeData(boolean isNewExam) {
		this.isNewExam = isNewExam;
		if (isNewExam) {
			// New Exam Mode
			choiceBoxCourse.getItems().setAll(((Teacher) CurrentUser.getUser()).getCourses());
		} else {
			// Edit Mode
			isNewExam = false;
			choiceBoxCourse.getSelectionModel().select(examItemController.getExam().getCourse());
			choiceBoxCourse.setDisable(true);
			btnCreateExam.setText("Edit Exam");
			exam = examItemController.getExam();
			exam.initializeQuestionsFromDB();
			txtExamTitle.setText(exam.getTitle());
		}
	}

	public void setExamItemController(ItemTeacherExamController examItemController) {
		this.examItemController = examItemController;
	}

	public void setExamsController(ExamsController examsController) {
		this.examsController = examsController;
	}

	@SuppressWarnings("unchecked")
	@FXML
	void onBtnCreateExamPressed(ActionEvent event) {
		if (ValidationHelper.validateEmptyTextField(txtExamTitle, "Exam Title")
				&& ValidationHelper.validateUnselectedchoiceBox(choiceBoxCourse, "Course")) {
			if (isNewExam) {
				exam = new Exam(txtExamTitle.getText(), choiceBoxCourse.getValue());
				questions = exam.getQuestions();
				questions.add(new Question(exam));
			} else {
				newExamTitle = txtExamTitle.getText();
				questions = (ArrayList<Question>) exam.getQuestions().clone();
			}
			pnlCreate.setVisible(false);
			loadNextQuestion();
		}
	}

	@FXML
	void onBtnAddQuestionClicked(ActionEvent event) {
		saveQuestion();
		questions.add(++position, new Question(exam));
		loadNextQuestion();

	}

	@FXML
	void onBtnDeleteQuestionClicked(ActionEvent event) {
		questions.remove(position);
		position = Math.max(position - 1, 0);
		loadNextQuestion();
	}

	@FXML
	void onBtnNextQuestionClicked(ActionEvent event) {
		saveQuestion();
		position++;
		loadNextQuestion();
	}

	@FXML
	void onBtnPreviousQuestionClicked(ActionEvent event) {
		saveQuestion();
		position--;
		loadNextQuestion();
	}

	@FXML
	void onBtnFinishClicked(ActionEvent event) {
		try {
			saveQuestion();
			if (isNewExam) {
				if (((Teacher) CurrentUser.getUser()).addNewExam(exam)) {
					examsController.getExamsList().getChildren()
							.add(ViewsCreator.createExamItem(examsController, exam, UserType.TEACHER));
					DialogCreator.showDialog(AlertType.INFORMATION, "Exam Created", "Exam Create Successfully", null);
					((Stage) btnCreateExam.getScene().getWindow()).close();
				}
			} else {
				((Teacher) CurrentUser.getUser()).editExam(exam, newExamTitle, questions);
				DialogCreator.showDialog(AlertType.INFORMATION, "Edit Completed", "Exam Edited Successfully", null);
				((Stage) pnlCreate.getScene().getWindow()).close();
			}

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	void setButtonsStatus() {
		if (ValidationHelper.validateQuestion(txtQuestion, txtChoiceA, txtChoiceB, txtChoiceC, txtChoiceD, correct)) {
			btnAddQuestion.setDisable(false);
			btnFinish.setDisable(false);
			if (position == 0)
				btnPreviousQuestion.setDisable(true);
			else
				btnPreviousQuestion.setDisable(false);
			if (position == questions.size() - 1)
				btnNextQuestion.setDisable(true);
			else
				btnNextQuestion.setDisable(false);
		} else {
			btnAddQuestion.setDisable(true);
			btnFinish.setDisable(true);
			btnNextQuestion.setDisable(true);
			btnPreviousQuestion.setDisable(true);
		}
		if (questions.size() == 1)
			btnDeleteQuestion.setDisable(true);
		else
			btnDeleteQuestion.setDisable(false);
	}

	Toggle getCorrectToggle(char correctAns) {

		if (correctAns == 'A')
			return radioA;
		if (correctAns == 'B')
			return radioB;
		if (correctAns == 'C')
			return radioC;
		if (correctAns == 'D')
			return radioD;
		return null;
	}

	char getFormCorrectAnswer() {
		Toggle selectedToggle = correct.getSelectedToggle();
		if (selectedToggle == radioA)
			return 'A';
		if (selectedToggle == radioB)
			return 'B';
		if (selectedToggle == radioC)
			return 'C';
		return 'D';
	}

	void saveQuestion() {
		Question q = questions.get(position);
		q.setQuestion(txtQuestion.getText());
		q.setChoiceA(txtChoiceA.getText());
		q.setChoiceB(txtChoiceB.getText());
		q.setChoiceC(txtChoiceC.getText());
		q.setChoiceD(txtChoiceD.getText());
		q.setCorrect(getFormCorrectAnswer());
	}

	void initializeFormFromQuestion() {
		Question q = questions.get(position);
		txtQuestion.setText(q.getQuestion());
		txtChoiceA.setText(q.getChoiceA());
		txtChoiceB.setText(q.getChoiceB());
		txtChoiceC.setText(q.getChoiceC());
		txtChoiceD.setText(q.getChoiceD());
		correct.selectToggle(getCorrectToggle(q.getCorrect()));
	}

	public void loadNextQuestion() {
		SceneCreator.animateToFront(pnlQuestion);
		initializeFormFromQuestion();
		setButtonsStatus();
	}

	@FXML
	void onActionRadioButtons(ActionEvent event) {
		setButtonsStatus();
	}

	@FXML
	void onKeyTyped(KeyEvent event) {
		setButtonsStatus();
	}

	@FXML
	void onBtnCloseClicked(MouseEvent event) {
		((Stage) btnFinish.getScene().getWindow()).close();
	}

}
