/**
 *
 */
package com.maohi.software.maohifx.control.cell;

import com.maohi.software.maohifx.control.events.CellActionEvent;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @author heifara
 *
 */
public class ComboBoxCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

	private ObjectProperty<EventHandler<CellActionEvent<S, T>>> onUpdateItem;
	private ObjectProperty<EventHandler<CellActionEvent<S, T>>> onAction;

	@Override
	public TableCell<S, T> call(final TableColumn<S, T> aParam) {
		return new ComboBoxTableCell<>(this.getOnUpdateItem(), this.getOnAction());
	}

	public EventHandler<CellActionEvent<S, T>> getOnAction() {
		return this.onActionProperty().get();
	}

	public EventHandler<CellActionEvent<S, T>> getOnUpdateItem() {
		return this.onUpdateItemProperty().get();
	}

	public ObjectProperty<EventHandler<CellActionEvent<S, T>>> onActionProperty() {
		if (this.onAction == null) {
			this.onAction = new SimpleObjectProperty<EventHandler<CellActionEvent<S, T>>>();
		}
		return this.onAction;
	}

	public ObjectProperty<EventHandler<CellActionEvent<S, T>>> onUpdateItemProperty() {
		if (this.onUpdateItem == null) {
			this.onUpdateItem = new SimpleObjectProperty<>(new EventHandler<CellActionEvent<S, T>>() {

				@Override
				public void handle(final CellActionEvent<S, T> aEvent) {
				}
			});
		}
		return this.onUpdateItem;
	}

	public void setOnAction(final EventHandler<CellActionEvent<S, T>> onAction) {
		this.onActionProperty().set(onAction);
	}

	public void setOnUpdateItem(final EventHandler<CellActionEvent<S, T>> aEventHandler) {
		this.onUpdateItemProperty().set(aEventHandler);
	}
}
