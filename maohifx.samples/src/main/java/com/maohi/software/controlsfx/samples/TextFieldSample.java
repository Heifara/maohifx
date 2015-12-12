/**
 *
 */
package com.maohi.software.controlsfx.samples;

import javax.swing.JOptionPane;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.AutoCompletionBinding.AutoCompletionEvent;
import org.controlsfx.control.textfield.TextFields;

import com.maohi.software.maohifx.samples.beans.Person;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class TextFieldSample extends Application {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		launch(args);
	}

	private final ObservableList<Person> data = FXCollections.observableArrayList(new Person("Jacob", "Smith", "jacob.smith@example.com"), new Person("Isabella", "Johnson", "isabella.johnson@example.com"), new Person("Ethan", "Williams", "ethan.williams@example.com"), new Person("Emma", "Jones", "emma.jones@example.com"), new Person("Michael", "Brown", "michael.brown@example.com"));

	@SuppressWarnings("rawtypes")
	@Override
	public void start(final Stage aStage) throws Exception {
		final BorderPane iPane = new BorderPane();

		final TextField iTextField = new TextField();
		iTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(final ObservableValue<? extends String> aobservable, final String aOldValue, final String aNewValue) {
				System.out.println("Changed: " + aNewValue);
			}
		});
		final AutoCompletionBinding<Person> iAutoCompletionBinding = new AutoCompletionTextFieldBinding<>(iTextField, SuggestionProvider.create(this.data));
		iAutoCompletionBinding.addEventHandler(AutoCompletionEvent.AUTO_COMPLETED, new EventHandler<AutoCompletionEvent>() {

			@Override
			public void handle(final AutoCompletionEvent aEvent) {
				final Person iPerson = (Person) aEvent.getCompletion();
				JOptionPane.showMessageDialog(null, iPerson.toString());
			}
		});
		TextFields.bindAutoCompletion(iTextField, this.data);
		iPane.setTop(iTextField);

		final TextField iClearableText = TextFields.createClearableTextField();
		iPane.setLeft(iClearableText);

		aStage.setScene(new Scene(iPane, 400, 200));
		aStage.show();
	}

}
