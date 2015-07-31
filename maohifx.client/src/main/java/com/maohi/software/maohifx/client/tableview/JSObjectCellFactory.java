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

	/**
	 * @author heifara
	 *
	 */
	public class JSObjectStringConverter extends StringConverter<T> {

		private Class<?> type;

		@Override
		public String toString(T aObject) {
			if (aObject != null) {
				if (aObject instanceof String) {
					type = String.class;
					return (String) aObject;
				} else if (aObject instanceof Double) {
					type = Double.class;
					return aObject.toString();
				} else if (aObject instanceof Float) {
					type = Float.class;
					return aObject.toString();
				} else {
					return aObject.toString();
				}
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T fromString(String aString) {
			if (type == String.class) {
				return (T) aString;
			} else if (type == Double.class) {
				return (T) Double.valueOf(aString);
			} else if (type == Float.class) {
				return (T) Float.valueOf(aString);
			} else {
				throw new IllegalArgumentException(String.format("The type of %s is illegal", aString));
			}
		}
	}

	@Override
	public TableCell<S, T> call(final TableColumn<S, T> param) {
		return new TextFieldTableCell<S, T>(new JSObjectStringConverter());
	}

}
