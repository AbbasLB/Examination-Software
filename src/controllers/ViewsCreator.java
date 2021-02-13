package controllers;

import helpers.SceneCreator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import models.Course;
import models.Exam;
import models.users.UserType;

public class ViewsCreator {

	public static Node createExamItem(ExamsController examsController, Exam exam, UserType userType) {
		FXMLLoader loader;
		if (userType == UserType.TEACHER)
			loader = SceneCreator.createFXML("../resources/views/ItemViewTeacherExam.fxml");
		else
			loader = SceneCreator.createFXML("../resources/views/ItemViewStdExam.fxml");
		ItemExamController controller = loader.getController();
		controller.setExam(exam);
		controller.setExamsController(examsController);
		controller.initializeData();
		return loader.getRoot();
	}

	public static Node createCourseItem(CoursesController coursesController, Course course, UserType userType) {
		FXMLLoader loader;
		if (userType == UserType.TEACHER)
			loader = SceneCreator.createFXML("../resources/views/ItemViewTeacherCourse.fxml");
		else
			loader = SceneCreator.createFXML("../resources/views/ItemViewStdCourse.fxml");
		ItemCourseController controller = loader.getController();
		controller.setCourse(course);
		controller.setCoursesController(coursesController);
		controller.initializeData();
		return loader.getRoot();
	}

	public static void createEditCourseView(ItemTeacherCourseController teacherCourseController) {
		FXMLLoader loader = SceneCreator.createFXML("../resources/views/EditCourseView.fxml");
		EditCourseController controller = loader.getController();
		controller.setTeacherCourseController(teacherCourseController);
		controller.initializeData();
		SceneCreator.createScene(loader, true);
	}

	public static void createStartExamView(ItemStdExamController itemStdExamController) {
		FXMLLoader loader = SceneCreator.createFXML("../resources/views/StartExamView.fxml");
		StartExamController controller = loader.getController();
		controller.setItemExamController(itemStdExamController);
		controller.initializeData();
		SceneCreator.createScene(loader, true);
	}

	public static void createAddCourseView(CoursesController coursesController) {
		FXMLLoader loader = SceneCreator.createFXML("../resources/views/AddCourseView.fxml");
		AddCourseController controller = loader.getController();
		controller.setCoursesController(coursesController);
		SceneCreator.createScene(loader, true);
	}

	public static void createEnrollView(CoursesController coursesController) {
		FXMLLoader loader = SceneCreator.createFXML("../resources/views/EnrollView.fxml");
		EnrollController controller = loader.getController();
		controller.setCoursesController(coursesController);
		controller.initializeData();
		SceneCreator.createScene(loader, true);
	}

	public static void createAddExamView(ExamsController examsController) {
		FXMLLoader loader = SceneCreator.createFXML("../resources/views/AddExamView.fxml");
		AddExamController controller = loader.getController();
		controller.setExamsController(examsController);
		controller.initializeData(true);
		SceneCreator.createScene(loader, true);
	}

	public static void createEditExamView(ItemTeacherExamController examItemController) {
		FXMLLoader loader = SceneCreator.createFXML("../resources/views/AddExamView.fxml");
		AddExamController controller = loader.getController();
		controller.setExamItemController(examItemController);
		controller.initializeData(false);
		SceneCreator.createScene(loader, true);
	}
}
