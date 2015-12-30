/**
 *
 */
package com.maohi.software.maohifx.client.event;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * @author heifara
 *
 */
public class ExceptionEvent extends Event {

	private static final long serialVersionUID = 1L;
	public static final EventType<ExceptionEvent> EXCEPTION_THROWN = new EventType<>("EXCEPTION_THROWN");

	private final Exception exception;

	public ExceptionEvent(final Object aSource, final Exception aException) {
		super(EXCEPTION_THROWN);

		this.source = aSource;
		this.exception = aException;
	}

	public Exception getException() {
		return this.exception;
	}

}
