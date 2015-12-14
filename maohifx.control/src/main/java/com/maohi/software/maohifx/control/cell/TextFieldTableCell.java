/**
 *
 */
package com.maohi.software.maohifx.control.cell;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * @author heifara
 *
 */
public class TextFieldTableCell<S, T> extends TableCell<S, T>implements EventHandler<ActionEvent>, ChangeListener<Boolean> {

	private TextField textfield;
	private final StringConverter<T> converter;

	public TextFieldTableCell(final StringConverter aStringConverter) {
		this.converter = aStringConverter;
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();

		this.setText(this.converter.toString(this.getItem()));
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
		this.commitEdit(this.converter.fromString(this.getTextfield().getText()));
	}

	@Override
	public void startEdit() {
		if (!this.isEditable() || !this.getTableView().isEditable() || !this.getTableColumn().isEditable()) {
			return;
		}
		super.startEdit();

		this.setText(null);

		final TableRow<?> iTableRow = (TableRow<?>) this.getParent();
		this.setGraphic(this.getTextfield());
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
				this.getTextfield().setText(this.converter.toString(aItem));
				this.setGraphic(this.getTextfield());
			} else {
				this.setText(this.converter.toString(aItem));
				this.getTextfield().setText(this.converter.toString(aItem));
				this.setGraphic(null);
			}
		}
	}

}
