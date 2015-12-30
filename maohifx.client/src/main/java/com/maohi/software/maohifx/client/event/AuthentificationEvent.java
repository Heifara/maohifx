/**
 *
 */
package com.maohi.software.maohifx.client.event;

import javax.ws.rs.client.WebTarget;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * @author heifara
 *
 */
public class AuthentificationEvent extends Event {

	private static final long serialVersionUID = 1L;
	private final WebTarget webTarget;

	public AuthentificationEvent(final EventType<? extends Event> aEventType, final Object aSource, final WebTarget aWebTarget) {
		super(aEventType);

		this.source = aSource;
		this.webTarget = aWebTarget;
	}

	public WebTarget getWebTarget() {
		return this.webTarget;
	}
}
