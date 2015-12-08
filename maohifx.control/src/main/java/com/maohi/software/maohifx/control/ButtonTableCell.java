/**
 *
 */
package com.maohi.software.maohifx.control;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
	private final SimpleStringProperty text;
	private final EventHandler<ActionEvent> actionEvent;

	public ButtonTableCell(final SimpleStringProperty aTextProperty, final EventHandler<ActionEvent> aActionEvent) {
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
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ButtonTableCell.this.actionEvent.handle(aEvent);
			}
		});
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
