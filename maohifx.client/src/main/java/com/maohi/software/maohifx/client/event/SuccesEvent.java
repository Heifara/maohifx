/**
 *
 */
package com.maohi.software.maohifx.client.event;

import java.net.URL;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * @author heifara
 *
 */
public class SuccesEvent extends Event {

	private static final long serialVersionUID = 1L;
	public static final EventType<ExceptionEvent> SUCCES = new EventType<>("SUCCES");
	private final Object item;
	private final URL url;
	private final URL processesdUrl;

	public SuccesEvent(final Object aSource, final Object aItem, final URL aUrl, final URL aProcessesdUrl) {
		super(SUCCES);

		this.source = aSource;
		this.item = aItem;
		this.url = aUrl;
		this.processesdUrl = aProcessesdUrl;
	}

	public Object getItem() {
		return this.item;
	}

	public URL getProcessesdUrl() {
		return this.processesdUrl;
	}

	public URL getUrl() {
		return this.url;
	}

}
