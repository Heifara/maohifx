/**
 *
 */
package com.maohi.software.maohifx.control.cell;

import com.maohi.software.maohifx.control.events.CellActionEvent;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

/**
 * @author heifara
 *
 */
public class ButtonTableCell<S, T> extends TableCell<S, T>implements EventHandler<ActionEvent> {

	private Button button;
	private final StringProperty text;
	private final EventHandler<ActionEvent> actionEvent;

	public ButtonTableCell(final StringProperty aTextProperty, final EventHandler<ActionEvent> aActionEvent) {
		this.text = aTextProperty;
		this.actionEvent = aActionEvent;
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();

		this.getButton().setText(this.text.get());
		this.setGraphic(this.getButton());
	}

	public Button getButton() {
		if (this.button == null) {
			this.button = new Button();
			this.button.setOnAction(this);
		}
		return this.button;
	}

	@Override
	public void handle(final ActionEvent aEvent) {
		this.getTableView().getSelectionModel().select(this.getTableRow().getIndex());
		ButtonTableCell.this.actionEvent.handle(new CellActionEvent<S, T>(this, aEvent, this.getTableRow(), this.getTableColumn(), this.getTableRow().getItem(), this.getIndex()));
	}

	@Override
	public void startEdit() {
		super.startEdit();

		this.setText(null);
	}

	@Override
	protected void updateItem(final T aItem, final boolean aEmpty) {
		super.updateItem(aItem, aEmpty);

		if (aEmpty) {
			this.setText(null);
			this.getButton().setText(null);
		} else {
			this.setText(null);
			this.getButton().setText(this.text.get());
			this.setGraphic(this.getButton());
		}
	}

}
