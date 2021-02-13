package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import helpers.SceneCreator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import models.DBModel;
import models.SResultsTableModel;
import models.users.CurrentUser;

public class SResultsController implements Initializable, ResultController {

	@FXML
	private Pane SPnlResults;

	@FXML
	private TableView<SResultsTableModel> TableStudentResults;

	@FXML
	private TableColumn<SResultsTableModel, String> colCourse;

	@FXML
	private TableColumn<SResultsTableModel, Integer> colCredits;

	@FXML
	private TableColumn<SResultsTableModel, String> colExamTitle;

	@FXML
	private TableColumn<SResultsTableModel, Integer> colGrade;

	private ObservableList<SResultsTableModel> oblist;

	@FXML
	private ImageView imgRefresh;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		oblist = DBModel.getInstance().getStudentGrades(CurrentUser.getUser());
		colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
		colCredits.setCellValueFactory(new PropertyValueFactory<>("credits"));
		colExamTitle.setCellValueFactory(new PropertyValueFactory<>("examTitle"));
		colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
		TableStudentResults.setItems(oblist);
	}

	@Override
	public Pane getPane() {
		return SPnlResults;
	}

	@FXML
	void onImgRefreshClicked(MouseEvent event) {
		refresh();
	}

	void refresh() {
		oblist = DBModel.getInstance().getStudentGrades(CurrentUser.getUser());
		TableStudentResults.setItems(oblist);
	}

	@Override
	public void pushToFront() {
		SceneCreator.animateToFront(SPnlResults);
		refresh();

	}
}
