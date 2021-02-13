package models;

public class Question {
	private int id;
	private String question, choiceA, choiceB, choiceC, choiceD;
	private char correctChoice;
	private Exam exam;

	public Question(int id, String question, String choiceA, String choiceB, String choiceC, String choiceD,
			char correct, Exam exam) {
		this.id = id;
		this.question = question;
		this.choiceA = choiceA;
		this.choiceB = choiceB;
		this.choiceC = choiceC;
		this.choiceD = choiceD;
		this.correctChoice = correct;
		this.exam = exam;
	}

	public Question(Exam exam) {
		this.exam = exam;
		this.question = "";
		this.choiceA = "";
		this.choiceB = "";
		this.choiceC = "";
		this.choiceD = "";
	}

	public Question(String question, String choiceA, String choiceB, String choiceC, String choiceD, char correct,
			Exam exam) {
		this.question = question;
		this.choiceA = choiceA;
		this.choiceB = choiceB;
		this.choiceC = choiceC;
		this.choiceD = choiceD;
		this.correctChoice = correct;
		this.exam = exam;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getChoiceA() {
		return choiceA;
	}

	public void setChoiceA(String choiceA) {
		this.choiceA = choiceA;
	}

	public String getChoiceB() {
		return choiceB;
	}

	public void setChoiceB(String choiceB) {
		this.choiceB = choiceB;
	}

	public String getChoiceC() {
		return choiceC;
	}

	public void setChoiceC(String choiceC) {
		this.choiceC = choiceC;
	}

	public String getChoiceD() {
		return choiceD;
	}

	public void setChoiceD(String choiceD) {
		this.choiceD = choiceD;
	}

	public char getCorrect() {
		return correctChoice;
	}

	public void setCorrect(char correct) {
		this.correctChoice = correct;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

}
