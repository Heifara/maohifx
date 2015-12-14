/**
 *
 */
package com.maohi.software.maohifx.control.cell;

import java.util.Collection;

import org.controlsfx.control.textfield.TextFields;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @author heifara
 *
 */
public class JSObjectCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

	private Collection autoCompletion;

	@Override
	public TableCell<S, T> call(final TableColumn<S, T> param) {
		final TextFieldTableCell<S, T> iTableCell = new TextFieldTableCell<S, T>(new JSObjectStringConverter());
		if (this.autoCompletion != null) {
			TextFields.bindAutoCompletion(iTableCell.getTextfield(), this.autoCompletion);
		}
		return iTableCell;
	}

	public Collection getAutoCompletion() {
		return this.autoCompletion;
	}

	public void setAutoCompletion(final Collection autoCompletion) {
		this.autoCompletion = autoCompletion;
	}

}
