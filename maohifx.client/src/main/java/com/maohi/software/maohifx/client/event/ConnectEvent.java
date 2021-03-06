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
public class ConnectEvent extends Event {

	private static final long serialVersionUID = 1L;
	public static final EventType<ConnectEvent> CONNECT_SUCCES = new EventType<>("CONNECT_SUCCES");
	public static final EventType<ConnectEvent> CONNECT_ERROR = new EventType<>("CONNECT_ERROR");

	public ConnectEvent(final EventType<? extends Event> aEventType, final Object aSource) {
		super(aEventType);

		this.source = aSource;
	}

}
