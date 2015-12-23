/**
 *
 */
package com.maohi.software.maohifx.common;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javafx.beans.property.SimpleObjectProperty;

/**
 * @author heifara
 *
 */
public class SimpleLocalDateProperty extends SimpleObjectProperty<LocalDate> {

	public static void main(final String[] args) throws ParseException {
		final SimpleLocalDateProperty iLocalDateProperty = new SimpleLocalDateProperty();
		System.out.println(iLocalDateProperty.get());
		System.out.println(iLocalDateProperty.getDate());

		iLocalDateProperty.setDate(System.currentTimeMillis());
		System.out.println(iLocalDateProperty.getDate());
	}

	public SimpleLocalDateProperty() {
	}

	public Date getDate() throws ParseException {
		if (this.get() == null) {
			return null;
		}

		final Date iDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(this.get().toString());
		return iDate;
	}

	public void setDate(final Long aMilliseconde) {
		if (aMilliseconde != null) {
			final Date iDate = new Date(aMilliseconde);
			this.set(iDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}
	}

}
