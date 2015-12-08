/**
 *
 */
package com.maohi.software.maohifx.control;

import com.maohi.software.maohifx.beans.Person;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author heifara
 *
 */
public class TableViewTest extends Application {

	public static void main(final String[] args) {
		launch(args);
	}

	private final TableView<Person> table = new TableView<Person>();
	private final TextField filter = new TextField("Saisir votre filtre");
	private final ObservableList<Person> data = FXCollections.observableArrayList(new Person("Jacob", "Smith", "jacob.smith@example.com"), new Person("Isabella", "Johnson", "isabella.johnson@example.com"), new Person("Ethan", "Williams", "ethan.williams@example.com"), new Person("Emma", "Jones", "emma.jones@example.com"), new Person("Michael", "Brown", "michael.brown@example.com"));

	@Override
	public void start(final Stage aStage) throws Exception {

		final TableColumn<Person, String> firstNameCol = new TableColumn<Person, String>("First Name");
		this.table.getColumns().add(firstNameCol);
		firstNameCol.setMinWidth(100);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
		firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		firstNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
			@Override
			public void handle(final CellEditEvent<Person, String> t) {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setFirstName(t.getNewValue());
			}
		});

		final TableColumn<Person, String> lastNameCol = new TableColumn<Person, String>("Last Name");
		this.table.getColumns().add(lastNameCol);
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
		lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lastNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
			@Override
			public void handle(final CellEditEvent<Person, String> t) {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setLastName(t.getNewValue());
			}
		});

		final TableColumn<Person, String> ageCol = new TableColumn<Person, String>("Age");
		this.table.getColumns().add(ageCol);
		ageCol.setMinWidth(50);
		ageCol.setCellValueFactory(new PropertyValueFactory<Person, String>("age"));
		ageCol.setCellFactory(new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {

			@Override
			public TableCell<Person, String> call(final TableColumn<Person, String> param) {
				final TableCell<Person, String> cell = new TableCell<Person, String>() {

					@Override
					protected void updateItem(final String item, final boolean empty) {
						super.updateItem(item, empty);
						this.setText(item);
					}

				};
				return cell;
			}
		});

		final TableColumn<Person, String> birthdateCol = new TableColumn<Person, String>("Date de naissance");
		this.table.getColumns().add(birthdateCol);
		birthdateCol.setMinWidth(50);
		birthdateCol.setCellValueFactory(new PropertyValueFactory<Person, String>("birthdate") {

			@Override
			public ObservableValue<String> call(final CellDataFeatures<Person, String> param) {
				return this.getValue(param.getValue());
			}

			private ObservableValue<String> getValue(final Person value) {
				final Person iPerson = value;
				return new ReadOnlyStringWrapper(iPerson.getBirthdate().toString());
			}

		});

		final TableColumn<Person, String> emailCol = new TableColumn<Person, String>("Email");
		this.table.getColumns().add(emailCol);
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

		this.filter.textProperty().bindBidirectional(this.table.filterProperty());

		final Scene scene = new Scene(new VBox(this.filter, this.table));
		aStage.setTitle("MyTableView Sample");
		aStage.setWidth(450);
		aStage.setHeight(550);
		aStage.setScene(scene);
		aStage.sizeToScene();
		aStage.show();

	}

}