/**
 *
 */
package com.maohi.software.maohifx.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

/**
 * @author heifara
 *
 */
public class ButtonTableCell<S, T> extends TableCell<S, T> {

	private Button button;
	private final SimpleStringProperty text;

	public ButtonTableCell(final SimpleStringProperty aTextProperty, final EventHandler<ActionEvent> aActionEvent) {
		this.text = aTextProperty;

		this.getButton().setOnAction(aActionEvent);
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
		}
		return this.button;
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
