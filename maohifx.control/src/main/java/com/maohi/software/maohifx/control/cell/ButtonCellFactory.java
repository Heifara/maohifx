/**
 *
 */
package com.maohi.software.maohifx.control.cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @author heifara
 *
 */
public class ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

	private final ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase<EventHandler<ActionEvent>>() {
		@Override
		public Object getBean() {
			return ButtonCellFactory.this;
		}

		@Override
		public String getName() {
			return "onAction";
		}

		@Override
		protected void invalidated() {
			// setEventHandler(ActionEvent.ACTION, this.get());
		}
	};

	private SimpleStringProperty text;

	public ButtonCellFactory() {
		this.text = new SimpleStringProperty();
	}

	public ButtonCellFactory(final String aText, final EventHandler<ActionEvent> aEventHandler) {
		this();
		this.textProperty().set(aText);
		this.onActionProperty().set(aEventHandler);
	}

	@Override
	public TableCell<S, T> call(final TableColumn<S, T> param) {
		return new ButtonTableCell<S, T>(this.textProperty(), this.onActionProperty().get());
	}

	public final EventHandler<ActionEvent> getOnAction() {
		return this.onActionProperty().get();
	}

	public String getText() {
		return this.text.get();
	}

	public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() {
		return this.onAction;
	}

	public final void setOnAction(final EventHandler<ActionEvent> value) {
		this.onActionProperty().set(value);
	}

	public void setText(final String aText) {
		this.text.set(aText);
	}

	public final SimpleStringProperty textProperty() {
		return this.text;
	}
}
