package application;

import helpers.ExceptionHandler;
import helpers.SceneCreator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExamApp extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../resources/views/LoginView.fxml"));
			primaryStage.setScene(new Scene(root));
			SceneCreator.makeDraggable(primaryStage);
			primaryStage.show();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
