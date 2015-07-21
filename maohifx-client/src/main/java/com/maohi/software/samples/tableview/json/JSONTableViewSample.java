package com.maohi.software.samples.tableview.json;

import java.util.Date;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class JSONTableViewSample extends Application {

	private TableView<JSONItem> table = new TableView<JSONItem>();
	private final ObservableList<JSONItem> data = FXCollections.observableArrayList();

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start(Stage stage) {
		TableColumn firstName = new TableColumn("First Name");
		firstName.setMinWidth(50);
		firstName.setCellValueFactory(new JSONValueFactory<String>("firstName"));

		TableColumn birthdateCol = new TableColumn("Date de naissance");
		birthdateCol.setMinWidth(50);
		birthdateCol.setCellValueFactory(new JSONValueFactory<String>("birthdate"));

		this.data.add(new JSONItem(new Object[] { "birthdate", new Date() }));
		this.data.add(new JSONItem(new Object[] { "firstName", "Toto" }, new Object[] { "birthdate", new Date() }));

		table.setEditable(true);
		table.setItems(data);
		table.getColumns().addAll(firstName, birthdateCol);

		Scene scene = new Scene(new Group());
		stage.setTitle("MyTableView Sample");
		stage.setWidth(450);
		stage.setHeight(550);
		((Group) scene.getRoot()).getChildren().addAll(table);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

}