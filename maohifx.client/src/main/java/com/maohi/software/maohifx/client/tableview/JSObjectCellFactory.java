/**
 * 
 */
package com.maohi.software.maohifx.client.tableview;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * @author heifara
 *
 */
public class JSObjectCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

	@Override
	public TableCell<S, T> call(final TableColumn<S, T> param) {
		return new TextFieldTableCell<S, T>(this.newStringConverter());
	}

	private StringConverter<T> newStringConverter() {
		return new StringConverter<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T fromString(final String aString) {
				return (T) aString;
			}

			@Override
			public String toString(final T aObject) {
				if (aObject != null) {
					if (aObject instanceof String) {
						return (String) aObject;
					}
				}
				return null;
			}
		};
	}
}
