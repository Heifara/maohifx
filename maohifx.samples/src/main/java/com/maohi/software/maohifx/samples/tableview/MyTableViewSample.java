package com.maohi.software.maohifx.samples.tableview;

import com.maohi.software.maohifx.samples.beans.Person;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MyTableViewSample extends Application {

	public static void main(final String[] args) {
		launch(args);
	}

	private final TableView<Person> table = new TableView<Person>();
	private final TextField filter = new TextField("Saisir votre filtre");
	private final ObservableList<Person> data = FXCollections.observableArrayList(new Person("Jacob", "Smith", "jacob.smith@example.com"), new Person("Isabella", "Johnson", "isabella.johnson@example.com"), new Person("Ethan", "Williams", "ethan.williams@example.com"), new Person("Emma", "Jones", "emma.jones@example.com"), new Person("Michael", "Brown", "michael.brown@example.com"));

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start(final Stage stage) {
		final TableColumn firstNameCol = new TableColumn("First Name");
		firstNameCol.setMinWidth(100);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
		firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		firstNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
			@Override
			public void handle(final CellEditEvent<Person, String> t) {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setFirstName(t.getNewValue());
			}
		});

		final TableColumn lastNameCol = new TableColumn("Last Name");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
		lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lastNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
			@Override
			public void handle(final CellEditEvent<Person, String> t) {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setLastName(t.getNewValue());
			}
		});

		final TableColumn ageCol = new TableColumn("Age");
		ageCol.setMinWidth(50);
		ageCol.setCellValueFactory(new PropertyValueFactory("age"));
		ageCol.setCellFactory(new Callback<TableColumn, TableCell>() {
			@Override
			public TableCell call(final TableColumn p) {
				final TableCell cell = new TableCell() {
					@Override
					protected void updateItem(final Object item, final boolean empty) {
						super.updateItem(item, empty);
						this.setText((String) item);
					}
				};
				return cell;
			}
		});

		final TableColumn birthdateCol = new TableColumn("Date de naissance");
		birthdateCol.setMinWidth(50);
		birthdateCol.setCellValueFactory(new PropertyValueFactory("birtdate") {
			@Override
			public ObservableValue call(final CellDataFeatures param) {
				return this.getValue(param.getValue());
			}

			private ObservableValue getValue(final Object value) {
				final Person iPerson = (Person) value;
				return new ReadOnlyStringWrapper(iPerson.getBirthdate().toString());
			}
		});

		final TableColumn emailCol = new TableColumn("Email");
		emailCol.setMinWidth(200);
		emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
		emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
		emailCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
			@Override
			public void handle(final CellEditEvent<Person, String> t) {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmail(t.getNewValue());
			}
		});
		this.table.setEditable(true);
		this.table.setItems(this.data);
		this.table.getColumns().addAll(firstNameCol, lastNameCol, ageCol, birthdateCol, emailCol);

		this.filter.setOnKeyTyped(new EventHandler<Event>() {

			@Override
			public void handle(final Event event) {
				final String iFilter = MyTableViewSample.this.filter.getText();
				if (!iFilter.isEmpty()) {
					for (int iRow = 0; iRow < MyTableViewSample.this.table.getItems().size(); iRow++) {
						for (int iColumn = 0; iColumn < MyTableViewSample.this.table.getColumns().size(); iColumn++) {
							final TableColumn iTableColumn = MyTableViewSample.this.table.getColumns().get(iColumn);
							final Object iCellData = iTableColumn.getCellData(iRow);
							final String iData = iCellData.toString();
							if ((iData != null) && iData.contains(iFilter)) {
								MyTableViewSample.this.table.getSelectionModel().select(iRow);
							}
						}
					}
				}
			}

		});

		final Scene scene = new Scene(new VBox(this.filter, this.table));
		stage.setTitle("MyTableView Sample");
		stage.setWidth(450);
		stage.setHeight(550);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

}