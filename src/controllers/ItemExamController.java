package controllers;

import models.Exam;

public interface ItemExamController {
	public void setExam(Exam exam);

	public void setExamsController(ExamsController examsController);

	public void initializeData();
}
