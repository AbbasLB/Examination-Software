package controllers;

import java.util.ArrayList;
import java.util.Collections;

import helpers.DialogCreator;
import helpers.SceneCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.DBModel;
import models.Exam;
import models.Question;
import models.users.CurrentUser;
import models.users.Student;

public class StartExamController {

	@FXML
	private AnchorPane pnlQuestion;

	@FXML
	private TextField txtQuestion;

	@FXML
	private Button btnNextQuestion;

	@FXML
	private Button btnPreviousQuestion;

	@FXML
	private RadioButton radioA;

	@FXML
	private ToggleGroup choice;

	@FXML
	private RadioButton radioB;

	@FXML
	private RadioButton radioC;

	@FXML
	private RadioButton radioD;

	private ItemStdExamController itemExamController;
	private Exam exam;
	private int position = 0;
	private ArrayList<Question> questions;
	private ArrayList<Character> studentAnswers;

	public void initializeData() {
		exam = itemExamController.getExam();
		exam.initializeQuestionsFromDB();
		questions = exam.getQuestions();
		studentAnswers = new ArrayList<Character>(Collections.nCopies(questions.size(), ' '));
		loadNextQuestion();
	}

	public void setItemExamController(ItemStdExamController itemExamController) {
		this.itemExamController = itemExamController;
	}

	@FXML
	void onBtnNextQuestionClicked(ActionEvent event) {
		saveChoice();
		if (position == questions.size() - 1) {
			finish();
		} else {
			position++;
			loadNextQuestion();
		}
	}

	public void finish() {
		int correctAnswers = 0;
		for (int i = 0; i < studentAnswers.size(); i++)
			if (studentAnswers.get(i) == questions.get(i).getCorrect())
				correctAnswers++;
		float grade = (float) correctAnswers * 100 / questions.size();
		grade = Math.round(grade * 4) / (float) 4.0;
		saveChoice();
		if (DBModel.getInstance().registerNewGrade((Student) CurrentUser.getUser(), exam, grade)) {
			DialogCreator.showDialog(AlertType.INFORMATION, "Exam Completed", "You scored " + grade + " out of 100",
					null);
			((Stage) pnlQuestion.getScene().getWindow()).close();
		}
	}

	@FXML
	void onBtnPreviousQuestionClicked(ActionEvent event) {
		saveChoice();
		position--;
		loadNextQuestion();
	}

	void initializeFormFromQuestion() {
		Question q = questions.get(position);
		txtQuestion.setText(q.getQuestion());
		radioA.setText(q.getChoiceA());
		radioB.setText(q.getChoiceB());
		radioC.setText(q.getChoiceC());
		radioD.setText(q.getChoiceD());
		choice.selectToggle(getChosenToggle(studentAnswers.get(position)));
	}

	public void loadNextQuestion() {
		SceneCreator.animateToFront(pnlQuestion);
		initializeFormFromQuestion();
		setButtonsStatus();
	}

	void setButtonsStatus() {
		if (position == 0)
			btnPreviousQuestion.setDisable(true);
		else
			btnPreviousQuestion.setDisable(false);
		if (position == questions.size() - 1) {
			btnNextQuestion.setText("Finish Exam");
			btnNextQuestion.setStyle("-fx-background-color:green");
		}

		else {
			btnNextQuestion.setText("Next Question");
			btnNextQuestion.setStyle("");
		}
	}

	Toggle getChosenToggle(char correctAns) {

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

	char getFormChosenAnswer() {
		Toggle selectedToggle = choice.getSelectedToggle();
		if (selectedToggle == radioA)
			return 'A';
		if (selectedToggle == radioB)
			return 'B';
		if (selectedToggle == radioC)
			return 'C';
		if (selectedToggle == radioD)
			return 'D';
		return ' ';
	}

	void saveChoice() {
		studentAnswers.set(position, getFormChosenAnswer());
	}

}
