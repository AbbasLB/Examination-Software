package helpers;

import java.io.IOException;

import animatefx.animation.AnimationFX;
import animatefx.animation.FadeIn;
import animatefx.animation.ZoomIn;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneCreator {
	private static double x, y;

	public static void makeDraggable(Stage stage) {

		stage.initStyle(StageStyle.TRANSPARENT);
		Scene scene = stage.getScene();
		scene.setFill(Color.TRANSPARENT);
		scene.setOnMousePressed(event -> {
			x = event.getSceneX();
			y = event.getSceneY();
		});
		scene.setOnMouseDragged(event -> {

			stage.setX(event.getScreenX() - x);
			stage.setY(event.getScreenY() - y);

		});
	}

	public static void createScene(FXMLLoader fxmlLoader, boolean wait) {
		try {
			Stage stage = new Stage();
			stage.setScene(new Scene(fxmlLoader.getRoot()));
			SceneCreator.makeDraggable(stage);
			new ZoomIn(stage.getScene().getRoot()).play();
			if (wait)
				stage.showAndWait();
			else
				stage.show();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	public static void createScene(String fxmlLocation, boolean wait) {
		createScene(createFXML(fxmlLocation), wait);
	}

	public static FXMLLoader createFXML(String fxml) {
		try {
			FXMLLoader loader = new FXMLLoader(SceneCreator.class.getResource(fxml));
			loader.load();
			return loader;
		} catch (IOException e) {
			ExceptionHandler.handleException(e);
			return null;
		}
	}

	public static void animateToFront(Pane pn) {
		AnimationFX transition = new FadeIn(pn).setSpeed(2);
		transition.play();
		pn.toFront();
	}

}
