package com.maohi.software.maohifx.control;

import javafx.util.StringConverter;

/**
 * @author heifara
 * @param <T>
 *
 */
public class JSObjectStringConverter<T> extends StringConverter<T> {

	private Class<?> type;

	@SuppressWarnings("unchecked")
	@Override
	public T fromString(final String aString) {
		if (this.type == String.class) {
			return (T) aString;
		} else if (this.type == Double.class) {
			return (T) Double.valueOf(aString);
		} else if (this.type == Float.class) {
			return (T) Float.valueOf(aString);
		} else {
			throw new IllegalArgumentException(String.format("The type of %s is illegal", aString));
		}
	}

	@Override
	public String toString(final T aObject) {
		if (aObject != null) {
			if (aObject instanceof String) {
				this.type = String.class;
				return (String) aObject;
			} else if (aObject instanceof Double) {
				this.type = Double.class;
				return aObject.toString();
			} else if (aObject instanceof Float) {
				this.type = Float.class;
				return aObject.toString();
			} else {
				return aObject.toString();
			}
		}
		return null;
	}
}