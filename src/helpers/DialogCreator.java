package helpers;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import models.Course;
import models.Exam;

public class DialogCreator {
	public static void showErrorDialog(Exception e) {
		showDialog(AlertType.ERROR, "Error", "Exception Thrown", e.toString());
	}

	public static boolean showDialog(AlertType type, String title, String header, String msg) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(msg);
		Optional<ButtonType> showAndWait = alert.showAndWait();
		if (!showAndWait.isEmpty() && showAndWait.get() == ButtonType.OK)
			return true;
		return false;
	}

	public static boolean showDeleteExamDialog(Exam exam) {
		return showDialog(AlertType.CONFIRMATION, "Delete Exam",
				"Are you sure you want to delete Exam " + exam.getTitle() + " for course " + exam.getCourse().getName(),
				"This will delete the exam and all its related data permanently");
	}

	public static boolean showDeleteCourseDialog(Course course) {
		return showDialog(AlertType.CONFIRMATION, "Delete Course",
				"Are you sure you want to delete course " + course.getName() + " " + course.getDesc(),
				"This will delete the course and all its related data permanently");
	}

	public static boolean showUnenrollDialog(Course course) {
		return DialogCreator.showDialog(AlertType.CONFIRMATION, "Unenroll",
				"Are you sure you want to Unenroll from course " + course.getName() + " " + course.getDesc(), null);
	}

}
