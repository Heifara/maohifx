package com.maohi.software.maohifx.common;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.util.StringConverter;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * @author heifara
 * @param <T>
 *
 */
@SuppressWarnings("restriction")
public class ExtStringConverter<T> extends StringConverter<T> {

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
			} else if (aObject instanceof Integer) {
				return Integer.valueOf(aObject.toString()).toString();
			} else if (aObject instanceof Double) {
				this.type = Double.class;
				final NumberFormat iFormat = NumberFormat.getInstance();
				iFormat.setMaximumFractionDigits(0);
				return iFormat.format(Double.valueOf((Double) aObject));
			} else if (aObject instanceof LocalDate) {
				final LocalDate iLocalDate = (LocalDate) aObject;
				return iLocalDate.toString();
			} else if (aObject instanceof Date) {
				final Date iDate = (Date) aObject;
				final DateFormat iFormat = DateFormat.getDateInstance();
				return iFormat.format(iDate);
			} else if (aObject instanceof Float) {
				this.type = Float.class;
				return aObject.toString();
			} else if (aObject instanceof ScriptObjectMirror) {
				final ScriptObjectMirror iScriptObject = (ScriptObjectMirror) aObject;
				final ScriptObjectMirror iToStringMethod = (ScriptObjectMirror) iScriptObject.get("toString");
				iToStringMethod.setMember("this", iScriptObject);

				return (String) iToStringMethod.call(iScriptObject);
			} else {
				throw new IllegalArgumentException(String.format("The type of %s is illegal", aObject.toString()));
			}
		}
		return null;
	}
}