/**
 *
 */
package com.maohi.software.maohifx.control.cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * @author heifara
 *
 */
public class TextFieldTableCell<S, T> extends TableCell<S, T>implements EventHandler<ActionEvent>, ChangeListener<Boolean> {

	private ObjectProperty<StringConverter<T>> stringConverter;

	private TextField textfield;

	public TextFieldTableCell(final StringConverter<T> aStringConverter) {
		this.stringConverterProperty().set(aStringConverter);
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();

		this.setText(this.getStringConverter().toString(this.getItem()));
		this.setGraphic(null);
	}

	@Override
	public void changed(final ObservableValue<? extends Boolean> aObservable, final Boolean aOldValue, final Boolean aNewValue) {
		if (aNewValue) {
			this.focusGain(aObservable);
		} else {
			this.focusLost(aObservable);
		}
	}

	private void focusGain(final ObservableValue<? extends Boolean> aObservable) {
	}

	private void focusLost(final ObservableValue<? extends Boolean> aObservable) {
		this.cancelEdit();
	}

	public StringConverter<T> getStringConverter() {
		return this.stringConverter.get();
	}

	public TextField getTextfield() {
		if (this.textfield == null) {
			this.textfield = new TextField();
			this.textfield.setOnAction(this);
			this.textfield.focusedProperty().addListener(this);
		}
		return this.textfield;
	}

	@Override
	public void handle(final ActionEvent aEvent) {
		this.commitEdit(this.getStringConverter().fromString(this.getTextfield().getText()));
	}

	@Override
	public void startEdit() {
		if (!this.isEditable() || !this.getTableView().isEditable() || !this.getTableColumn().isEditable()) {
			return;
		}
		super.startEdit();

		this.setText(null);

		this.setGraphic(this.getTextfield());
	}

	protected ObjectProperty<StringConverter<T>> stringConverterProperty() {
		if (this.stringConverter == null) {
			this.stringConverter = new SimpleObjectProperty<StringConverter<T>>();
		}
		return this.stringConverter;
	}

	@Override
	protected void updateItem(final T aItem, final boolean aEmpty) {
		super.updateItem(aItem, aEmpty);

		if (aEmpty) {
			this.setText(null);
			this.getTextfield().setText(null);
			this.setGraphic(null);
		} else {
			if (this.isEditing()) {
				this.setText(null);
				this.getTextfield().setText(this.getStringConverter().toString(aItem));
				this.setGraphic(this.getTextfield());
			} else {
				this.setText(this.getStringConverter().toString(aItem));
				this.getTextfield().setText(this.getStringConverter().toString(aItem));
				this.setGraphic(null);
			}
		}
	}

}
