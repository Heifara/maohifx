/**
 *
 */
package com.maohi.software.maohifx.control.cell;

import com.maohi.software.maohifx.common.ExtStringConverter;
import com.maohi.software.maohifx.control.events.CellActionEvent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;

/**
 * @author heifara
 *
 */
public class ComboBoxTableCell<S, T> extends TableCell<S, T>implements EventHandler<ActionEvent> {

	private ComboBox<T> comboBox;
	private final EventHandler<CellActionEvent<S, T>> onUpdateItem;
	private final EventHandler<CellActionEvent<S, T>> onAction;

	public ComboBoxTableCell(final EventHandler<CellActionEvent<S, T>> aOnUpdateItem, final EventHandler<CellActionEvent<S, T>> aOnAction) {
		this.onUpdateItem = aOnUpdateItem;
		this.onAction = aOnAction;
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();

		this.setGraphic(this.getComboBox());
	}

	public ComboBox<T> getComboBox() {
		if (this.comboBox == null) {
			this.comboBox = new ComboBox<>();
			this.comboBox.setConverter(new ExtStringConverter<>());
			this.comboBox.setOnAction(this);
		}
		return this.comboBox;
	}

	@Override
	public void handle(final ActionEvent aEvent) {
		final Object iSelectedItem = this.comboBox.getSelectionModel().getSelectedItem();

		this.getTableView().getSelectionModel().select(this.getTableRow().getIndex());
		this.onAction.handle(new CellActionEvent<S, T>(this, aEvent, this.getTableRow(), this.getTableColumn(), iSelectedItem, this.getIndex()));
	}

	@Override
	public void startEdit() {
		super.startEdit();

		this.setText(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void updateItem(final T aItem, final boolean aEmpty) {
		super.updateItem(aItem, aEmpty);

		if (aEmpty) {
			this.setText(null);
			this.setGraphic(null);
		} else {
			this.setText(null);
			this.setGraphic(this.getComboBox());

			this.onUpdateItem.handle(new CellActionEvent<S, T>(this, null, this.getTableRow(), this.getTableColumn(), aItem, this.getIndex()));
		}
	}

}
