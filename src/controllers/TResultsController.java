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
import models.TResultsTableModel;
import models.users.CurrentUser;

public class TResultsController implements Initializable, ResultController {

	@FXML
	private Pane TPnlResults;

	@FXML
	private TableView<TResultsTableModel> TableTeacherResults;

	@FXML
	private TableColumn<TResultsTableModel, String> colName;

	@FXML
	private TableColumn<TResultsTableModel, String> colEmail;

	@FXML
	private TableColumn<TResultsTableModel, String> colCourse;

	@FXML
	private TableColumn<TResultsTableModel, String> colExamTitle;

	@FXML
	private TableColumn<TResultsTableModel, Integer> colGrade;

	private ObservableList<TResultsTableModel> oblist;

	@FXML
	private ImageView imgRefresh;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		oblist = DBModel.getInstance().getTeacherGrades(CurrentUser.getUser());
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
		colExamTitle.setCellValueFactory(new PropertyValueFactory<>("examTitle"));
		colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
		TableTeacherResults.setItems(oblist);
	}

	@Override
	public Pane getPane() {
		return TPnlResults;
	}

	@FXML
	void onImgRefreshClicked(MouseEvent event) {
		refresh();
	}

	void refresh() {
		oblist = DBModel.getInstance().getTeacherGrades(CurrentUser.getUser());
		TableTeacherResults.setItems(oblist);
	}

	@Override
	public void pushToFront() {
		SceneCreator.animateToFront(TPnlResults);
		refresh();
	}
}
